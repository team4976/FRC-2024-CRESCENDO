package frc.robot.Swerve.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Swerve.Subsystem.Swerve;

public class cardinal extends Command {

    private Swerve s_Swerve;

    int direction;


    cardinal (Swerve s_Swerve, int direct) {
         this.s_Swerve = s_Swerve;
          direction = direct;

    }

@Override
public void initialize() {
super.initialize()
;}

@Override
public void execute() {
    s_Swerve.drive(null, Math.toRadians(90*direction), isFinished(), isScheduled());
    super.execute();
}


@Override
public boolean isFinished() {
    return super.isFinished();
}
    
}
