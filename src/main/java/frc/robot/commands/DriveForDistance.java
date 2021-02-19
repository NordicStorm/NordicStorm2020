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
    
    double distance;
    double targetSpeed;
    double leftTargetPos;
    double rightTargetPos;
    /**
     * 
     * @param distance distance in encoder units. 913=1 ft
     * @param speed speed%. Between -1-1. Negative means backward
     */
    public DriveForDistance(double distance, double speed) {

        this.distance=distance;
        this.targetSpeed=speed;
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
        //Robot.drivetrain.resetEncoderPositions();
        leftTargetPos=Robot.drivetrain.getLeftEncoderDistance()-distance;
        rightTargetPos=Robot.drivetrain.getRightEncoderDistance()+distance;
        Robot.drivetrain.setOutsideControl(true);
        done=false;
    }

    boolean done=false;
    double pVal = 0.00015;

    double maxSpeed = 0.5;
    double minSpeed = 0.1;//0.25

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double leftPos=Robot.drivetrain.getLeftEncoderDistance();
        double rightPos=Robot.drivetrain.getRightEncoderDistance();
        double leftSpeed=calcSpeedNeeded(leftPos, leftTargetPos, -targetSpeed);
        double rightSpeed=calcSpeedNeeded(rightPos, rightTargetPos, targetSpeed);
        Robot.drivetrain.tankDriveDirect(leftSpeed, rightSpeed);
        if(getDistanceAway(leftPos, leftTargetPos, true)<=0
         && getDistanceAway(rightPos, rightTargetPos, false)<=0
         && Math.abs(Robot.drivetrain.getLeftEncoderVelocity())<12
         && Math.abs(Robot.drivetrain.getRightEncoderVelocity())<12){
            done=true;
        }
    }
    private double getDistanceAway(double currentPos, double targetPos, boolean reversed){
        System.out.println(currentPos);
        double distance;
        if(reversed){
            distance=currentPos-targetPos;

        }else{
            distance=targetPos-currentPos;

        }
        System.out.println(distance);
        return distance;
    }
    private double calcSpeedNeeded(double currentPos, double targetPos, double speed){
        double distance=0;
        //System.out.println("next");
        if(speed>0){
            distance=targetPos-currentPos;
        }else{
            distance=currentPos-targetPos;
        }
        if(distance>0){
            double driveSpeed=speed*distance*pVal;
            //System.out.println(driveSpeed);
            if (Math.abs(driveSpeed) > targetSpeed) {
                driveSpeed = Math.copySign(speed, driveSpeed);
            }
            if (Math.abs(driveSpeed) < minSpeed ) {
                driveSpeed = Math.copySign(minSpeed, driveSpeed);
            }
            //System.out.println(driveSpeed);
            return driveSpeed; 
        }else{
            return 0;
        }
    }
    
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return done;
        //return rotatePID.atSetpoint();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.drivetrain.setOutsideControl(false);
        Robot.drivetrain.drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}