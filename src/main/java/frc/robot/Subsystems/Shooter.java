package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import static frc.robot.RobotConstants.m_AngleSpark;
import static frc.robot.RobotConstants.m_ShooterSpark;
import frc.robot.Subsystems.index;


public class Shooter extends SubsystemBase {
    //define motors. 
    private final index s_index = new index(); 

    private final CommandXboxController m_ctrl;  
    //elevator

    double angleEn = 0; 

    public Shooter(CommandXboxController c) {
        m_ctrl = c; 
        m_AngleSpark.getPIDController().setP(0.015); 
    }

    public void shAim(double a){
        //angleEn =  angleSpark.getEncoder().getPosition(); 
        m_AngleSpark.getEncoder().setPosition(0); 
        m_AngleSpark.getPIDController().setReference(a, CANSparkMax.ControlType.kPosition);
    }
    public void shoot(){
        //shoot
        m_ShooterSpark.set(1);
        while (m_ShooterSpark.getEncoder().getVelocity() < 1){
            //just waiting. theres a better command for this isnt there but I do not remember
        }
        s_index.runIndexOut(); 
        //stop after a few seconds? 
        Commands.waitSeconds(2);
        m_ShooterSpark.set(0); 
    }
    
    //manual control commands
    public Command shTilt(){
        return run( () -> { //command is "while true", should not run while joystick not past threshold
                m_AngleSpark.set(m_ctrl.getRightX()); 
        });

    }
    public Command shootManual(){ //theoretically could be bound to an axis if so desired
        return runOnce( () -> {
            m_ShooterSpark.set(1);
            while (m_ShooterSpark.getEncoder().getVelocity() < 1){
                //just waiting. theres a better command for this isnt there but I do not remember
            }
            s_index.runIndexOut(); 
            //stop after a few seconds? 
            Commands.waitSeconds(2);
            m_ShooterSpark.set(0); 
        });
    }

    
}
//raise shooter
//angle shooter
//index [note: index also used to intake notes] [heh. index. intake. badum tiss]
//shoot