// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Commands.intake_in;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.intake;
import frc.robot.Swerve.Subsystem.Swerve;
import frc.robot.Swerve.commands.TeleopSwerve;

public class RobotContainer {
  

  private JoystickButton zeroGyro = new JoystickButton(RobotConstants._primarycontroller, XboxController.Button.kY.value);
  
  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;


    /* Subsystems */
  private final Swerve s_Swerve = new Swerve();
  Shooter Shooter = new Shooter(RobotConstants._secondarycontroller); 
  intake Intake = new intake ();

  public RobotContainer() {

    s_Swerve.setDefaultCommand(
      new TeleopSwerve(
          s_Swerve, 
          () -> -RobotConstants._primarycontroller.getRawAxis(translationAxis), 
          () -> -RobotConstants._primarycontroller.getRawAxis(strafeAxis), 
          () ->RobotConstants._primarycontroller.getRawAxis(rotationAxis),
          () -> RobotConstants._primarycontroller.getLeftBumper()
      )
  );

    configureBindings();
  }

  private void configureBindings() {
    //axis three is. probably right joystick x?  i sure hope it is
   RobotConstants._secondarycontroller.axisGreaterThan(3, 0.1).whileTrue(Shooter.shTilt()); 
    RobotConstants._secondarycontroller.axisLessThan(3, 0.1).whileTrue(Shooter.shTilt()); 
    RobotConstants._secondarycontroller.rightBumper().onTrue(Shooter.shootManual());
    RobotConstants._secondarycontroller.povDown().onTrue(Shooter.shAim(RobotConstants.ampAngle())); 
    RobotConstants._secondarycontroller.povUp().onTrue(Shooter.shAim(RobotConstants.speakerAngle()));


        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));//
        zeroGyro.onTrue(s_Swerve.testButtons("TRUE"));
        zeroGyro.onFalse(s_Swerve.testButtons("False"));
    
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
