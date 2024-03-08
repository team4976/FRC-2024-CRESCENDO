package frc.robot.Objects;

public class Note {
    static double[] topLeft = {114, 285, 0};
    static double[] midLeft = {114, 228, 0};
    static double[] botLeft = {114, 171, 0};
    static double[] topMid = {325.625, 293.64, 0};
    static double[] upperMid = {325.625, 227.64, 0};
    static double[] trueMid = {325.625, 161.64, 0};
    static double[] lowerMid = {325.625, 95.64, 0};
    static double[] botMid = {325.625, 29.64, 0};
    static double[] topRight = {537.25, 285, 0};
    static double[] midRight = {537.25, 228, 0};
    static double[] botRight = {537.25, 171, 0};
        
    static double[][] idRef = {topLeft, midLeft, botLeft, topMid, upperMid, trueMid, lowerMid, botMid, topRight, midRight, botRight};
    static double[][] closest = {{1, 10}, {2, 10}, {3, 9}, {4, 9}, {5, 8}, {6, 1}, {7, 2}, {8, 2}, {9, 3}, {10, 3}, {11, 5}, {12, 9}, {13, 5}, {14, 5}, {15, 1}, {16, 5}};

    static int index = 0;

    public static void findClosest(double idLime, double idPosX, double idPosY, double horDriveTo, double verDriveTo){
        for (int i = 0; i < closest.length; i++){
            if (idLime == closest[i][0]){
                index = (int)closest[i][1];

                if (idRef[index][2] == 1){
                    if (idRef[index][1] > idRef[index+1][1]){
                        index += 1;
                    }else{
                        index -= 1;
                    }
                }
                horDriveTo = idPosX - idRef[index][0];
                verDriveTo = idPosY - idRef[index][1];
            }
        }
    }
        
    public static void removeFromList(int placement){
        
        if (idRef[placement][2] != 1){
            idRef[placement][2] = 1;
        }
    }
}
