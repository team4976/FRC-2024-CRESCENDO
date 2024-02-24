package frc.robot.Subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.RobotConstants.m_IndexTalon;

public class index extends SubsystemBase{
    boolean noteIn;
    boolean noteOut; 
    double vel; 
    //placeholder values, calibrate later
    double noteDetectVelocity = 0;
    double noteNoneVelocity = 0; 

    public index(){

    }
//the run commands take the note into the pizza box. the rev commands push it out. 
//"-In" puts the note between the wheels. "-Out" pushes it through when it's already in there
    public void runIndexIn(){
        while (!noteIn){
            m_IndexTalon.set(ControlMode.PercentOutput, 1);
        }
        //stop when there's a note sufficiently In There
    } //NOTE TO SELF: ADD TO INTAKE

    public void runIndexOut(){
        while (!noteOut){
            m_IndexTalon.set(ControlMode.PercentOutput, 1);
        }
        //stop when note is not In There
    }

    public void revIndexIn(){
        while (!noteIn){
            m_IndexTalon.set(ControlMode.PercentOutput, -1);
        }
    }

    public void revIndexOut(){
        while (!noteOut){
            m_IndexTalon.set(ControlMode.PercentOutput, -1);
        }
    }
    
    public boolean noteIndexed(){
        return noteIn; 
    }

    @Override
    public void periodic(){
        vel = m_IndexTalon.getSelectedSensorVelocity();
        if (vel < noteDetectVelocity){ //threshold for "there is definitely a note in here"
            noteIn = true;
        }
        else {
            noteIn = false; 
        }   

        if (vel > noteNoneVelocity){ //threshold for "the note is Gone"
            noteOut = true;
        }
        else {
            noteOut = false;
        }
        //making that two separate criterion cuts out the inbetween spaces where the note is partially in there

        SmartDashboard.putNumber("IndexMotorVelocity", vel); 
    }
}
