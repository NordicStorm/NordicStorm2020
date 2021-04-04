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

        //Robot.shooter.setAutoSeekHeading(true, 0);
        Robot.shooter.setAutoTargetSeek(true);
    }
    @Override
    public void initializeCommands(){
        addSequential(new SetIntakeRunning(true));

        //Shoot from 6 = 180 inch.
        addSequential(new SetShooter(1645, 41));
        addSequential(new ShootBalls(3));
        addSequential(new WaitTime(0.25));
        addSequential(new MultiPartPath(0, true, true).addStraight(19, false, 0, 0.5).finalizePath());
        addSequential(new WaitTime(3));
        addSequential(new SetShooter(1970, 31));//2080, 27
        addSequential(new MultiPartPath(0, true, true).addStraight(14.2, true, 0, 0.5).finalizePath());
        addSequential(new ShootBalls(3));
        addSequential(new WaitTime(0.25));
        addSequential(new MultiPartPath(0, true, true).addStraight(15, false, 0, 0.5).finalizePath());
        addSequential(new WaitTime(3));
        addSequential(new SetShooter(2550, 25));
        addSequential(new MultiPartPath(0, true, true).addStraight(11, true, 0, 0.5).finalizePath());
        addSequential(new ShootBalls(3));
        addSequential(new WaitTime(0.25));
        addSequential(new MultiPartPath(0, true, true).addStraight(11, false, 0, 0.5).finalizePath());
        addSequential(new WaitTime(3));
        addSequential(new SetShooter(2400, 25));
        addSequential(new MultiPartPath(0, true, true).addStraight(6, true, 0, 0.5).finalizePath());
        addSequential(new ShootBalls(3));
        addSequential(new WaitTime(0.25));
        addSequential(new MultiPartPath(0, true, true).addStraight(6, false, 0, 0.5).finalizePath());
        addSequential(new WaitTime(3));
        addSequential(new SetShooter(1645, 41));
        addSequential(new MultiPartPath(0, true, true).addStraight(20, true, 0, 0.5).finalizePath());
        addSequential(new ShootBalls(3));
        addSequential(new SetIntakeRunning(false));

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