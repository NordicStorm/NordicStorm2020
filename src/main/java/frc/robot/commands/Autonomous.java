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
        MultiPartPath path = new MultiPartPath(0);
        path.addStraight(9, true);
        path.addGridArc(90, true, true);
        path.addStop();
        path.addGridArc(0, false, true);
        //path.addStraight(4, true);

        //path.addGridArc(270, false);
        //path.addRawArc(1, 0.051, 0.48, true);
        //path.addStraight(7.5, false);
        //path.addRawArc(90, 0.051, 0.48, false);

        addSequential(path.finalizePath());

        /*addSequential(new DriveForDistance(4*913, 0.5));
       
       addSequential(new TurnToAngle(270));
       addSequential(new DriveForDistance(5*913, 0.5));
       addSequential(new TurnToAngle(0));
       addSequential(new DriveForDistance(15*913, 1));
       addSequential(new TurnToAngle(90)); 
       addSequential(new DriveForDistance(5*913, 0.5));
       addSequential(new TurnToAngle(0));
       addSequential(new DriveForDistance(5*913, 0.5));
       addSequential(new TurnToAngle(270));
       addSequential(new DriveForDistance(5*913, 0.5));
       addSequential(new TurnToAngle(180));
       addSequential(new DriveForDistance(5*913, 0.5));
       addSequential(new TurnToAngle(90));
       addSequential(new DriveForDistance(5*913, 0.5));
       addSequential(new TurnToAngle(180));
       addSequential(new DriveForDistance(15*913, 1));
       addSequential(new TurnToAngle(270));
       addSequential(new DriveForDistance(5*913, 0.5));
       addSequential(new TurnToAngle(180));
       addSequential(new DriveForDistance(5*913, 0.5));*/



    }
}
