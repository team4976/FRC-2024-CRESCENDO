package frc.robot.Autonomous;

import java.util.*;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
//import edu.wpi.first.math.proto.System;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotConstants;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.index;
import frc.robot.Subsystems.intake;
import frc.robot.Swerve.Constants;
import frc.robot.Swerve.Subsystem.Swerve;

public class autoexp extends SequentialCommandGroup{
  Swerve u_Swerve = new Swerve(); 
  PIDController pidX = new PIDController(0.015, 0, 0);
  PIDController pidY = new PIDController(0.015, 0, 0);

    private Trajectory path = new Trajectory();

    private ProfiledPIDController ThetaController = new ProfiledPIDController(
        RobotConstants.AutoConstants.kPThetaController, 
        0.0,
        0.0,
        RobotConstants.AutoConstants.kThetaControllerConstraints
     );

    public autoexp (Swerve s_Swerve, intake s_Intake, Shooter s_Shooter, index s_Index) {

    System.out.println("autoexp: this runs A");




    //ThetaController.enableContinuousInput(-Math.PI, Math.PI);

    SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
        swervetrajectory(),
        s_Swerve::getPose,
        Constants.Swerve.swerveKinematics, 
        new PIDController(0.015, 0, 0),
        new PIDController(0.015, 0, 0),
        new ProfiledPIDController(
            0.015, 
            0.0,
            0.0,
            RobotConstants.AutoConstants.kThetaControllerConstraints),
        s_Swerve::setModuleStates,
        s_Swerve);

    System.out.println("this runs B");

    addCommands(
      new InstantCommand(()-> s_Shooter.shootManual()),
      new WaitCommand(0.5),//may change to wait untill based off of voltage
      new InstantCommand(()-> s_Index.runIndexIn()),
      //  new InstantCommand(()-> RobotConstants.motorsZero()),
      new InstantCommand(()-> s_Shooter.shootStop()),
      new InstantCommand(()-> s_Index.indexManualStop()),
      swerveControllerCommand 
      //  new InstantCommand(()-> s_Intake.runIntake()),
      //  new InstantCommand(()-> RobotConstants.motorsZero())
      //new InstantCommand( () -> u_Swerve.driveLime(new Translation2d( 3, 0), 0, true, false))
    );

    }

      public Trajectory generateTrajectory() {

    // 2018 cross scale auto waypoints.
    var sideStart = new Pose2d(Units.feetToMeters(1.54), Units.feetToMeters(10.23),
        Rotation2d.fromDegrees(-180));
    var crossScale = new Pose2d(Units.feetToMeters(10.7), Units.feetToMeters(6.8),
        Rotation2d.fromDegrees(-160));

    var interiorWaypoints = new ArrayList<Translation2d>();
    interiorWaypoints.add(new Translation2d(Units.feetToMeters(14.54), Units.feetToMeters(23.23)));
    interiorWaypoints.add(new Translation2d(Units.feetToMeters(21.04), Units.feetToMeters(18.23)));

    TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(12), Units.feetToMeters(12));
    config.setReversed(true);

    var trajectory = TrajectoryGenerator.generateTrajectory(
        sideStart,
        interiorWaypoints,
        crossScale,
        config);
        return trajectory;
  }

  Trajectory swervetrajectory() {

    var startpose = new Pose2d(0, 0,Rotation2d.fromDegrees(0));
    var endpose = new Pose2d(9, 21,Rotation2d.fromDegrees(0));

    var interiorWaypoints = new ArrayList<Translation2d>();
    interiorWaypoints.add(new Translation2d(3, 7));
    interiorWaypoints.add(new Translation2d(6, 14));

    TrajectoryConfig config =  new TrajectoryConfig(
                0.3,
                0.3);
        
        config.setKinematics(Constants.Swerve.swerveKinematics);

    path = TrajectoryGenerator.generateTrajectory(
        startpose,
        interiorWaypoints,
        endpose,
        config);
        return path;
  }
}
