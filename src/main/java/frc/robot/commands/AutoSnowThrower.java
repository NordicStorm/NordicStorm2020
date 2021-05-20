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
import frc.robot.Robot;
import frc.robot.subsystems.*;

/**
 *
 */
public class AutoSnowThrower extends AutoWithInit {

    public AutoSnowThrower() {
        requires(Robot.drivetrain);
    
    }
    @Override
    protected void initialize() {
        //Robot.drivetrain.resetHeading();
        //Robot.shooter.setAutoSeekHeading(true, 0);
        //Robot.shooter.setAutoTargetSeek(true, 5);
    }
    @Override
    public void initializeCommands(){
        double startAng = 36;
        addSequential(new SetIntakeRunning(true));
        MultiPartPath path = new MultiPartPath(startAng);
        path.addStraight(8.5, false);
        path.addRawArc(-33, 0.051, 0.4025, false);
        path.addBallGrab();
        path.addPivot(150, 0.25, false, true);
        path.addStraight(5.5, false, 140);
        path.addRawArc(110, 0.051, 0.48, false);
        path.addBallGrab();
        path.addStraight(2, false);
        path.addRawArc(250, 0.051, 0.4025, true);
        path.addBallGrab();
        path.addPivot(0, 0.25, true, true);
        path.addStraight(11, false); //long straight 1
        path.addRawArc(285, 0.051, 0.4025, false);
        path.addStraight(5, false); //first 8 line of figure 8
        path.addRawArc(140, 0.06, 0.39, true);
        path.addStraight(2, false);
        path.addRawArc(270, 0.06, 0.39, false);
        path.addRawArc(110, 0.06, 0.39, false);
        path.addRawArc(180, 0.051, 0.4025, true);
        path.addStraight(9, false);
        path.addRawArc(235, 0.051, 0.4025, true);
        path.addRawArc(100, 0.056, 0.37, false);



        //addSequential(new SetIntakeRunning(true));
        addSequential(path.finalizePath());

    }
    @Override
    protected void end() {
        Robot.ballIntake.setLongRunningIntake(false);
        Robot.shooter.setAutoSeekHeading(false, 0);
    }
    @Override
    protected void interrupted(){
        end();
    }   
}