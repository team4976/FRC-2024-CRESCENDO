package frc.robot.Subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import com.revrobotics.CANSparkBase;
//import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkLowLevel.MotorType;
//import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
//import com.ctre.phoenix.motorcontrol.can.TalonFX;

import static frc.robot.RobotConstants._tiltLimit;
import static frc.robot.RobotConstants._tiltUpLimit;  
import static frc.robot.RobotConstants.m_AngleSpark;
import static frc.robot.RobotConstants.m_AngleSparkB;
import static frc.robot.RobotConstants.m_ShooterSpark;
import static frc.robot.RobotConstants.m_ShooterSparkB;
import static frc.robot.RobotConstants.shooter_speed;
//import static frc.robot.RobotConstants.m_IndexTalon;
import frc.robot.Subsystems.index;


public class Shooter extends SubsystemBase {
    //define motors. 
    private final index s_index; 

    private final CommandXboxController m_ctrl; 
    private final GenericHID a_ctrl = new GenericHID(0);   
    private final PIDController PIDtilt = new PIDController(0.015, 0, 0);

    double angleEn = 0; 
    double tiltAxis; 
    int arcTopInversed = -1; 
    int arcBottomInversed = 1;
    boolean tiltStopped; 
    boolean arcActive; 

    public Shooter(CommandXboxController c, index i) {
        m_ctrl = c;
        s_index = i;  
        //m_AngleSpark.getPIDController().setP(0.015);
        m_ShooterSparkB.setInverted(true);
        //m_ShooterSparkB.follow(m_ShooterSparkB);  
        //m_AngleSparkB.follow(m_AngleSpark); 
        //m_AngleSparkB.setInverted(false); 
        //m_ShooterSpark.getPIDController().
        m_AngleSpark.getEncoder().setPosition(0);
        tiltStopped = false; 
        arcActive = false; 
    }

    public Command shAim(double a){ //tilts to position
        return runOnce( () -> {
        //angleEn =  angleSpark.getEncoder().getPosition(); 
        //m_AngleSpark.getPIDController().setReference(a, CANSparkBase.ControlType.kPosition);
        m_AngleSpark.set(PIDtilt.calculate(m_AngleSpark.getEncoder().getPosition(), a));
        }); 
    }
    //for amp shooting. runs indexer and shooter simultaneously to gently eject note
    public Command shootIndexed(){ 
        return runOnce( () -> {
            m_ShooterSpark.set(-shooter_speed);
            m_ShooterSparkB.set(shooter_speed); 
            s_index.indexManual();
        });
    } //stop for amp shooting
    public Command stopIndexed(){
        return runOnce( () -> {
            m_ShooterSpark.set(0);
            m_ShooterSparkB.set(0); 
            s_index.indexManualStop();
        });
    }
   public Command encoderZero(){ //for arm tilt. called when limit hit
    return runOnce( () -> {
        m_AngleSpark.getEncoder().setPosition(0);
    });
   }
    
    //manual control commands
    //ARM TILT
    public Command shTilt(){ //set as 'default command' to constantly check
        return run( () -> {
            tiltAxis = m_ctrl.getRawAxis(5); 
            if (!_tiltLimit.get() && tiltAxis > 0){ //if down limit hit, can't go down
                tiltAxis = 0; 
            }
            if (!_tiltUpLimit.get() && tiltAxis < 0){ //if up limit hit, can't go up
                tiltAxis = 0; 
            }
            m_AngleSpark.set(tiltAxis/2); 
            SmartDashboard.putNumber("TILT INPUT", tiltAxis/2);
        });
    }
    public Command tiltStop(){
        return runOnce( () -> {
            tiltStopped = true; //boolean not used at current
            m_AngleSpark.set(0); 
        });
    }
    //SHOOTER
    public Command shootManual(){ 
        return runOnce( () -> {
            //arcActive = false;
            m_ShooterSpark.set(-shooter_speed); 
            m_ShooterSparkB.set(shooter_speed);
        });
    }
     public Command shootStop(){ 
        return runOnce( () -> {
            //arcActive = false;
            m_ShooterSpark.set(0);
            m_ShooterSparkB.set(0);
            
        });
    }
    public Command shootManualInverse(){ 
        return runOnce( () -> {
            m_ShooterSpark.set(shooter_speed); 
            m_ShooterSparkB.set(-shooter_speed);

        });
    }

    public void shootAuto(){
        m_ShooterSpark.set(-shooter_speed); 
        m_ShooterSparkB.set(shooter_speed);
    }

    //"Arcade Shooter" bound to operator triggers and bumpers. nonfunctional, not used in container
    public Command shootArcTop(){
        return run( () -> {
            if (arcActive){
            m_ShooterSparkB.set(m_ctrl.getLeftTriggerAxis()*arcTopInversed);
            //SmartDashboard.putNumber("ARCTOP_SPEED", m_ctrl.getLeftTriggerAxis()*arcTopInversed);
            }
        });
    }
    public Command shootArcBottom(){
        return run( () -> {
            if (arcActive){
            m_ShooterSpark.set(m_ctrl.getRightTriggerAxis()*arcBottomInversed); 
            //SmartDashboard.putNumber("ARCBOTTOM_SPEED", m_ctrl.getRightTriggerAxis()*arcBottomInversed);
            }
        });
    }

    public Command invertArcTop(){
        return runOnce( () -> {
            arcTopInversed = arcTopInversed*-1; //flip sign. 
        });
    }
    public Command invertArcBottom(){
        return runOnce( () -> {
            arcBottomInversed = arcBottomInversed*-1; 
        });
    }

    public Command arcSwitch(){
        //Connect this in robot container
        return runOnce(() -> {
            if (arcActive){
                arcActive =false;
            }
            else{
                arcActive = true; 
            }
        });
    }

    @Override
    public void periodic(){
        //SmartDashboard.putBoolean("ARCSHOOTER ACTIVE", arcActive);   
        SmartDashboard.putNumber("SHOOTER POS", m_AngleSpark.getEncoder().getPosition());
        SmartDashboard.putBoolean("TILT LIMIT", _tiltLimit.get());
        SmartDashboard.putBoolean("TILTBACK LIMIT", _tiltUpLimit.get()); 
        
        if (_tiltLimit.get()){
            a_ctrl.setRumble(RumbleType.kBothRumble, 0.0);
        }
        else{
            a_ctrl.setRumble(RumbleType.kBothRumble, 0.0);
        }
    }

}
