package frc.robot.Commands;//this is a command for telling were the bot is off april tags from the serounding area using a spin mechanic.

import edu.wpi.first.math.geometry.Translation2d;//this should work, have not had time to test.
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.limelight;
import frc.robot.Swerve.Subsystem.Swerve;//all the swerve stuff including the drive stuff






public class AutoSonar  extends Command{
    
    Swerve s_Swerve;
    limelight limelight;

    Translation2d test = new Translation2d();

    public AutoSonar(Swerve s_Swerve, limelight limelight){
        this.limelight = limelight;
        this.s_Swerve = s_Swerve;
    }

    @Override
    public void initialize() {
      // TODO Auto-generated method stub
      super.initialize();

    s_Swerve.drive(test, 180, false, false);
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
      super.execute();
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
     return limelight.V() >= 1;
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);

        s_Swerve.drive(test, 0.0, false, false);
    }

}
