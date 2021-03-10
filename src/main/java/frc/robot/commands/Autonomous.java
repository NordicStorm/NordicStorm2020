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


import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.*;

/**
 *
 */
public class Autonomous extends AutoWithInit {


    public Autonomous() {

    } 
    @Override
    public void initializeCommands(){
        //addSequential(new WaitTime(1));
        addSequential(new DriveForDistance(4*913, 0.5));
        addSequential(new TurnToAngle(270));
        addSequential(new SetIntakeRunning(true));
        addSequential(new FollowBall(false, true));
        addSequential(new WaitTime(1.5));
        addSequential(new SetIntakeRunning(false));
        addSequential(new TurnToAngle(270));





    }
}
