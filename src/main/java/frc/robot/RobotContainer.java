// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.intake_in;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.index;
import frc.robot.Subsystems.intake;
import frc.robot.Swerve.Subsystem.Swerve;
import frc.robot.Swerve.commands.TeleopSwerve;

import static frc.robot.RobotConstants._primarycontroller;
import static frc.robot.RobotConstants._secondarycontroller;

public class RobotContainer {
  

  //swerve copied from kitbot code
  private final Joystick driver = new Joystick(0);
  private JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
  private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kB.value);
  
  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;


    /* Subsystems */
  private final Swerve s_Swerve = new Swerve();
  Shooter Shooter = new Shooter(_secondarycontroller); 
  index Index = new index();
  intake Intake = new intake (Index);
  public BooleanSupplier noteCheck = () -> Index.noteIndexed();
  

  public RobotContainer() {
    RobotConstants.motorsZero(); 
    configureBindings();
  }

  private void configureBindings() {
    //axis four is right js y 
    //_secondarycontroller.axisGreaterThan(5, 0.1).whileTrue(Shooter.shTilt()); 
    //_secondarycontroller.start().onTrue(Shooter.tiltStop());

    //_secondarycontroller.povDown().onTrue(Shooter.shAim(RobotConstants.ampAngle())); 
    //_secondarycontroller.povUp().onTrue(Shooter.shAim(RobotConstants.speakerAngle()));

    _primarycontroller.x().onTrue(Intake.runIntake());
    new Trigger(noteCheck).onTrue(Intake.stopIntake());
    //_primarycontroller.a().onTrue(Intake.intakeManualInverse()); 
    //_primarycontroller.x().onFalse(Intake.intakeManualStop());
    //_primarycontroller.a().onFalse(Intake.intakeManualStop());

    //_primarycontroller.leftBumper().onTrue(Shooter.shootManual());
    //_primarycontroller.leftBumper().onFalse(Shooter.shootStop()); 
    _primarycontroller.rightBumper().onTrue(Index.indexManual());
    _primarycontroller.rightBumper().onFalse(Index.indexManualStop()); 
    
    //_secondarycontroller.y().onTrue(Index.indexManual());
    //_secondarycontroller.y().onFalse(Index.indexManualStop());
    _secondarycontroller.x().onTrue(Intake.intakeManual());
    _secondarycontroller.x().onFalse(Intake.intakeManualStop()); 
    _secondarycontroller.a().onTrue(Intake.intakeManualInverse());
    _secondarycontroller.a().onFalse(Intake.intakeManualStop()); 
    _secondarycontroller.b().onTrue(Index.indexManualInverse());
    _secondarycontroller.b().onFalse(Index.indexManualStop()); 

    _secondarycontroller.leftBumper().onTrue(Shooter.shootManual());
    _secondarycontroller.leftBumper().onFalse(Shooter.shootStop());
    //_secondarycontroller.rightBumper().onTrue(Shooter.invertArcBottom());
    //_secondarycontroller.rightBumper().onFalse(Shooter.shootStop());

    //_secondarycontroller.leftTrigger(0.1).whileTrue(Shooter.shootArcTop());
    //_secondarycontroller.leftTrigger(0.1).onFalse(Shooter.shootStop());
    //_secondarycontroller.rightTrigger(0.1).whileTrue(Shooter.shootArcBottom());
    //_secondarycontroller.rightTrigger(0.1).onFalse(Shooter.shootStop()); 

    //more swerve copied from kitbot code
    s_Swerve.setDefaultCommand(
      new TeleopSwerve(
                s_Swerve, 
                () -> driver.getRawAxis(translationAxis), 
                () -> driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        //zeroGyro.onTrue(s_Swerve.testButtons("TRUE"));
        //zeroGyro.onFalse(s_Swerve.testButtons("False"));
    
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
