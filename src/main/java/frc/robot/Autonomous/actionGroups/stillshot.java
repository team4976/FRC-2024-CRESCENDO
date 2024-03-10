package frc.robot.Autonomous.actionGroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotConstants;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.index;

public class stillshot extends SequentialCommandGroup {// basic stationary fire cmd for use elsewhere

    public stillshot(Shooter s_Shooter, index s_Index){

addCommands(      
new InstantCommand(()-> s_Shooter.shootAuto()),//turns on shooter motors
new WaitCommand(0.6),//waits till time elapsed //may change to wait untill based off of voltage
new InstantCommand(()-> s_Index.runIndexIn()),// indexes note
new WaitCommand(0.3),
new InstantCommand(()-> RobotConstants.motorsZero())//shuts off non drive motors
);

    }
}
