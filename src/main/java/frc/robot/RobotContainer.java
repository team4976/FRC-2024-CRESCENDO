// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Commands.intake_in;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.intake;

public class RobotContainer {
  intake Intake = new intake ();
  CommandXboxController _primarycontroller = new CommandXboxController(0);
  CommandXboxController _secondarycontroller = new CommandXboxController(1);
  Shooter Shooter = new Shooter(_secondarycontroller); 
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    //axis three is. probably right joystick x?  i sure hope it is
    _secondarycontroller.axisGreaterThan(3, 0.1).whileTrue(Shooter.shTilt()); 
    _secondarycontroller.axisLessThan(3, 0.1).whileTrue(Shooter.shTilt()); 
    _secondarycontroller.rightBumper().onTrue(Shooter.shootManual());
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
