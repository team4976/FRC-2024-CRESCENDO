package frc.robot.Autonomous;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Swerve.Subsystem.Swerve;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.limelight;
import frc.robot.Subsystems.index;

public class speakerAim extends Command {
    private boolean isFinished;
    private boolean botPositioned;
    private boolean shooterAimed;
    private double distSetpoint = 40;
    private double hSetpoint = -0.0; 
    private double distMin = distSetpoint - 6;
    private double distMax = distSetpoint + 6;
    private double hMin = hSetpoint - 3;
    private double hMax = hSetpoint + 3;  
    private Swerve a_Swerve; 
    private Shooter a_Shooter; 
    private limelight a_l = new limelight();
    private index a_Index = new index(); 
    PIDController pidT = new PIDController(0.015, 0, 0);
    PIDController pidF = new PIDController(0.015, 0, 0);
    PIDController pidH = new PIDController(0.015, 0, 0); 

    public speakerAim(Swerve s, Shooter h){
        a_Swerve = s; 
        a_Shooter = h; 
        addRequirements(s);
        addRequirements(h);
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
    }

    @Override 
    public void execute() {
        //the side tags are probably close enough that we still hit the speaker
        if (a_l.ID() == 4 || a_l.ID() == 7 || a_l.ID() == 8 || a_l.ID() == 3){
            if (a_l.THor() < distMax && a_l.THor() > distMin && a_l.X() < hMax && a_l.X() > hMin){
                botPositioned = true; 
            }
            else {
                botPositioned = false; 
            }
            SmartDashboard.putBoolean("BOT POSITIONED", botPositioned);
            if (!shooterAimed){
                a_Shooter.shAim(-20);
                //put this back when I figure out where the positions are
                shooterAimed = true; 
                //a_Shooter.shootIndexed(); 
            }
            SmartDashboard.putBoolean("SHOOTER AIMED", shooterAimed); 
            
            if (!botPositioned){
                //point at target and drive at it. distances changeable see setpoints
                a_Swerve.driveLime(new Translation2d(pidF.calculate(a_l.THor(), distSetpoint), pidH.calculate(a_l.X(), hSetpoint)),
                - pidT.calculate(a_l.X(), hSetpoint), false, true);
            }
            if (botPositioned){
                //a_Index.indexManual(); 
                 System.out.println("SHOOT");
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
        //a_Shoot.shootStop(); 
        //stop motors somehow. index stops by button-trigger
    }

    @Override
    public boolean isFinished() {
        return isFinished; 
    }
}