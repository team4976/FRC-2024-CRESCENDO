package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public final class RobotConstants {
//motors n whatnot
    //shooter
    //intake
    public static final TalonSRX m_IntakeTalon = new TalonSRX(47);
    //elevator
    public static final TalonSRX m_ElevatorTalon = new TalonSRX(45);
    public static final VictorSPX m_ElevatorVictor = new VictorSPX(46);
    //idk is there anything else we need?
//variables
    public static boolean elevator_up = false;
    public static boolean shooter_up = false;
}
