// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 *
 */
public class StartAutoShooter extends InstantCommand {

    int whichOne;
    public StartAutoShooter(int whichOne) {
        requires(Robot.drivetrain);
        this.whichOne=whichOne;
    }
    @Override
    protected void initialize() {
        AutoWithInit auto=null;
        if(whichOne==0){
            auto = new AutoBallShoot();
        }else if (whichOne==1){
            auto = new TimedBallShoot();
        }else if (whichOne==2){
            auto = new AutoSnowRemoval();
        }
        auto.initializeCommands();
        auto.start();
    }
    
}
