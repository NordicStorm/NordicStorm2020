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


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PARAMETERS
    public Autonomous() {

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
        MultiPartPath path = new MultiPartPath(0);
 /*       path.addStraight(1.3, false);

        //path.addGridArc(270, false);
        path.addRawArc(290, 0.051, 0.48, false);
        path.addGridArc(0, true);
        path.addStraight(10 , false);//10.5

        path.addGridArc(73, true);

        path.addGridArc(359, false);
        path.addGridArc(90, false);
        path.addGridArc(180,true);
        path.addStraight(10.2 , false);
        path.addGridArc(270,true);
        path.addGridArc(180,false);*/
        path.addStraight(3.5, false);
        path.addGridArc(90, true);
        path.addGridArc(0, false);
        //path.addStraight(5, false);
        //path.addGridArc(270, true);
        //path.addStraight(8.5, false);
        //path.addGridArc(0, true);

        addSequential(path.finalizePath());
        addSequential(new TurnToAngle(0));



    }
}
