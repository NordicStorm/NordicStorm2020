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
public class AutonomousBarrelRacing extends AutoWithInit {


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
    public AutonomousBarrelRacing() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=COMMAND_DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=COMMAND_DECLARATIONS
 
    } 
    @Override
    public void initializeCommands(){
        //addSequential(new WaitTime(1));
        //addSequential(new TurnToAngle(90));
        //addSequential(new DriveForDistance(921, 0.5));
        addSequential(new DriveForDistance(10.5*913, 0.5));
        addSequential(new TurnToAngle(90));
        addSequential(new DriveForDistance(3*913, 0.5));
        addSequential(new TurnToAngle(180));
        addSequential(new DriveForDistance(3*913, 0.5));
        addSequential(new TurnToAngle(270));
        addSequential(new DriveForDistance(3*913, 0.5));
        addSequential(new TurnToAngle(0));
        addSequential(new DriveForDistance(9.5*913, 0.5));
        addSequential(new TurnToAngle(270));
        addSequential(new DriveForDistance(3*913, 0.5));
        addSequential(new TurnToAngle(180));
        addSequential(new DriveForDistance(3*913, 0.5));
        addSequential(new TurnToAngle(90));
        addSequential(new DriveForDistance(4*913, 0.5));
        addSequential(new TurnToAngle(45));
        addSequential(new DriveForDistance(10*913, 0.5));
        addSequential(new TurnToAngle(0));
        addSequential(new DriveForDistance(5*913, 0.5));
        addSequential(new TurnToAngle(315));
        addSequential(new DriveForDistance(2.5*913, 0.5));
        addSequential(new TurnToAngle(180));
        addSequential(new DriveForDistance(22*913, 0.5));
    }
}
