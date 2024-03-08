package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//motor import
import static frc.robot.RobotConstants.m_ElevatorTalon;
import static frc.robot.RobotConstants.m_ElevatorVictor;
import static frc.robot.RobotConstants._ratchet;
import static frc.robot.RobotConstants._elevatorLimit;

public class elevator extends SubsystemBase{
    boolean ratcheted; 

    public elevator(){
        ratcheted = false; 
        m_ElevatorTalon.setSelectedSensorPosition(0);
        m_ElevatorVictor.setSelectedSensorPosition(0); 
    }

    public Command elevate(){
        return runOnce( () -> { 
            m_ElevatorTalon.set(ControlMode.PercentOutput,-0.5);
            m_ElevatorVictor.set(ControlMode.PercentOutput, 0.5);
        });
    }
    public Command reverse(){
        return runOnce( () -> {
            _ratchet.set(0.65); 
            m_ElevatorTalon.set(ControlMode.PercentOutput,0.5);
            m_ElevatorVictor.set(ControlMode.PercentOutput, -0.5);
        });
    }
    public Command home(){
        return runOnce( () -> {
            if (_elevatorLimit.get()){
                m_ElevatorTalon.set(ControlMode.PercentOutput,0.5);
                m_ElevatorVictor.set(ControlMode.PercentOutput, -0.5);
            }
            else{
                m_ElevatorTalon.set(ControlMode.PercentOutput, 0); 
                m_ElevatorVictor.set(ControlMode.PercentOutput, 0); 
            }
        });
    }
    public Command stop(){
        return runOnce( () -> {
            m_ElevatorTalon.set(ControlMode.PercentOutput, 0); 
            m_ElevatorVictor.set(ControlMode.PercentOutput, 0); 
        });
    }

    public Command ratchetToggle(){
        return runOnce( () -> {
            if (!ratcheted){
                _ratchet.set(1.0);
                ratcheted = true;
            }
            else {
                _ratchet.set(0.25);
                ratcheted = false; 
            }
        }); 
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("ratchet angle", _ratchet.getAngle());
        SmartDashboard.putNumber("Elevator Vic SensorPos", m_ElevatorVictor.getSelectedSensorPosition());
        SmartDashboard.putNumber("Elevator Tal SensprPos", m_ElevatorTalon.getSelectedSensorPosition());
        SmartDashboard.putBoolean("ELEVATOT LIMIT", _elevatorLimit.get()); 
    }
}
