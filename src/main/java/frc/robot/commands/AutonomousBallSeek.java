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
public class AutonomousBallSeek extends AutoWithInit {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
    public AutonomousBallSeek() {

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
        
        //Put the common, first stage stuff here.
        //doRedA();
        addSequential(new CheckAndSplit(this, 0));
        /*MultiPartPath path = new MultiPartPath(0);

        path.addStraight(9, false);

        //path.addGridArc(270, false);
        path.addRawArc(359, 0.051, 0.48, true);
        path.addStraight(7.5, false);
        path.addRawArc(90, 0.051, 0.48, false);

        addSequential(path.finalizePath());

        addSequential(new CheckAndSplit(this, 0));*/

    }

    public void checkAndSplit(int whichCheck){
        //This is used to sequence checks and forking paths.
        if(whichCheck==0){
            //if(pixy_on_right())
            doRedA();
            //else
            //add more other driving, then 
            addSequential(new CheckAndSplit(this, 1));

        }else if(whichCheck==1){
            //second check
        }
    }
    public void doRedA(){
        CommandGroup group=new CommandGroup();
        addSequential(new SetIntakeRunning(true));
        addSequential(new FollowBall(false, true));
        addSequential(new TurnToAngle(26, 5));
        addSequential(new FollowBall(false, true));
        group.start();
        
        
       /*
        addSequential(new FollowBall(false, true));
        addSequential(new TurnToAngle(0));
        MultiPartPath path = new MultiPartPath(0);
        path.addGridArc(26.5, true);
        path.addStraight(1.5, false);
        addSequential(path.finalizePath());
        addSequential(new FollowBall(false, true));
        addSequential(new TurnToAngle(26.5));
        path = new MultiPartPath(26.5);
        path.addGridArc(270, false);
        path.addStraight(5, false);
        addSequential(path.finalizePath());
        addSequential(new FollowBall(false, true));
        addSequential(new TurnToAngle(0));
        path = new MultiPartPath(0);
        path.addStraight(6, false);
        addSequential(new SetIntakeRunning(false));*/
    }

    public void doBlueA(){

    }

    public void doRedB(){

    }

    public void doBlueB(){

    }
}
