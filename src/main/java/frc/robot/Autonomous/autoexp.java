package frc.robot.Autonomous;

import java.util.*;
import java.util.function.BooleanSupplier;

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
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.RobotConstants;
import frc.robot.Autonomous.actionGroups.stillshot;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.index;
import frc.robot.Subsystems.intake;
import frc.robot.Swerve.Constants;
import frc.robot.Swerve.Subsystem.Swerve;

public class autoexp extends SequentialCommandGroup{
  Swerve u_Swerve = new Swerve(); //curently has no use
  PIDController pidX = new PIDController(0.2, 0, 0);
  PIDController pidY = new PIDController(0.015, 0, 0);
  

    private Trajectory path = new Trajectory();//creates an instance to be used later

    private ProfiledPIDController ThetaController = new ProfiledPIDController(//not used
        RobotConstants.AutoConstants.kPThetaController, 
        0.0,
        0.0,
        RobotConstants.AutoConstants.kThetaControllerConstraints
     );

    public autoexp (Swerve s_Swerve, intake s_Intake, Shooter s_Shooter, index s_Index, BooleanSupplier s_noteCheck) {

    // System.out.println("autoexp: this runs A");
    new Trigger(s_noteCheck).onTrue(s_Intake.stopIntake());//julia black magic


    //ThetaController.enableContinuousInput(-Math.PI, Math.PI);

    SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
        swervetrajectory(),//the path the bot follow
        s_Swerve::getPose,
        Constants.Swerve.swerveKinematics,//wheel ratios 
        new PIDController(0.2, 0, 0),//PID that controls the x vector
        new PIDController(1, 0, 0),//PID that controls the y vector
        new ProfiledPIDController(//PID to control rotation constraind to 1 radian per second
            1, 
            0.0,
            0.0,
            RobotConstants.AutoConstants.kThetaControllerConstraints),
        s_Swerve::setModuleStates,//sets the moduals to the required speeds
        s_Swerve);

    //System.out.println("this runs B");

    addCommands(
      //just same commands as stillshot
      // new InstantCommand(() -> s_Swerve.zeroGyro()),
      // new InstantCommand(()-> s_Shooter.shootAuto()),
      // new WaitCommand(0.6),//may change to wait untill based off of voltage
      // new InstantCommand(()-> s_Index.runIndexIn()),
      // new WaitCommand(0.3),  
      new stillshot(s_Shooter, s_Index),//fires note
      new WaitCommand(10),
      new InstantCommand(()-> RobotConstants.motorsZero()),
      new InstantCommand(() -> s_Intake.runIntakeAuto()),//spins intake
      swerveControllerCommand, //bot follows path
      new InstantCommand(()-> RobotConstants.motorsZero())
    );
    // System.out.println("auto_End: "+ System.currentTimeMillis());
    }

    Trajectory swervetrajectory() {

    var startpose = new Pose2d(12, 0,Rotation2d.fromDegrees(0));

    var interiorWaypoints = new ArrayList<Translation2d>();
    interiorWaypoints.add(new Translation2d(10.5, 0));
    interiorWaypoints.add(new Translation2d(8, 0));
    interiorWaypoints.add(new Translation2d(5, 0));

    var endpose = new Pose2d(3, 0, Rotation2d.fromDegrees(0));//9.5, 14.25

    TrajectoryConfig config =  new TrajectoryConfig(
                4.5,
              5);
        
        config.setKinematics(Constants.Swerve.swerveKinematics);
        config.setReversed(true); 

    path = TrajectoryGenerator.generateTrajectory(
        startpose,
        interiorWaypoints,
        endpose,
        config);
        return path;
  }

  Trajectory swervetrajectory_B() {//this is not used

    var startpose = new Pose2d(3, 19,Rotation2d.fromDegrees(0));

    var interiorWaypoints = new ArrayList<Translation2d>();
    interiorWaypoints.add(new Translation2d(4.5, 19));
    interiorWaypoints.add(new Translation2d(5, 19));
    interiorWaypoints.add(new Translation2d(6, 19));

    var endpose = new Pose2d(9, 19,Rotation2d.fromDegrees(0));//9.5, 14.25

    TrajectoryConfig config =  new TrajectoryConfig(
                4.5,
              5);
        
    config.setKinematics(Constants.Swerve.swerveKinematics);
        

    path = TrajectoryGenerator.generateTrajectory(
        startpose,
        interiorWaypoints,
        endpose,
        config);
        return path;
  }
}
