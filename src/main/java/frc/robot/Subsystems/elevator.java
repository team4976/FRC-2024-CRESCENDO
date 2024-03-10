package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
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
    private final GenericHID a_ctrl = new GenericHID(0); 

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
    public Command reverse(){ //still *available* on pov left
        return runOnce( () -> {
            //_ratchet.set(0.65); 
            m_ElevatorTalon.set(ControlMode.PercentOutput,1);
            m_ElevatorVictor.set(ControlMode.PercentOutput, -1);
        });
    }
    public Command home(){ //primary call from robot container
        return runOnce( () -> {
            if (_elevatorLimit.get()){ //prevents it from passing limit switch
                m_ElevatorTalon.set(ControlMode.PercentOutput,1);
                m_ElevatorVictor.set(ControlMode.PercentOutput, -1);
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

    public Command ratchetToggle(){ //stops elevator from moving up, locks climb
        return runOnce( () -> {
            if (!ratcheted){
                _ratchet.set(1.0);
                ratcheted = true;
                a_ctrl.setRumble(RumbleType.kRightRumble, 0.3);
            }
            else {
                _ratchet.set(0.25);
                ratcheted = false; 
                a_ctrl.setRumble(RumbleType.kRightRumble, 0);
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
