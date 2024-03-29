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
public class TimedBallShoot extends AutoWithInit {

    public TimedBallShoot() {
        requires(Robot.drivetrain);
    
    }
    @Override
    protected void initialize() {

        Robot.shooter.setAutoSeekHeading(true, 0);
        //Robot.shooter.setAutoTargetSeek(true, -3);
    }
    @Override
    public void initializeCommands(){
        addSequential(new SetIntakeRunning(true));
        addSequential(new SetShooter(2000, 25));

        for(int i=0; i<111; i++){
            addSequential(new ShootBalls(3));
            addSequential(new WaitTime(0.25));
            addSequential(new MultiPartPath(0, true, true).addStraight(15, false, 0, 0.5).finalizePath());
            if(i==2){
                addSequential(new SetShooterTargeting(true, -2, false, 0));
            }
            if(i==2){
                addSequential(new SetShooterTargeting(true, -2, false, 0));
            }
            if(i==3){
                addSequential(new SetShooterTargeting(true, -2.5, false, 0));
            }
            if(i==4){
                addSequential(new SetShooterTargeting(true, -3, false, 0));
            }
            //addSequential(new WaitTime(3));
            addSequential(new WaitForButton(9));
            addSequential(new MultiPartPath(0, true, true).addStraight(15, true, 0, 0.5).finalizePath());
            
            //System.out.println("iter:"+i);
        }
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