package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;

public class elevator_home extends Command{
    frc.robot.Subsystems.elevator Elevator;

    public void Elevator(frc.robot.Subsystems.elevator Elevator){
        this.Elevator = Elevator;
        addRequirements();
    }
    @Override
    public void initialize(){
        Elevator.home();
        super.initialize();
    }
    @Override
    public boolean isFinished() {
        return true;
    }
}
