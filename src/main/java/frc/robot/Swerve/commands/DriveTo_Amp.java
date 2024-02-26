package frc.robot.Swerve.commands;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.RobotConstants;
import frc.robot.Subsystems.limelight;
import frc.robot.Swerve.Constants;
import frc.robot.Swerve.Subsystem.Swerve;
public class DriveTo_Amp extends SequentialCommandGroup{
    

    private Trajectory bottoamp = new Trajectory();
                   private ProfiledPIDController ThetaController = new ProfiledPIDController(
                RobotConstants.AutoConstants.kPThetaController, 
                0.0,
                0.0,
                RobotConstants.AutoConstants.kThetaControllerConstraints
               );


    
    public DriveTo_Amp (limelight s_Limelight, Swerve s_Swerve) {
       
       //need method to pull limelight array

        Pose2d botpose = new Pose2d(s_Limelight.Position(0),s_Limelight.Position(1), new Rotation2d(s_Limelight.Position(5)));//ideally indexes are right

        //find bot pose and target pos rotation differences
       
        //setup tragectory
        TrajectoryConfig config = 
        new TrajectoryConfig(
            RobotConstants.AutoConstants.kMaxSpeedMetersPerSecond,
             RobotConstants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
             .setKinematics(Constants.Swerve.swerveKinematics);

         bottoamp = TrajectoryGenerator.generateTrajectory(
            botpose, 
             null,
              getAmpcolour(),
               config);

               ThetaController.enableContinuousInput(-Math.PI, Math.PI);

    SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
            bottoamp,
            s_Swerve::getPose,
            Constants.Swerve.swerveKinematics, 
            new PIDController(RobotConstants.AutoConstants.kPXController, 0, 0),
            new PIDController(RobotConstants.AutoConstants.kPYController, 0, 0),
            ThetaController,
            s_Swerve::setModuleStates,
            s_Swerve);

            addCommands(
                new InstantCommand(() -> s_Swerve.resetOdometry(bottoamp.getInitialPose())),
                swerveControllerCommand
            );


    }


private Pose2d getAmpcolour() {
//get alience then output amp pose2d for that teams amp
return RobotConstants.AutoConstants.amppose_blue;
}
}