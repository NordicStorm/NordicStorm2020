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
public class AutoBallShoot extends AutoWithInit {

    public AutoBallShoot() {
        requires(Robot.drivetrain);
    
    }
    @Override
    protected void initialize() {

        Robot.shooter.setAutoSeekHeading(true, 0);
    }
    @Override
    public void initializeCommands(){
        
        //Shoot from 6 = 180 inch.
        /*addSequential(new SetShooter(1775, 41));
        addSequential(new ShootBalls());
        addSequential(new WaitTime(0.25));*/
        addSequential(new MultiPartPath(0, true ,true)
        .addStraight(5.8, false)
        .addStop()
        
        .addStraight(5.8, true)
        .finalizePath());
        /*addSequential(new MoveToDistanceFromTarget(250, true));//122
        addSequential(new StopMovement());
        addSequential(new WaitTime(0.5));
        addSequential(new MoveToDistanceFromTarget(180, false));
        addSequential(new StopMovement());*/
        /*addSequential(new WaitTime(0.5));
        addSequential(new MoveToDistanceFromTarget(250, true));
        addSequential(new StopMovement());
        addSequential(new WaitTime(0.5));
        addSequential(new MoveToDistanceFromTarget(180, false));
        addSequential(new StopMovement());
        addSequential(new WaitTime(0.5));
        addSequential(new MoveToDistanceFromTarget(250, true));
        addSequential(new StopMovement());
        addSequential(new WaitTime(0.5));
        addSequential(new MoveToDistanceFromTarget(180, false));
        addSequential(new StopMovement());
        addSequential(new WaitTime(0.5));
        addSequential(new MoveToDistanceFromTarget(250, true));
        addSequential(new StopMovement());
        addSequential(new WaitTime(0.5));
        addSequential(new MoveToDistanceFromTarget(180, false));
        addSequential(new StopMovement());
        addSequential(new WaitTime(0.5));
        addSequential(new MoveToDistanceFromTarget(250, true));
        addSequential(new StopMovement());
        addSequential(new WaitTime(0.5));
        addSequential(new MoveToDistanceFromTarget(180, false));
        addSequential(new StopMovement());
        addSequential(new WaitTime(0.5));
        addSequential(new MoveToDistanceFromTarget(250, true));
        addSequential(new StopMovement());
        addSequential(new WaitTime(0.5));
        addSequential(new MoveToDistanceFromTarget(180, false));*/
    }
    @Override
    protected void end() {
        Robot.shooter.setAutoSeekHeading(false, 0);
    }
    @Override
    protected void interrupted(){
        end();
    }   
}