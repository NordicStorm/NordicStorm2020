
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

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

/**
 *
 */
public class ShootBalls extends Command {

   
    
    double shotsLeft;
    public ShootBalls(double numShots) {
        this.shotsLeft=numShots;
    }
    public ShootBalls() {
        this(3);
    }

    @Override
    protected void initialize() {
        
    }
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if(Robot.shooter.isReadyToShoot() && Robot.ballIntake.isBallAvailableToShoot()){
            Robot.ballIntake.feedBallToShooter();
            shotsLeft-=1;
        }
    }

    
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return shotsLeft<=0;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }

    
}
