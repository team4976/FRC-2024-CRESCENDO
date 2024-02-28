package frc.robot.Swerve.commands;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.RobotConstants;
import frc.robot.Commands.intake_in;
import frc.robot.Swerve.Constants;
import frc.robot.Swerve.Subsystem.Swerve;

public class drive extends SequentialCommandGroup{

private Trajectory drivefwd = new Trajectory();

    private ProfiledPIDController ThetaController = new ProfiledPIDController(
        RobotConstants.AutoConstants.kPThetaController-1, 
        0.0,
        0.0,
        RobotConstants.AutoConstants.kThetaControllerConstraints
     );


public drive (Swerve s_Swerve){



TrajectoryConfig config = 
        new TrajectoryConfig(
            RobotConstants.AutoConstants.kMaxSpeedMetersPerSecond,
             RobotConstants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
             .setKinematics(Constants.Swerve.swerveKinematics);

         drivefwd = TrajectoryGenerator.generateTrajectory(
            new Pose2d(0, 0, new Rotation2d(0)), 
             
            List.of(new Translation2d(-1,0)),
           
              new Pose2d(-3, 0, new Rotation2d(0)) ,
               config);

               ThetaController.enableContinuousInput(-Math.PI, Math.PI);



    SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
            drivefwd,
            s_Swerve::getPose,
            Constants.Swerve.swerveKinematics, 
            new PIDController(RobotConstants.AutoConstants.kPXController, 0, 0),
            new PIDController(RobotConstants.AutoConstants.kPYController, 0, 0),
            ThetaController,
            s_Swerve::setModuleStates,
            s_Swerve);


               addCommands(
                new InstantCommand(() -> s_Swerve.resetOdometry(drivefwd.getInitialPose())),
                swerveControllerCommand
            );

}
}
