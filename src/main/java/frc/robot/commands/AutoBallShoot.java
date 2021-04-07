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
        //Robot.shooter.setAutoTargetSeek(true, 5);
    }
    @Override
    public void initializeCommands(){
        addSequential(new SetIntakeRunning(true));

        //Great run: :Le4che off charger, 2in left of box. Also good 12.46
        addSequential(new SetShooter(1615, 41));
        addSequential(new ShootBalls(3));
        addSequential(new WaitTime(0.25));
        addSequential(new MultiPartPath(0, true, true).addStraight(19, false, 0, 0.5).finalizePath());
        addSequential(new WaitForButton(9));
        addSequential(new SetShooter(2000, 25));
        addSequential(new MultiPartPath(0, true, true).addStraight(14.2, true, 0, 0.5).finalizePath());
        addSequential(new ShootBalls(3));
        addSequential(new WaitTime(0.25));
        addSequential(new MultiPartPath(0, true, true).addStraight(15, false, 0, 0.5).finalizePath());
        addSequential(new WaitForButton(9));
        addSequential(new SetShooter(2100, 25));
        addSequential(new MultiPartPath(0, true, true).addStraight(11, true, 0, 0.5).finalizePath());
        addSequential(new ShootBalls(3));
        addSequential(new WaitTime(0.25));
        addSequential(new MultiPartPath(0, true, true).addStraight(11, false, 0, 0.5).finalizePath());
        addSequential(new WaitForButton(9));
        addSequential(new SetShooterTargeting(true, -2, false, 0));
        addSequential(new SetShooter(2300, 25));
        addSequential(new MultiPartPath(0, true, true).addStraight(6, true, 0, 0.5).finalizePath());
        addSequential(new ShootBalls(3));
        addSequential(new WaitTime(0.25));
        addSequential(new MultiPartPath(0, true, true).addStraight(6, false, 0, 0.5).finalizePath());
        addSequential(new WaitForButton(9));
        addSequential(new SetShooterTargeting(true, -4, false, 0));
        addSequential(new SetShooter(1615, 41));
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