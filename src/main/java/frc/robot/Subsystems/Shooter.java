package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType; 

public class Shooter extends SubsystemBase {
    //define motors. yeah. 
    private final CANSparkMax shSpark = new CANSparkMax(0,MotorType.kBrushless); 
    private final TalonFX shIndexer = new TalonFX(0); 
    private final CANSparkMax aimSpark = new CANSparkMax(0,MotorType.kBrushless); 
    //define whatever angles the shooter
    //I hope someone else codes the elevator i don't know how to code the pistons and don't want to learn
    //now that I've said that it'll end up being me who does that won't it. sigh. 

    //ENCODERS?? its a tuesday problem

    double angle; 

    public Shooter() {
        
    }

    public void shAim(double a){
        //encoder?? ENCODES?? 
        //point at A value
        
    }
    
    public void shoot(){
        //index
        //shoot
    }

    
}
//raise shooter
//angle shooter
//index [note: index also used to intake notes] [heh. index. intake. badum tiss]
//shoot