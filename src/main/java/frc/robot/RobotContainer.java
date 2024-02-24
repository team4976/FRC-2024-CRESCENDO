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
  
  CommandXboxController _primarycontroller = new CommandXboxController(0);
  CommandXboxController _secondarycontroller = new CommandXboxController(1);

  //swerve stuff I copied from kitbot code
  private final Joystick driver = new Joystick(0);
  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;

  private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
  private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    /* Subsystems */
  private final Swerve s_Swerve = new Swerve();
  Shooter Shooter = new Shooter(_secondarycontroller); 
  intake Intake = new intake ();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    //axis three is. probably right joystick x?  i sure hope it is
    _secondarycontroller.axisGreaterThan(3, 0.1).whileTrue(Shooter.shTilt()); 
    _secondarycontroller.axisLessThan(3, 0.1).whileTrue(Shooter.shTilt()); 
    _secondarycontroller.rightBumper().onTrue(Shooter.shootManual());


    //more swerve I copied from kitbot code
    s_Swerve.setDefaultCommand(
      new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        zeroGyro.onTrue(s_Swerve.testButtons("TRUE"));
        zeroGyro.onFalse(s_Swerve.testButtons("False"));
    
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
