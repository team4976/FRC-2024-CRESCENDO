package frc.robot.Swerve.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Swerve.Subsystem.Swerve;
import frc.robot.Subsystems.limelight;

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
    private Swerve a_Swerve; 
    private limelight a_l;
    PIDController pidT = new PIDController(0.015, 0, 0);
    PIDController pidF = new PIDController(0.015, 0, 0);
    PIDController pidH = new PIDController(0.015, 0, 0); 

    @Override 
    public void initialize() {
        a_Swerve.teleopToggle(); 
        botPositioned = false;
        shooterAimed = false;
        isFinished = false; //idk if i have to set it but better safe than sorry
        pidT.setTolerance(5.0);
        pidF.setTolerance(5.0);
        pidH.setTolerance(5.0); 
        //set shooter to a point we'll just have to determine with trial and error ig 
    }

    @Override 
    public void execute() {
        if (a_l.ID() == 3){
            if (a_l.THor() < distMax && a_l.THor() > distMin){
                botPositioned = true; 
            }
            //insert similar gate to make shooter aimed true (provided ll on shooter)
            
            if (!botPositioned){
                //point at target and drive at it. distances changeable see setpoints
                a_Swerve.driveLime(new Translation2d(pidF.calculate(a_l.THor(), distSetpoint), pidH.calculate(a_l.X(), hSetpoint)),
                - pidT.calculate(a_l.X(), hSetpoint), false, true);
            }
            if (botPositioned){
                //this is where I'd put my shoot command
                //IF I HAD ONE
            }
        }
        else {
            System.out.println("SPEAKER TAG NOT FOUND"); 
            isFinished = true; 
        }
    }

    public void end(){
        a_Swerve.teleopToggle();
    }

    @Override
    public boolean isFinished() {
        return isFinished; 
    }
}
