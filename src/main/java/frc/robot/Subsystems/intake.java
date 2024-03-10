package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//motor import
import static frc.robot.RobotConstants.m_IntakeTalon;

import java.util.function.BooleanSupplier;

import static frc.robot.RobotConstants.m_IndexTalon;
import frc.robot.RobotConstants;

public class intake extends SubsystemBase{
   index i_index; 

    public intake(index dex){
        i_index = dex; 
    }
    //run intake AND current-sensitive index
    public Command runIntake(){
        return runOnce( () -> {
            m_IntakeTalon.set(ControlMode.PercentOutput,-0.7);
            i_index.runIndexIn();
        }); 
    }
    //same as above, but a void so it works in the autos
    public void runIntakeAuto(){
        m_IntakeTalon.set(ControlMode.PercentOutput,-0.7);
        i_index.runIndexIn();    
    }
    //stops intake and index hard. only called on current index button
    public Command stopIntake(){
        return runOnce ( () -> {
            //System.out.println("STOP INT");
            m_IntakeTalon.set(ControlMode.PercentOutput, 0);
            i_index.runIndexOut(); 
        });
    }

    //MANUAL CONTROLS
    //does not run index, just intake
    public Command intakeManual(){ 
        return runOnce( () -> {
            
            //System.out.println("START");
            m_IntakeTalon.set(ControlMode.PercentOutput, -0.7); 
        });
    }
    public Command intakeManualStop(){ 
        return runOnce( () -> {
            //System.out.println("STOP");
            m_IntakeTalon.set(ControlMode.PercentOutput, 0); 
        });
    }
    public Command intakeManualInverse(){ 
        return runOnce( () -> {
            m_IntakeTalon.set(ControlMode.PercentOutput, 0.5); 
        });
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("INTAKE SPEED", m_IntakeTalon.getMotorOutputPercent());
    }

}
