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
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
//import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.intake_in;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.index;
import frc.robot.Subsystems.intake;
import frc.robot.Subsystems.elevator;
import frc.robot.Swerve.Subsystem.Swerve;
import frc.robot.Swerve.commands.TeleopSwerve;
import frc.robot.Swerve.commands.drive;
import frc.robot.Autonomous.speakerAim;

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
  //Command speaker_aim = new speakerAim(s_Swerve);
  intake Intake = new intake (Index);
  elevator Elevator = new elevator(); 
  public BooleanSupplier noteCheck = () -> Index.noteIndexed();
// public final drive s_drive = new drive(s_Swerve); 

  public RobotContainer() {
    RobotConstants.motorsZero(); 
    configureBindings();
  }

  private void configureBindings() {
    //axis five is right js y 
    //_secondarycontroller.axisGreaterThan(5, 0.1).onTrue(Shooter.shTilt()); 
    //_secondarycontroller.axisLessThan(5, -0.1).on
    

    _secondarycontroller.povDown().whileTrue(Elevator.reverse()); 
    _secondarycontroller.povDown().onFalse(Elevator.stop());
    _secondarycontroller.povUp().whileTrue(Elevator.elevate());
    _secondarycontroller.povUp().onFalse(Elevator.stop()); 
    _secondarycontroller.rightBumper().onTrue(Elevator.ratchetToggle()); 

    _primarycontroller.x().onTrue(Intake.runIntake());
    new Trigger(noteCheck).onTrue(Intake.stopIntake());
    //_primarycontroller.a().onTrue(Intake.intakeManualInverse()); 
    //_primarycontroller.x().onFalse(Intake.intakeManualStop());
    //_primarycontroller.a().onFalse(Intake.intakeManualStop());

    _primarycontroller.a().whileTrue(new speakerAim(s_Swerve, Shooter));
    //_primarycontroller.leftBumper().onTrue(Shooter.shootManual());
   // _primarycontroller.leftBumper().onFalse(Shooter.shootStop()); 
    _primarycontroller.rightBumper().onTrue(Index.indexManual());
    _primarycontroller.rightBumper().onFalse(Index.indexManualStop()); 

    _secondarycontroller.y().onTrue(Index.indexManual());
    _secondarycontroller.y().onFalse(Index.indexManualStop());
    _secondarycontroller.x().onTrue(Intake.intakeManual());
    _secondarycontroller.x().onFalse(Intake.intakeManualStop()); 
    _secondarycontroller.a().onTrue(Intake.intakeManualInverse());
    _secondarycontroller.a().onFalse(Intake.intakeManualStop()); 
    _secondarycontroller.b().onTrue(Index.indexManualInverse());
    _secondarycontroller.b().onFalse(Index.indexManualStop()); 

    _secondarycontroller.leftBumper().onTrue(Shooter.shootManual());
    _secondarycontroller.leftBumper().onFalse(Shooter.shootStop());
  
    _primarycontroller.povUp().onTrue(s_Swerve.setTurnIdeal(0.0));
    _primarycontroller.povUp().whileTrue(s_Swerve.locationRotation());
    _primarycontroller.povDown().onTrue(s_Swerve.setTurnIdeal(180));
    _primarycontroller.povDown().whileTrue(s_Swerve.locationRotation());
    _primarycontroller.povRight().onTrue(s_Swerve.setTurnIdeal(90));
    _primarycontroller.povRight().whileTrue(s_Swerve.locationRotation());
    _primarycontroller.povLeft().onTrue(s_Swerve.setTurnIdeal(270));
    _primarycontroller.povLeft().whileTrue(s_Swerve.locationRotation()); 
    
    //_secondarycontroller.leftTrigger(0.1).whileTrue(Shooter.shootArcTop());
    //_secondarycontroller.leftTrigger(0.1).onFalse(Shooter.shootStop());
    //_secondarycontroller.rightTrigger(0.1).whileTrue(Shooter.shootArcBottom());
    //_secondarycontroller.rightTrigger(0.1).onFalse(Shooter.shootStop()); 
    Shooter.setDefaultCommand(Shooter.shTilt());
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
    return null;
  }
}
