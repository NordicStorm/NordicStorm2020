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

import java.util.List;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
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
            List<PixyObject> obs = Robot.pixy.readObjects();
            if(obs.size()>0){
                PixyObject ball = obs.get(0);
                System.out.println("ballx:"+ball.x);
                if(ball.x>250){
                    doRedB();
                } else{
                   doRedA();
                }
            }else{
                System.out.println("none");
                CommandGroup group=new CommandGroup();
                MultiPartPath path = new MultiPartPath(0);
                path.addStraight(0.5, false);

                //" This is the part where it goes over to look for the balls of the blue runs.
                //path.addGridArc(270, false);
                path.addRawArc(45, 0.051, 0.48, true);
                path.addStraight(4, false);
                path.addGridArc(0, false);
                group.addSequential(path.finalizePath());
                group.addSequential(new TurnToAngle(0));
                group.start();
            }
           
            //add more other driving, then 
           // addSequential(new CheckAndSplit(this, 1));

        }else if(whichCheck==1){
            //second check
        }
    }
    public void doRedA(){
        CommandGroup group=new CommandGroup();
        group.addSequential(new SetIntakeRunning(true));
        group.addSequential(new FollowBall(false, true));
        //addSequential(new TurnToAngle(26, 5));
        /*group.addSequential(new MultiPartPath(0, false, false)
            .addRawArc(26, 0, 1, true)
            .finalizePath());
*/
        group.addSequential(new WaitTime(0.25));
        group.addSequential(new FollowBall(false, true));
        //addSequential(new TurnToAngle(270, 5));
        group.addSequential(new MultiPartPath(26, false, false)
            .addRawArc(340, -0.5, 0.5, false)
            .finalizePath());

        group.addSequential(new FollowBall(false, true));
        //group.addSequential(new TurnToAngle(0, 5));
        group.addSequential(new MultiPartPath(0, false, true)
            .addRawArc(0, -0.5, 0.5, true)
            .addStraight(12, false)
            //.atMaxSpeed()
            .finalizePath());
        group.start();
        //addSequential(new MultiPartPath(0).finalizePath());

        
        
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

        CommandGroup group=new CommandGroup();
        group.addSequential(new SetIntakeRunning(true));
        group.start();
    }

    public void doBlueB(){

    }
}
