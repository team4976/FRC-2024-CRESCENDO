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
    NetworkTableEntry tid = table.getEntry("tid");
    NetworkTableEntry botpose = table.getEntry("botpose");
    double x; 
    double y;
    double area;
    double v;
    double th; 
    double id; 
    double[] position;

    public limelight(){

    }

    //callable values for other classes
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
    public double ID(){
        return id; 
    }
    public double Position(int item){
        return position[item];
    }


//this is needed for some of my code, i have not had time to test any of this. Jamie
     /** 
    * @return {x,y,z}
    */
    public double[] botpose(){//This stuff is from Jamie`s Code
        //swap();//this is not in this one?
        return botpose.getDoubleArray(new double[]{0,0,0});
    }

     ///TEST GOD TEST THIS      this might not work, we have not had time to test.
     public boolean isvaildTarget(){//This stuff is from Jamie`s Code
        startTime = System.currentTimeMillis();
        saveTime = startTime;
        while (System.currentTimeMillis()-startTime < 5){
            if(V() == 1){
                flag = true;
            }
            else {
                flag = false;
                saveTime = System.currentTimeMillis();
            }

            if(System.currentTimeMillis() - saveTime > 3 && flag){
                return true;
            }
            // else if(saveTime-startTime > 3){
            //     return false;
            // }
        }
        return false;
    }

    
    @Override
    public void periodic(){
        //read values periodically
        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        v = tv.getDouble(0.0); 
        area = ta.getDouble(0.0);
        th = thor.getDouble(0.0);
        id = tid.getDouble(0.0); 
    }
}
