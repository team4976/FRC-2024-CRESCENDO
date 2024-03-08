package frc.robot.Objects;

import java.util.HashMap;


import frc.robot.Subsystems.limelight;
import frc.robot.Objects.Note;

public class Feild {
  public void main(String[] args) {


    HashMap<Double, double[]> aprilTags = new HashMap<Double, double[]>();

limelight feildLimelight = new limelight();


double[] test = feildLimelight.BotPose();





// double[] finalPosArray = {finalPosX, finalPosY, finalPosZ};//moved to the end.


    aprilTags.put(1.0, new double[]{15.07, 0.24, 0});
    aprilTags.put(2.0, new double[]{16.18, 0.88, 0});
    aprilTags.put(3.0, new double[]{16.57, 4.98, 0});
    aprilTags.put(4.0, new double[]{16.57, 5.54, 0});
    aprilTags.put(5.0, new double[]{14.70, 8.20, 0});
    aprilTags.put(6.0, new double[]{1.84, 8.20, 0});
    aprilTags.put(7.0, new double[]{-0.03, 5.54, 0});
    aprilTags.put(8.0, new double[]{-0.03, 4.98, 0});
    aprilTags.put(9.0, new double[]{0.35, 0.88, 0});
    aprilTags.put(10.0, new double[]{1.46, 0.24, 0});
    aprilTags.put(11.0, new double[]{11.90, 3.71, 0});
    aprilTags.put(12.0, new double[]{11.90, 4.49, 0});
    aprilTags.put(13.0, new double[]{11.22, 4.10, 0});
    aprilTags.put(14.0, new double[]{5.32, 4.10, 0});
    aprilTags.put(15.0, new double[]{4.64, 4.49, 0});
    aprilTags.put(16.0, new double[]{4.64, 3.71, 0});


// new double[aprilTags.get(2).length()] test =aprilTags.get(2);


double botPoseX = test[0];//botPose
double botPoseY = test[1];
double botPoseZ = test[2];

double aprilTagX = aprilTags.get(feildLimelight.ID())[0];//aprilTag
double aprilTagY = aprilTags.get(feildLimelight.ID())[1];
double aprilTagZ = aprilTags.get(feildLimelight.ID())[2];

double finalPosX = 0.0;//final Position
double finalPosY = 0.0;
double finalPosZ = 0.0;




aprilTags.get(feildLimelight.ID());




finalPosX = botPoseX - aprilTagX;
finalPosY = botPoseY - aprilTagY;
finalPosZ = botPoseZ - aprilTagZ;


double[] finalPosArray = {finalPosX, finalPosY, finalPosZ};

System.out.println("You are seeing April Tag: ");
System.out.println("X: " +finalPosArray[0] + " , Y: " + finalPosArray[1] + " , Z: "  + finalPosArray[2]);

// System.out.println(aprilTags.get(feildLimelight.ID()).toString());

}   
}
