package frc.robot.Swerve;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import frc.robot.Swerve.math.Conversions;
import frc.robot.Swerve.util.CTREModuleState;
import frc.robot.Swerve.util.SwerveModuleConstants;
import frc.robot.Swerve.*;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix6.*;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix.sensors.CANCoder;

public class SwerveModule {
    public int moduleNumber;
    private Rotation2d angleOffset;
    private Rotation2d lastAngle;

    private TalonFX mAngleMotor;
    //private TalonFX mDriveMotor;
    private com.ctre.phoenix6.hardware.TalonFX mDriveMotor; 
    private CANCoder angleEncoder;
    
    
    SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(Constants.Swerve.driveKS, Constants.Swerve.driveKV, Constants.Swerve.driveKA);

    public SwerveModule(int moduleNumber, SwerveModuleConstants moduleConstants){
        this.moduleNumber = moduleNumber-1;
        this.angleOffset = moduleConstants.angleOffset;
        
        /* Angle Encoder Config */
        angleEncoder = new CANCoder(moduleConstants.cancoderID);
        configAngleEncoder();

        /* Angle Motor Config */
        mAngleMotor = new TalonFX(moduleConstants.angleMotorID);
        configAngleMotor();

        /* Drive Motor Config */
        mDriveMotor = new com.ctre.phoenix6.hardware.TalonFX(moduleConstants.driveMotorID);
        configDriveMotor();

        lastAngle = getState().angle;
    }

    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop){
        /* This is a custom optimize function, since default WPILib optimize assumes continuous controller which CTRE and Rev onboard is not */
        desiredState = CTREModuleState.optimize(desiredState, getState().angle); 
        setAngle(desiredState);
        setSpeed(desiredState, isOpenLoop);
    }

    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop){//motor speed
        if(isOpenLoop){
            double percentOutput = desiredState.speedMetersPerSecond / Constants.Swerve.maxSpeed;
            //mDriveMotor.set(ControlMode.PercentOutput, percentOutput);
            mDriveMotor.set(percentOutput);
        }
        else { //i sure hope we don't ever use close loop!
            double velocity = Conversions.MPSToFalcon(desiredState.speedMetersPerSecond, Constants.Swerve.wheelCircumference, Constants.Swerve.driveGearRatio);
            //mDriveMotor.set(ControlMode.Velocity, velocity, DemandType.ArbitraryFeedForward, feedforward.calculate(desiredState.speedMetersPerSecond));
        }
    }

    private void setAngle(SwerveModuleState desiredState){ //motor angle
        Rotation2d angle = (Math.abs(desiredState.speedMetersPerSecond) <= (Constants.Swerve.maxSpeed * 0.01)) ? lastAngle : desiredState.angle; //Prevent rotating module if speed is less then 1%. Prevents Jittering.
        
        mAngleMotor.set(ControlMode.Position, Conversions.degreesToFalcon(angle.getDegrees(), Constants.Swerve.angleGearRatio));
        lastAngle = angle;
    }

    private Rotation2d getAngle(){
        return Rotation2d.fromDegrees(Conversions.falconToDegrees(mAngleMotor.getSelectedSensorPosition(), Constants.Swerve.angleGearRatio));
    }

    public Rotation2d getCanCoder(){
        return Rotation2d.fromDegrees(angleEncoder.getAbsolutePosition());
    }

    public void resetToAbsolute(){
        double absolutePosition = Conversions.degreesToFalcon(getCanCoder().getDegrees() - angleOffset.getDegrees(), Constants.Swerve.angleGearRatio);
        mAngleMotor.setSelectedSensorPosition(absolutePosition);
    }

    private void configAngleEncoder(){        
        angleEncoder.configFactoryDefault();
        angleEncoder.configAllSettings(Robot.ctreConfigs.swerveCanCoderConfig);
    }

    private void configAngleMotor(){
        mAngleMotor.configFactoryDefault();
        mAngleMotor.configAllSettings(Robot.ctreConfigs.swerveAngleFXConfig);
        mAngleMotor.setInverted(Constants.Swerve.angleMotorInvert);
        mAngleMotor.setNeutralMode(Constants.Swerve.angleNeutralMode);
        resetToAbsolute();
    }

    /*private void configDriveMotor(){        
        //mDriveMotor.configFactoryDefault();
        //mDriveMotor.configAllSettings(Robot.ctreConfigs.swerveDriveFXConfig);
        mDriveMotor.setInverted(Constants.Swerve.driveMotorInvert);
        //mDriveMotor.setNeutralMode(Constants.Swerve.driveNeutralMode);
        //mDriveMotor.setSelectedSensorPosition(0);
    }
    */

    private void configDriveMotor(){
        TalonFXConfiguration fig = Robot.ctreConfigs.swerveDriveFX6Config;
        mDriveMotor.getConfigurator().apply(new TalonFXConfiguration());
        mDriveMotor.getConfigurator().apply(fig);         
        mDriveMotor.setInverted(Constants.Swerve.driveMotorInvert);

        mDriveMotor.setNeutralMode(NeutralModeValue.Brake);
        mDriveMotor.setPosition(0);
    }

    public SwerveModuleState getState(){
        return new SwerveModuleState(
            //Conversions.falconToMPS(mDriveMotor.getSelectedSensorVelocity(), Constants.Swerve.wheelCircumference, Constants.Swerve.driveGearRatio), getAngle()); 
            Conversions.falconToMPS(mDriveMotor.getVelocity().getValue(), Constants.Swerve.wheelCircumference, Constants.Swerve.driveGearRatio), getAngle()); 
    }

    public SwerveModulePosition getPosition(){
        return new SwerveModulePosition(
        Conversions.falconToMeters(mDriveMotor.getPosition().getValue(), Constants.Swerve.wheelCircumference, Constants.Swerve.driveGearRatio), getAngle());    
        //Conversions.falconToMeters(mDriveMotor.getSelectedSensorPosition(), Constants.Swerve.wheelCircumference, Constants.Swerve.driveGearRatio), getAngle());
    }
}