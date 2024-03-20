package frc.robot.Swerve;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.sensors.SensorTimeBase;
import com.ctre.phoenix6.*;
import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;

import frc.robot.Swerve.Constants;

public final class CTREConfigs {
    public TalonFXConfiguration swerveAngleFXConfig;
    public TalonFXConfiguration swerveDriveFXConfig;
    public CANCoderConfiguration swerveCanCoderConfig;

    public com.ctre.phoenix6.configs.TalonFXConfiguration swerveDriveFX6Config = new com.ctre.phoenix6.configs.TalonFXConfiguration(); 

    public CTREConfigs(){
        swerveAngleFXConfig = new TalonFXConfiguration();
        swerveDriveFXConfig = new TalonFXConfiguration();
        swerveCanCoderConfig = new CANCoderConfiguration();

        /* Swerve Angle Motor Configurations */
        SupplyCurrentLimitConfiguration angleSupplyLimit = new SupplyCurrentLimitConfiguration(
            Constants.Swerve.angleEnableCurrentLimit, 
            Constants.Swerve.angleContinuousCurrentLimit, 
            Constants.Swerve.anglePeakCurrentLimit, 
            Constants.Swerve.anglePeakCurrentDuration);

        swerveAngleFXConfig.slot0.kP = Constants.Swerve.angleKP;
        swerveAngleFXConfig.slot0.kI = Constants.Swerve.angleKI;
        swerveAngleFXConfig.slot0.kD = Constants.Swerve.angleKD;
        swerveAngleFXConfig.slot0.kF = Constants.Swerve.angleKF;
        swerveAngleFXConfig.supplyCurrLimit = angleSupplyLimit;

        /* Swerve Drive Motor Configuration */
        SupplyCurrentLimitConfiguration driveSupplyLimit = new SupplyCurrentLimitConfiguration(
            Constants.Swerve.driveEnableCurrentLimit, 
            Constants.Swerve.driveContinuousCurrentLimit, 
            Constants.Swerve.drivePeakCurrentLimit, 
            Constants.Swerve.drivePeakCurrentDuration);

        swerveDriveFXConfig.slot0.kP = Constants.Swerve.driveKP;
        swerveDriveFXConfig.slot0.kI = Constants.Swerve.driveKI;
        swerveDriveFXConfig.slot0.kD = Constants.Swerve.driveKD;
        swerveDriveFXConfig.slot0.kF = Constants.Swerve.driveKF;        
        swerveDriveFXConfig.supplyCurrLimit = driveSupplyLimit;
        swerveDriveFXConfig.openloopRamp = Constants.Swerve.openLoopRamp;
        swerveDriveFXConfig.closedloopRamp = Constants.Swerve.closedLoopRamp;

        CurrentLimitsConfigs limitsConfigs = new CurrentLimitsConfigs();
        limitsConfigs.SupplyCurrentLimitEnable = Constants.Swerve.driveEnableCurrentLimit; 
        limitsConfigs.withSupplyCurrentLimit(Constants.Swerve.driveContinuousCurrentLimit);
        limitsConfigs.withSupplyCurrentThreshold(Constants.Swerve.drivePeakCurrentLimit);
        limitsConfigs.withSupplyTimeThreshold(Constants.Swerve.drivePeakCurrentDuration);

        OpenLoopRampsConfigs Open_RampsConfigs = new OpenLoopRampsConfigs();
        Open_RampsConfigs.withDutyCycleOpenLoopRampPeriod(Constants.Swerve.openLoopRamp);

        ClosedLoopRampsConfigs Closed_RampsConfigs = new ClosedLoopRampsConfigs(); 
        Closed_RampsConfigs.withDutyCycleClosedLoopRampPeriod(Constants.Swerve.closedLoopRamp);

        swerveDriveFX6Config.Slot0.kP = Constants.Swerve.driveKP;
        swerveDriveFX6Config.Slot0.kI = Constants.Swerve.driveKI;
        swerveDriveFX6Config.Slot0.kD = Constants.Swerve.driveKD;
        swerveDriveFX6Config.Slot0.kV = Constants.Swerve.driveKV;
        swerveDriveFX6Config.CurrentLimits = limitsConfigs;
        swerveDriveFX6Config.OpenLoopRamps = Open_RampsConfigs;
        swerveDriveFX6Config.ClosedLoopRamps = Closed_RampsConfigs; 

        
        /* Swerve CANCoder Configuration */
        swerveCanCoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        swerveCanCoderConfig.sensorDirection = Constants.Swerve.canCoderInvert;
        swerveCanCoderConfig.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        swerveCanCoderConfig.sensorTimeBase = SensorTimeBase.PerSecond;
    }
}