package frc.robot;

import java.util.function.BooleanSupplier;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

public final class RobotConstants {
//motors n whatnot
    //shooter
    public static final CANSparkMax m_ShooterSpark = new CANSparkMax(41,MotorType.kBrushless); 
    public static final CANSparkMax m_ShooterSparkB = new CANSparkMax(42, MotorType.kBrushless); 
    public static final CANSparkMax m_AngleSpark = new CANSparkMax(43,MotorType.kBrushless);
    public static final CANSparkMax m_AngleSparkB = new CANSparkMax(44, MotorType.kBrushless); 
    //indexer 
    public static final TalonFX m_IndexTalon = new TalonFX(40);
    //intake
    public static final TalonSRX m_IntakeTalon = new TalonSRX(47);
    //elevator
    public static final TalonSRX m_ElevatorTalon = new TalonSRX(45);
    public static final VictorSPX m_ElevatorVictor = new VictorSPX(46);
    //idk is there anything else we need?
//variables
    public static double speakerAngle(){
        return 0.3; 
    }
    public static double ampAngle(){
        return 0.1; 
    }
    public static double elevator_position = 0;
    public static double shooter_position = 0;
    public static double elevator_threshold = 1;
    public static double shooter_threshold = 1;
    public static BooleanSupplier elevator_up = () -> m_ElevatorTalon.getSelectedSensorPosition() > elevator_threshold;
    public static BooleanSupplier shooter_up = () -> m_ShooterSpark.getAbsoluteEncoder()  > shooter_threshold;

}
