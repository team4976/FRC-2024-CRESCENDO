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
    double vol;
    //calibrated
    double noteDetectVelocity = 7;
    double noteNoneVelocity = 7; 

    public index(){

    }
 //run command and allow runindexout to stop it on the current trigger
    public void runIndexIn(){
            m_IndexTalon.set(ControlMode.PercentOutput, -0.35);
            indexManual = false; 
    } 

    public void runIndexOut(){ //this is a stop. an auto stop. for the current-button in bot container
        //name is weird it didn't always do this.
        //System.out.println("STOP IND"); 
        //stop when note is in there
        if (!indexManual){
        m_IndexTalon.set(ControlMode.PercentOutput, 0);
        }
    }

    //not used. theoretically, current based index reverse, which we don't need
    //if this is ever needed fix it so it works first
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
            m_IndexTalon.set(ControlMode.PercentOutput, -0.6); 
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
    
    
    public boolean noteIndexed(){ //IS USED FOR THE BOOLEANSUPPLIER NOTECHECK IN BOT CONTAINER!
        return noteIn; 
    }

    @Override
    public void periodic(){
        vel = m_IndexTalon.getSupplyCurrent();
        vol = m_IntakeTalon.getSupplyCurrent();
        //I will be honest with you we messed with this so much I have no idea what's happening
        //it does work. but the variables might need readjusting if the intake/index changes drastically
        if (vel > noteDetectVelocity && vol < noteNoneVelocity){ //threshold for "there is definitely a note in here"
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
        SmartDashboard.putNumber("IntakeCurrent", vol); 
        SmartDashboard.putBoolean("noteIn", noteIn);

    }
}
