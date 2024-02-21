package frc.robot.Swerve.commands;

import java.util.List;

import javax.swing.text.Position;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Swerve.Constants;
import frc.robot.Swerve.Subsystem.Swerve;
import frc.robot.Subsystems.limelight;

public class findNote extends Command{
    private boolean isFinished;
    private double[] horPlace = {0.0, 211.625};
    private double verPlace[][] = {{0.0, 57.0, 114.0}, {0.0, 66.0, 132.0}};
    // placement in ref to center of field in inches for quick guide, we can f*ck with the numbers later, my brain is soup
    private Swerve a_Swerve; 
    private limelight a_l;
    PIDController pidT = new PIDController(0.015, 0, 0);
    PIDController pidF = new PIDController(0.015, 0, 0);
    PIDController pidH = new PIDController(0.015, 0, 0); 

    
    @Override
    public void initialize(){
        a_Swerve.teleopToggle(); 
        isFinished = false;
        pidT.setTolerance(5.0);
        pidF.setTolerance(5.0);
        pidH.setTolerance(5.0); 
    }

    @Override
    public void execute(){
        // find closest note
        double check = 1000000;
        int indexH = 0;
        int indexV = 0;
        int column;
        for (int i = 0; i < horPlace.length; i++){
            double diff = Math.abs(a_l.Position(0) - horPlace[i]);
            if (diff < check){
                check = diff;
                indexH = i; 
            }
        }
        if (indexH != 0){// determine which line of notes is closest to our bot's position
            column = 1;
        }else{
            column = 0;
        }
        for (int i = 0; i < verPlace[column].length; i++){
            double diff = Math.abs(a_l.Position(1) - verPlace[column][i]);
            if (diff < check){
                check = diff;
                indexV = i;
            }
        }
        TrajectoryConfig config = 
            new TrajectoryConfig(
            Constants.AutoConstants.kMaxSpeedMetersPerSecond,
            Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
            .setKinematics(Constants.Swerve.swerveKinematics);
        Trajectory goToNote = 
            TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // navigate to a note
            List.of(new Translation2d(horPlace[indexH], verPlace[column][indexV])),
            new Pose2d(0, 0, new Rotation2d(0)),
            config);
    }

    public void end(){
        a_Swerve.teleopToggle();
    }

    @Override
    public boolean isFinished() {
        return isFinished; 
    }
}
