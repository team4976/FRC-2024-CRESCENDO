package frc.robot.Subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class limelight extends SubsystemBase {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");
    NetworkTableEntry tv = table.getEntry("tv");
    NetworkTableEntry thor = table.getEntry("thor");
    double x; 
    double y;
    double area;
    double v;
    double th; 

    public limelight(){

    }

    public double X(){
        return x; 
    }
    public double Y(){
        return y;
    }
    public double Area(){
        return area;
    }
    public double V(){
        return v; 
    }
    public double THor(){
        return th; 
    }

    
    @Override
    public void periodic(){
        //read values periodically
        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        v = tv.getDouble(0.0); 
        area = ta.getDouble(0.0);
        th = thor.getDouble(0.0); 
    }
}
