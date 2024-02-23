package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class intake extends SubsystemBase{
    private final TalonSRX intake = new TalonSRX(0);//I have a talon for this in RobotConstants but it ain't workin :P

    public void runIntake(){
        intake.set(ControlMode.PercentOutput,0.8);
    }
}
