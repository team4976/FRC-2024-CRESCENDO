package frc.robot;

import java.util.function.BooleanSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
//import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public final class RobotConstants {

//controllers
public static final  CommandXboxController _primarycontroller = new CommandXboxController(0);
public static final  CommandXboxController _secondarycontroller = new CommandXboxController(1);

//motors n whatnot
    //shooter
    public static final CANSparkMax m_ShooterSpark = new CANSparkMax(41,MotorType.kBrushless); //BOTTOM
    public static final CANSparkMax m_ShooterSparkB = new CANSparkMax(42, MotorType.kBrushless); //TOP
    public static final CANSparkMax m_AngleSpark = new CANSparkMax(43,MotorType.kBrushless);
    public static final CANSparkMax m_AngleSparkB = new CANSparkMax(44, MotorType.kBrushless); 
    //indexer 
    public static final TalonSRX m_IndexTalon = new TalonSRX(40);
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

        public static final class AutoConstants { //TODO: The below constants must be tuned to the robot
        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;
        public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
        public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;
    
        public static final double kPXController = 1;
        public static final double kPYController = 1;
        public static final double kPThetaController = 1;

        public static Pose2d amppose_blue = new Pose2d(112, 310, new Rotation2d(0));//needs to be fine tuned + and need to test angle
        public static Pose2d amppose_red = new Pose2d(600, 310, new Rotation2d(0));//needs to be fine tuned
    
        /* Constraint for the motion profilied robot angle controller */
        public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
            new TrapezoidProfile.Constraints(
                kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);

        }

        public static void motorsZero(){
            m_ShooterSpark.set(0);
            m_ShooterSparkB.set(0);
            m_AngleSpark.set(0);
            m_AngleSparkB.set(0);
            m_ElevatorTalon.set(ControlMode.PercentOutput, 0);
            m_ElevatorVictor.set(ControlMode.PercentOutput, 0);
            m_IndexTalon.set(ControlMode.PercentOutput, 0);
            m_IntakeTalon.set(ControlMode.PercentOutput, 0);
        }
}
