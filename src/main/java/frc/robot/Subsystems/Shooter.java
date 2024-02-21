package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType; 

public class Shooter extends SubsystemBase {
    //define motors. yeah. 
    private final CANSparkMax shSpark = new CANSparkMax(0,MotorType.kBrushless); 
    private final CANSparkMax angleSpark = new CANSparkMax(0,MotorType.kBrushless); 
    //I hope someone else codes the elevator i don't know how to code the pistons and don't want to learn
    //now that I've said that it'll end up being me who does that won't it. sigh. 

    double angleEn = 0; 

    public Shooter() {
        angleSpark.getPIDController().setP(0.015); 
    }

    public void shAim(double a){
        //angleEn =  angleSpark.getEncoder().getPosition(); 
        angleSpark.getEncoder().setPosition(0); 
        angleSpark.getPIDController().setReference(a, CANSparkMax.ControlType.kPosition);
    }
    
    public void shoot(){
        //shoot
        shSpark.set(1);
        while (shSpark.getEncoder().getVelocity() < 1){
            //just waiting. theres a better command for this isnt there but I do not remember
        }
        //index command
        //stop after a few seconds? 
        Commands.waitSeconds(2);
        shSpark.set(0); 
         
        
    }

    
}
//raise shooter
//angle shooter
//index [note: index also used to intake notes] [heh. index. intake. badum tiss]
//shoot