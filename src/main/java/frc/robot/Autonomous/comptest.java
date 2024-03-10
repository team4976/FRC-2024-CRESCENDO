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
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.index;
import frc.robot.Subsystems.intake;
import frc.robot.Swerve.Constants;
import frc.robot.Swerve.Subsystem.Swerve;

public class comptest extends SequentialCommandGroup{
  Swerve u_Swerve = new Swerve(); 
  PIDController pidX = new PIDController(0.2, 0, 0);
  PIDController pidY = new PIDController(0.015, 0, 0);
  

    private Trajectory path = new Trajectory();

    private ProfiledPIDController ThetaController = new ProfiledPIDController(
        RobotConstants.AutoConstants.kPThetaController, 
        0.0,
        0.0,
        RobotConstants.AutoConstants.kThetaControllerConstraints
     );

    public comptest (Swerve s_Swerve, Shooter s_Shooter, index s_Index, BooleanSupplier s_noteCheck) {

    System.out.println("autoexp: this runs A");
    


    //ThetaController.enableContinuousInput(-Math.PI, Math.PI);

            SwerveControllerCommand bwd_swerveControllerCommand = new SwerveControllerCommand(
        bwd_swervetrajectory(),
        s_Swerve::getPose,
        Constants.Swerve.swerveKinematics, 
        new PIDController(0.2, 0, 0),
        new PIDController(1, 0, 0),
        new ProfiledPIDController(
            1, 
            0.0,
            0.0,
            RobotConstants.AutoConstants.kThetaControllerConstraints),
        s_Swerve::setModuleStates,
        s_Swerve);

    System.out.println("this runs B");

    addCommands(
       new InstantCommand(() -> s_Swerve.zeroGyro()),
      new InstantCommand(()-> RobotConstants.motorsZero()),
      new InstantCommand(()-> s_Shooter.shootAuto()), 
      bwd_swerveControllerCommand,
     new InstantCommand(()-> s_Index.runIndexIn()),
      new WaitCommand(2 ),  
      new InstantCommand(()-> RobotConstants.motorsZero())


      //new InstantCommand( () -> u_Swerve.driveLime(new Translation2d( 3, 0), 0, true, false))
    );
    System.out.println("auto_End: "+ System.currentTimeMillis());
    }

  Trajectory bwd_swervetrajectory() {

    var startpose = new Pose2d(3, 0,Rotation2d.fromDegrees(0));

    var interiorWaypoints = new ArrayList<Translation2d>();
    interiorWaypoints.add(new Translation2d(5.5, 0));
    interiorWaypoints.add(new Translation2d(8, 0));
    interiorWaypoints.add(new Translation2d(10, 0));

    var endpose = new Pose2d(12, 0,Rotation2d.fromDegrees(0));//9.5, 14.25

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
