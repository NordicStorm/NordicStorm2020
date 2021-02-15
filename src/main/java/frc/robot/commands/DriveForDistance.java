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
import frc.robot.Robot;

/**
 *
 */
public class DriveForDistance extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    
    int distance;
    double speed;
    /**
     * 
     * @param distance distance in enocder units. 913=1 ft
     * @param speed speed%. Between -1-1. Negative means backward
     */
    public DriveForDistance(int distance, double speed) {

        this.distance=distance;
        this.speed=speed;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drivetrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    @Override
    protected void initialize() {
        Robot.drivetrain.setEncMode(true);
        Robot.drivetrain.resetEncoderPositions();
        Robot.drivetrain.setOutsideControl(true);
    }

    
    double pVal = 0.023;

    double maxSpeed = 0.5;
    double minSpeed = 0.25;

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double leftPos=Robot.drivetrain.getLeftEncoderDistance();
        double rightPos=Robot.drivetrain.getRightEncoderDistance();
        Robot.drivetrain.tankDriveDirect(calcSpeedNeeded(leftPos, distance), calcSpeedNeeded(rightPos, distance));
    }
    private void calcSpeedNeeded(double currentPos, double targetPos){
        if(currentPos<targetPos){
            return speed;
        }else{
            return 0;
        }
    }
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
        //return rotatePID.atSetpoint();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.drivetrain.drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
