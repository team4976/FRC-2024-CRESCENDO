package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//motor import
import static frc.robot.RobotConstants.m_ElevatorTalon;
import static frc.robot.RobotConstants.m_ElevatorTalonB;
import static frc.robot.RobotConstants.elevator_position;
import static frc.robot.RobotConstants._ratchet;

public class elevator extends SubsystemBase{
    public Command elevate(){
        return runOnce( () -> { 
            _ratchet.set(0.9);
            m_ElevatorTalon.set(ControlMode.PercentOutput,-0.5);
            //m_ElevatorTalonB.set(ControlMode.PercentOutput, 0.5);
            elevator_position = m_ElevatorTalon.getSelectedSensorPosition();
        });
    }
    public Command reverse(){
        return runOnce( () -> {
            _ratchet.set(0.65); 
            m_ElevatorTalon.set(ControlMode.PercentOutput,0.5);
            //m_ElevatorTalonB.set(ControlMode.PercentOutput, -0.5);
            elevator_position = m_ElevatorTalon.getSelectedSensorPosition();
        });
    }
    public Command home(){
        return runOnce( () -> {
            m_ElevatorTalon.set(ControlMode.Position, 0);
            //m_ElevatorTalon.set(ControlMode.Position, 0); 
        });
    }
    public Command stop(){
        return runOnce( () -> {
            _ratchet.set(0.65);
            m_ElevatorTalon.set(ControlMode.PercentOutput, 0); 
            //m_ElevatorTalonB.set(ControlMode.PercentOutput, 0); 
        });
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("ratchet angle", _ratchet.getAngle());
    }
}
