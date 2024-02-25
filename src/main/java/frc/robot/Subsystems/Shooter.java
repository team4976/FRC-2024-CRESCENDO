package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import static frc.robot.RobotConstants.m_AngleSpark;
import static frc.robot.RobotConstants.m_AngleSparkB;
import static frc.robot.RobotConstants.m_ShooterSpark;
import static frc.robot.RobotConstants.m_ShooterSparkB;
import static frc.robot.RobotConstants.shooter_up;
import static frc.robot.RobotConstants.m_IndexTalon;
//import frc.robot.Subsystems.index;


public class Shooter extends SubsystemBase {
    //define motors. 
    private final index s_index = new index(); 

    private final CommandXboxController m_ctrl;  
    //elevator

    double angleEn = 0; 

    public Shooter(CommandXboxController c) {
        m_ctrl = c; 
        m_AngleSpark.getPIDController().setP(0.015);
        m_ShooterSparkB.setInverted(true);
        m_ShooterSparkB.follow(m_ShooterSpark);  
        m_AngleSparkB.follow(m_AngleSpark); 
        //m_ShooterSpark.getPIDController().
        m_AngleSpark.getEncoder().setPosition(0);
    }

    public Command shAim(double a){ //tilts to position
        return runOnce( () -> {
        //angleEn =  angleSpark.getEncoder().getPosition(); 
        //m_AngleSpark.getEncoder().setPosition(0); 
        m_AngleSpark.getPIDController().setReference(a, CANSparkMax.ControlType.kPosition);
        //CHECK ELEVATOR HEIGHT??
        }); 
    }
    public Command shootIndexed(){ 
        return runOnce( () -> {
            m_ShooterSpark.set(-0.5);
            //s_index.runIndexOut();
        });
    }
   
    
    //manual control commands
    public Command shTilt(){
        return run( () -> { //command is "while true", should not run while joystick not past threshold
                m_AngleSpark.set(m_ctrl.getRightX()/2); 
        });

    }
    public Command shootManual(){ 
        return runOnce( () -> {
            m_ShooterSpark.set(-0.5); 
        });
    }
     public Command shootStop(){ 
        return runOnce( () -> {
            m_ShooterSpark.set(0);
            
        });
    }
    public Command shootManualInverse(){ 
        return runOnce( () -> {
            m_ShooterSpark.set(0.5); 
        });
    }


    @Override
    public void periodic(){
        if (m_AngleSpark.getEncoder().getPosition() > 0.09){
            //constants boolean true
            shooter_up = true; 
        }
        else{
            shooter_up = false; 
        }
    }
}
