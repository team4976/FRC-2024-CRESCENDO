package frc.robot.Autonomous;

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
import frc.robot.Subsystems.intake;
import frc.robot.Subsystems.limelight;

public class findNote extends Command{
    private double noteLocation[][] = {{1, 171}, {1, 114}, {1, 57}, {2, 293.64}, {2, 227.64}, {2, 161.64}, {2, 95.64}, {2, 29.64}, {3, 171}, {3, 114}, {3, 57,}};
    private double closest[][] = {{6, 10}, {7, 9}, {8, 9}, {9, 8}, {10, 8}};

    double diffH;
    double diffV;
    int indexH;
    int indexV;
    int column;
    // placement in ref to center of field in inches for quick guide, we can f*ck with the numbers later, my brain is soup
    private Swerve a_Swerve; 
    private limelight a_l;
    PIDController pidT = new PIDController(0.015, 0, 0);
    PIDController pidF = new PIDController(0.015, 0, 0);
    PIDController pidH = new PIDController(0.015, 0, 0); 

    intake Intake;

    public void runIntake(intake Intake){
        this.Intake = Intake;
        addRequirements(Intake);
    }
    
    @Override
    public void initialize(){
        a_Swerve.teleopToggle(); 
        a_l = new limelight();
        pidT.setTolerance(5.0);
        pidF.setTolerance(5.0);
        pidH.setTolerance(5.0); 
    }

    @Override
    public void execute(){
        // find closest note
        for (int i = 0; i < closest.length; i++){
            if (a_l.ID() == closest[i][0]){
                int index = (int)closest[i][1];
                diffH = a_l.Position(0) - noteLocation[index][0];
                diffV = a_l.Position(1) - noteLocation[index][1];
                System.out.println(noteLocation[index][0]);
                System.out.println(noteLocation[index][1]);
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
            List.of(new Translation2d(diffH, diffV)),
            new Pose2d(0, 0, new Rotation2d(0)),
            config);
    }

    public void end(){
        Intake.runIntake();
        super.initialize();
        a_Swerve.teleopToggle();
    }

    @Override
    public boolean isFinished() {
        return true; 
    }
}
