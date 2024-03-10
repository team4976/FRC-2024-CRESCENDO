package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;

public class intake_out extends Command{
    frc.robot.Subsystems.intake Intake;

    public void Intake(frc.robot.Subsystems.intake Intake){
        this.Intake = Intake;
        addRequirements();
    }

    @Override
    public void initialize(){
        //Intake.invertIntake();
        super.initialize();
    }
    @Override
    public boolean isFinished() {
        return true;
    }
}