package frc.robot.Autonomous;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Swerve.Subsystem.Swerve;
import frc.robot.Subsystems.intake;
import frc.robot.Subsystems.limelight;
import frc.robot.Objects.Note;

public class findNote extends Command{
    double diffH;
    double diffV;
    int index;
    int indexH;
    int indexV;
    private Swerve a_Swerve; 
    private limelight a_l;
    PIDController pidT = new PIDController(0.015, 0, 0);
    PIDController pidF = new PIDController(0.015, 0, 0);
    PIDController pidH = new PIDController(0.015, 0, 0); 

    intake Intake;

    public void runIntake(intake Intake){
        this.Intake = Intake;
        addRequirements(Intake);
    }
    
    @Override
    public void initialize(){
        //a_Swerve.teleopToggle(); 
        a_l = new limelight();
        pidT.setTolerance(5.0);
        pidF.setTolerance(5.0);
        pidH.setTolerance(5.0); 
    }

    @Override
    public void execute(){
        // find closest note
        Note.findClosest(a_l.ID(), a_l.Position(0), a_l.Position(1), diffH, diffV);
        Note.removeFromList(index);
        //System.out.println(noteLocation[index][0]);
        //System.out.println(noteLocation[index][1]);
        
        a_Swerve.drive(new Translation2d(diffH, diffV), 0, true, true);
    }

    public void end(){
        Intake.runIntake();
        super.initialize();
        //a_Swerve.teleopToggle();
        //TODO put back teleoptoggles
    }

    @Override
    public boolean isFinished() {
        return true; 
    }
}
