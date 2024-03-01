package frc.robot.Autonomous;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Swerve.Subsystem.Swerve;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.limelight;
import frc.robot.Subsystems.index;

public class speakerAim extends Command {
    private boolean isFinished;
    private boolean botPositioned;
    private boolean shooterAimed;
    private double distSetpoint = 50;
    private double hSetpoint = 0.0; 
    private double distMin = distSetpoint - 6;
    private double distMax = distSetpoint + 6;
    private double hMin = hSetpoint - 6;
    private double hMax = hSetpoint + 6;   
    private double speakerTarget; 
    private Swerve a_Swerve; 
    private Shooter a_Shooter; 
    private limelight a_l = new limelight();
    private index a_Index = new index(); 
    PIDController pidT = new PIDController(0.015, 0, 0);
    PIDController pidF = new PIDController(0.015, 0, 0);
    PIDController pidH = new PIDController(0.015, 0, 0); 

    public speakerAim(Swerve s){
        a_Swerve = s; 
        addRequirements(s);
        //do I need this? I just need swerve linked to the robotcontainer class. 
    }

    @Override 
    public void initialize() {
        a_Swerve.teleopToggle(); 
        botPositioned = false;
        shooterAimed = false;
        isFinished = false; //idk if i have to set it but better safe than sorry
        pidT.setTolerance(5.0);
        pidF.setTolerance(5.0);
        pidH.setTolerance(5.0); 
        speakerTarget = 4; 
        //NOTE: ADD CODE TO DETERMINE WHICH TAG BY ALLIANCE COLOUR LATER
    }

    @Override 
    public void execute() {
        if (a_l.ID() == speakerTarget){
            if (a_l.THor() < distMax && a_l.THor() > distMin && a_l.X() < hMax && a_l.X() > hMin){
                botPositioned = true; 
            }
           /*  if (!shooterAimed){
                a_Shooter.shAim(0.0);
                shooterAimed = true; 
            }*/
            
            if (!botPositioned){
                //point at target and drive at it. distances changeable see setpoints
                a_Swerve.driveLime(new Translation2d(pidF.calculate(a_l.THor(), distSetpoint), pidH.calculate(a_l.X(), hSetpoint)),
                - pidT.calculate(a_l.X(), hSetpoint), false, true);
            }
            if (botPositioned){
                //a_Shooter.shootManual();
                //a_Index.runIndexIn(); 
                isFinished = true; 
            }
        }
        else {
            System.out.println("SPEAKER TAG NOT FOUND"); 
            isFinished = true; 
        }
    }

    public void end(){
        a_Swerve.teleopToggle();
        //TODO put these back once swerve gets called
        //stop motors somehow
    }

    @Override
    public boolean isFinished() {
        return isFinished; 
    }
}