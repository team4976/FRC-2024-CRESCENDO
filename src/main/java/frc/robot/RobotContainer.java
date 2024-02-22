// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Commands.intake_in;
import frc.robot.Subsystems.intake;

public class RobotContainer {
  intake Intake = new intake ();
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    CommandXboxController _primarycontroller = new CommandXboxController(0);
    CommandXboxController _secondarycontroller = new CommandXboxController(1);
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
