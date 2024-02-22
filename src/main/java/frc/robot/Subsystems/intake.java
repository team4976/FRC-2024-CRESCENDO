package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//motor import
import static frc.robot.RobotConstants.m_IntakeTalon;
import frc.robot.RobotConstants;

public class intake extends SubsystemBase{
    
    public void runIntake(){
        m_IntakeTalon.setInverted(false);
        m_IntakeTalon.set(ControlMode.PercentOutput,0.8);
    }

    public void invertIntake(){
        m_IntakeTalon.setInverted(true);
        m_IntakeTalon.set(ControlMode.PercentOutput,0.8);
    }
}
