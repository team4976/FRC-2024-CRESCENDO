package frc.robot.Swerve.Subsystem;

import frc.robot.Swerve.Constants;
import frc.robot.Swerve.SwerveModule;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swerve extends SubsystemBase {
    public SwerveDriveOdometry swerveOdometry;
    public SwerveModule[] mSwerveMods;
    // public Pigeon2 gyro;
    public static AHRS gyro;
int index =0;
boolean teleopActive = true; 
    public Swerve() {
        // gyro = new Pigeon2(Constants.Swerve.pigeonID);
        gyro = new AHRS();
        // gyro.configFactoryDefault();
      // gyro.calibrate();

        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        zeroGyro();

        mSwerveMods = new SwerveModule[] {
            new SwerveModule(1, Constants.Swerve.Mod1.constants),
            new SwerveModule(2, Constants.Swerve.Mod2.constants),
            new SwerveModule(4, Constants.Swerve.Mod3.constants),
            new SwerveModule(3, Constants.Swerve.Mod4.constants)
        };

        /* By pausing init for a second before setting module offsets, we avoid a bug with inverting motors.
         * See https://github.com/Team364/BaseFalconSwerve/issues/8 for more info.
         */
        Timer.delay(1.0);
       resetModulesToAbsolute();

        swerveOdometry = new SwerveDriveOdometry(Constants.Swerve.swerveKinematics, getYaw(), getModulePositions());
    }

    public Command teleopToggle(){
        return runOnce( () -> { 
            System.out.println("teleopActive:"+teleopActive);
            if (!teleopActive){
                teleopActive = true;
            }
            else {
                teleopActive = false; 
            }
             
        });
    }
    

    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
        //SmartDashboard.putNumber("TEST2", index);
        index++;
        if (teleopActive){
        SwerveModuleState[] swerveModuleStates =
            Constants.Swerve.swerveKinematics.toSwerveModuleStates(
                fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(// the ? and : are shortform for an if else statement. boolean ? (if true) : (else)
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation, 
                                    getYaw()
                                )
                                : new ChassisSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation)
                                );
                                
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);

        SmartDashboard.putNumber("MaxSpeed",  Constants.Swerve.maxSpeed);
        
        
            for(SwerveModule mod : mSwerveMods){
                mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
            }
        }
/* 
        SmartDashboard.putNumber("transationx", translation.getX());
        SmartDashboard.putNumber("translationy", translation.getY());
        SmartDashboard.putNumber("rotation", rotation);
        SmartDashboard.putBoolean("fieldrelative", fieldRelative);
        SmartDashboard.putBoolean("isopen", isOpenLoop);
        */
        
        
    }    
    
    public void driveLime(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
        //SmartDashboard.putNumber("TEST2", index);
        index++;
        //System.out.println(teleopActive); 
        System.out.println("L");
        SwerveModuleState[] swerveModuleStates =
            Constants.Swerve.swerveKinematics.toSwerveModuleStates(
                fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(// the ? and : are shortform for an if else statement. boolean ? (if true) : (else)
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation, 
                                    getYaw()
                                )
                                : new ChassisSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation)
                                );
                                
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);
        
        
            for(SwerveModule mod : mSwerveMods){
                mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
            }
        
        
    }    

    /* Used by SwerveControllerCommand in Auto */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);
        
        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
        }
    }    

    public Pose2d getPose() {
       
        return swerveOdometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        swerveOdometry.resetPosition(getYaw(), getModulePositions(), pose);
        
    }

    public SwerveModuleState[] getModuleStates(){
        SwerveModuleState[] states = new SwerveModuleState[4];
        for(SwerveModule mod : mSwerveMods){
            states[mod.moduleNumber] = mod.getState();
        }
      
        return states;
    }

    public SwerveModulePosition[] getModulePositions(){
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for(SwerveModule mod : mSwerveMods){
            positions[mod.moduleNumber] = mod.getPosition();
        }
        
        return positions;
    }

    public void zeroGyro(){
        SmartDashboard.putBoolean("zerogyro", true);
        gyro.zeroYaw();
    }

    public static Rotation2d getYaw() {
        //System.out.println((Constants.Swerve.invertGyro) ? Rotation2d.fromDegrees(360 - gyro.getYaw()) : Rotation2d.fromDegrees(gyro.getYaw()));
        return (Constants.Swerve.invertGyro) ? Rotation2d.fromDegrees(360 - gyro.getYaw()) : Rotation2d.fromDegrees(gyro.getYaw());
                                             
    }

    public void resetModulesToAbsolute(){
        for(SwerveModule mod : mSwerveMods){
            mod.resetToAbsolute();
        }
        SmartDashboard.putBoolean("resettoabsolute", true);
    }

    public Command testButtons(String test){
        return runOnce(()->{
            SmartDashboard.putString("axis", test);
        });
    }
    @Override
    public void periodic(){
        swerveOdometry.update(getYaw(), getModulePositions());  

        /*for(SwerveModule mod : mSwerveMods){
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Cancoder", mod.getCanCoder().getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Integrated", mod.getPosition().angle.getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond);    
        }*/

        SmartDashboard.putBoolean("TELEOP ACTIVE", teleopActive);
    }

}