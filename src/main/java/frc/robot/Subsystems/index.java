package frc.robot.Subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class index extends SubsystemBase{
    private final TalonFX indexM = new TalonFX(0); 
    boolean noteIn;
    double vel; 

    public index(){

    }

    public void runIndex(){
        while (noteIn){
            indexM.set(ControlMode.PercentOutput, 1);
        }
    }
    
    public boolean noteIndexed(){
        return noteIn; 
    }

    @Override
    public void periodic(){
        vel = indexM.getSelectedSensorVelocity();
        if (vel > 0){
            noteIn = true;
        }
        else {
            noteIn = false; 
        }   
    }
}
