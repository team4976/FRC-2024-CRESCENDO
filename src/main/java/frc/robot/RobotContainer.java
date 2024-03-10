// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
import frc.robot.Autonomous.actionGroups.stillshot;
import frc.robot.Autonomous.autoexp;
import frc.robot.Autonomous.returnauto;

import static frc.robot.RobotConstants._primarycontroller;
import static frc.robot.RobotConstants._secondarycontroller;
import static frc.robot.RobotConstants.tiltLimit;

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

  index Index = new index();
  Shooter Shooter = new Shooter(_secondarycontroller, Index); 
    //Command speaker_aim = new speakerAim(s_Swerve);
  intake Intake = new intake (Index);
  elevator Elevator = new elevator(); 
  public BooleanSupplier noteCheck = () -> Index.noteIndexed();
// public final drive s_drive = new drive(s_Swerve); 


/* Autos */
private autoexp m_Autoexp = new autoexp(s_Swerve, Intake, Shooter, Index, noteCheck);
private returnauto m_Returnauto = new returnauto(s_Swerve, Intake, Shooter, Index, noteCheck);
private stillshot m_Stillshot = new stillshot(Shooter, Index);

SendableChooser<SequentialCommandGroup> m_Chooser = new SendableChooser<>();

  public RobotContainer() {
    m_Chooser.setDefaultOption("shoot_drivefwd", m_Autoexp);//this is for auto selection deafault
    m_Chooser.addOption("2piece", m_Returnauto); //adds another auto
    m_Chooser.addOption("stillshot", m_Stillshot);
    SmartDashboard.putData("Auto Selector", m_Chooser);

    RobotConstants.motorsZero(); 
    configureBindings();
  }

  private void configureBindings() {    

    //ELEVATOR
    _secondarycontroller.povDown().whileTrue(Elevator.home()); //down with stop
    _secondarycontroller.povDown().onFalse(Elevator.stop());
    _secondarycontroller.povUp().whileTrue(Elevator.elevate()); //up
    _secondarycontroller.povUp().onFalse(Elevator.stop()); 
    _secondarycontroller.rightBumper().onTrue(Elevator.ratchetToggle()); //ratchet toggle 
    _secondarycontroller.povLeft().whileTrue(Elevator.reverse()); //down no stop (in case limit breaks)
    _secondarycontroller.povLeft().onFalse(Elevator.stop()); 

    //INTAKE-CURRENT
    _primarycontroller.x().onTrue(Intake.runIntake()); //intake w index , current stopped
    new Trigger(noteCheck).onTrue(Intake.stopIntake()); //current stop in 
    //_primarycontroller.a().onTrue(Intake.intakeManualInverse()); 
    //_primarycontroller.x().onFalse(Intake.intakeManualStop());
    //_primarycontroller.a().onFalse(Intake.intakeManualStop());

    //_primarycontroller.a().whileTrue(new speakerAim(s_Swerve, Shooter));

    //SHOOTER-AMP 
    _primarycontroller.leftBumper().onTrue(Shooter.shootIndexed()); //runs shooter and index simultaneous
    _primarycontroller.leftBumper().onFalse(Shooter.stopIndexed()); //stops shooter+index
    //INDEX-MANUAL
    _primarycontroller.rightBumper().onTrue(Index.indexManual());
    _primarycontroller.rightBumper().onFalse(Index.indexManualStop()); 

    //INDEX-MANUAL (for op!)
    _secondarycontroller.y().onTrue(Index.indexManual());
    _secondarycontroller.y().onFalse(Index.indexManualStop());
    //INTAKE-MANUAL
    _secondarycontroller.x().onTrue(Intake.intakeManual());
    _secondarycontroller.x().onFalse(Intake.intakeManualStop());
    //INTAKE-INVERSE 
    _secondarycontroller.a().onTrue(Intake.intakeManualInverse());
    _secondarycontroller.a().onFalse(Intake.intakeManualStop()); 
    //INDEX-INVERSE
    _secondarycontroller.b().onTrue(Index.indexManualInverse());
    _secondarycontroller.b().onFalse(Index.indexManualStop()); 

    //SHOOTER-MANUAL
    _secondarycontroller.leftBumper().onTrue(Shooter.shootManual());
    _secondarycontroller.leftBumper().onFalse(Shooter.shootStop());
  
    //D-PAD TURNING (nonfunctional)
    /* 
    _primarycontroller.povUp().onTrue(s_Swerve.setTurnIdeal(0.0));
    _primarycontroller.povUp().whileTrue(s_Swerve.locationRotation());
    _primarycontroller.povDown().onTrue(s_Swerve.setTurnIdeal(180));
    _primarycontroller.povDown().whileTrue(s_Swerve.locationRotation());
    _primarycontroller.povRight().onTrue(s_Swerve.setTurnIdeal(90));
    _primarycontroller.povRight().whileTrue(s_Swerve.locationRotation());
    _primarycontroller.povLeft().onTrue(s_Swerve.setTurnIdeal(270));
    _primarycontroller.povLeft().whileTrue(s_Swerve.locationRotation()); 
    */
    
    //ARCADE SHOOTER (derogatory) (abandoned, not fully fuctional but not *not* functional)
    /*_secondarycontroller.leftTrigger(0.1).whileTrue(Shooter.shootArcTop());
    //_secondarycontroller.leftTrigger(0.1).onFalse(Shooter.shootStop());
    //_secondarycontroller.rightTrigger(0.1).whileTrue(Shooter.shootArcBottom());
    //_secondarycontroller.rightTrigger(0.1).onFalse(Shooter.shootStop());  */
    
    //SHOOTER ARM TILT
    Shooter.setDefaultCommand(Shooter.shTilt());
    new Trigger(tiltLimit).whileFalse(Shooter.encoderZero());

    //_primarycontroller.povUp().onTrue(Shooter.shAim(50));
    
    //TELEOP SWERVE
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
    /*if the chooser isn't working:
    1. try smartdashboard instead of shuffleboard
    2. if that doesn't, give up and comment out the last line and uncomment one of the others */ 
    SmartDashboard.putString("autotestoutput", m_Chooser.getSelected().getName());
    //return null; 
    //return new autoexp(s_Swerve, Intake, Shooter, Index, noteCheck);
    //return new stillshot(Shooter, Index);
    //return new returnauto(s_Swerve, Intake, Shooter, Index, noteCheck);
    return m_Chooser.getSelected();
  }
}
