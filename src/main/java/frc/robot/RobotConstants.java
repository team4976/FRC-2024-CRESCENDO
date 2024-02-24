package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

public final class RobotConstants {
//motors n whatnot
    //shooter
    public static final CANSparkMax m_ShooterSpark = new CANSparkMax(41,MotorType.kBrushless); 
    public static final CANSparkMax m_ShooterSparkB = new CANSparkMax(42, MotorType.kBrushless); 
    public static final CANSparkMax m_AngleSpark = new CANSparkMax(0,MotorType.kBrushless);
    //indexer 
    public static final TalonFX m_IndexTalon = new TalonFX(40);
    //intake
    public static final TalonSRX m_IntakeTalon = new TalonSRX(0);
    //elevator
    public static final TalonSRX m_ElevatorTalon = new TalonSRX(0);
    //sim
    //idk is there anything else we need?
//variables
    public double speakerAngle(){
        return 0.3; 
    }
    public double ampAngle(){
        return 0.1; 
    }
}
