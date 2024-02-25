package frc.robot.Subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.RobotConstants.m_IndexTalon;
import static frc.robot.RobotConstants.m_IntakeTalon;

public class index extends SubsystemBase{
    boolean noteIn;
    boolean indexManual; 
    double vel; 
    //calibrated
    double noteDetectVelocity = 10;
    double noteNoneVelocity = 10; 

    public index(){

    }
//the run commands take the note into the pizza box. the rev commands push it out. 
//"-In" puts the note between the wheels. "-Out" pushes it through when it's already in there
    public void runIndexIn(){
            m_IndexTalon.set(ControlMode.PercentOutput, -0.35);
            indexManual = false; 
    } 

    public void runIndexOut(){
        System.out.println("STOP IND"); 
        //stop when note is not In There
        if (!indexManual){
        m_IndexTalon.set(ControlMode.PercentOutput, 0);
        }
    }

    public void revIndexIn(){
        while (!noteIn){
            m_IndexTalon.set(ControlMode.PercentOutput, 0.35);
        }
        m_IndexTalon.set(ControlMode.PercentOutput, 0);
    }

    public void revIndexOut(){
        while (noteIn){
            m_IndexTalon.set(ControlMode.PercentOutput, 0.35);
        }
        m_IndexTalon.set(ControlMode.PercentOutput, 0);
    }
//MANUAL CONTROLS HERE
    public Command indexManual(){ 
        return runOnce( () -> {
            indexManual = true; 
            m_IndexTalon.set(ControlMode.PercentOutput, -0.35); 
        });
    }
    public Command indexManualInverse(){ 
        return runOnce( () -> {
            m_IndexTalon.set(ControlMode.PercentOutput, 0.35); 
        });
    }
    public Command indexManualStop(){ 
        return runOnce( () -> {
            m_IndexTalon.set(ControlMode.PercentOutput, 0); 
        });
    }
    
    
    public boolean noteIndexed(){
        return noteIn; 
    }

    @Override
    public void periodic(){
        vel = m_IndexTalon.getSupplyCurrent();
        if (vel > noteDetectVelocity && m_IntakeTalon.getSupplyCurrent() < noteNoneVelocity){ //threshold for "there is definitely a note in here"
            noteIn = true;
        }
        else {
            noteIn = false; 
        }   

        //making that two separate criterion cuts out the inbetween spaces where the note is partially in there
        /*if (vel > 3){
            System.out.println(vel);
        }*/
        SmartDashboard.putNumber("IndexCurrent", vel);
        SmartDashboard.putNumber("IntakeCurrent", m_IntakeTalon.getSupplyCurrent()); 
        SmartDashboard.putBoolean("noteIn", noteIn);
         

    }
}
