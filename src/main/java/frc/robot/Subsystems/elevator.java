package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//motor import
import static frc.robot.RobotConstants.m_ElevatorTalon;
import static frc.robot.RobotConstants.m_ElevatorVictor;
import static frc.robot.RobotConstants.elevator_up;
import frc.robot.RobotConstants;

public class elevator extends SubsystemBase{
    public void elevate(){
        m_ElevatorTalon.set(ControlMode.PercentOutput,0.4);
        m_ElevatorVictor.follow(m_ElevatorTalon);
        elevator_up = true;
    }
    public void reverse(){
        m_ElevatorTalon.set(ControlMode.PercentOutput,-0.4);
        m_ElevatorVictor.follow(m_ElevatorTalon);
        elevator_up = false;
    }
}
