package frc.robot.Subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class index extends SubsystemBase{
    private final TalonFX indexM = new TalonFX(0); 
    boolean noteIn;
    double vel; 
    double noteDetectVelocity = 0;

    public index(){

    }

    public void runIndexIn(){
        while (!noteIn){
            indexM.set(ControlMode.PercentOutput, 1);
        }
        //stop when there's a note sufficiently In There
    } //NOTE TO SELF: ADD TO INTAKE

    public void runIndexOut(){
        while (noteIn){
            indexM.set(ControlMode.PercentOutput, 1);
        }
        //stop when note is not In There
    }
    
    public boolean noteIndexed(){
        return noteIn; 
    }

    @Override
    public void periodic(){
        vel = indexM.getSelectedSensorVelocity();
        if (vel > noteDetectVelocity){
            noteIn = true;
        }
        else {
            noteIn = false; 
        }   
    }
}
