package frc.robot.Swerve.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Swerve.Subsystem.Swerve;
import frc.robot.Subsystems.limelight;

public class speakerAim extends Command {
    boolean isFinished; 
    private Swerve a_Swerve; 
    private limelight a_l;
    PIDController pidT = new PIDController(0.015, 0, 0);
    PIDController pidF = new PIDController(0.015, 0, 0);
    PIDController pidH = new PIDController(0.015, 0, 0); 

    @Override 
    public void initialize() {
        a_Swerve.teleopToggle(); 
    }

    @Override 
    public void execute() {
        if (a_l.V() == 1){
            //point at target and drive at it. distances changeable see setpoints
            a_Swerve.driveLime(new Translation2d(pidF.calculate(a_l.THor(), 50), pidH.calculate(a_l.X(), 0.0)),
            - pidT.calculate(a_l.X(), 0.0), false, true);
        }
        //I realize a "spin until you see a target" is a little counterintuitive. just don't call this if the speaker target isnt detected
    }
    @Override
    public boolean isFinished() {
        return isFinished; 
    }
}
