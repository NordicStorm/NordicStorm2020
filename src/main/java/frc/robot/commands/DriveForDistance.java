
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
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

/**
 *
 */
public class DriveForDistance extends CommandGroup {

    double leftTargetPos;
    double rightTargetPos;

    double distance;
    boolean reversed;
    double mainSpeed;

    boolean done = false;
    double pVal = 0.0002; // 0.00015
    double minSpeed = 0.1;// 0.25

    /**
     * 
     * @param distance distance in encoder units. 913=1 ft
     * @param speed    speed%. Between -1-1. Negative means backward
     */
    public DriveForDistance(double distance, double speed) {
        if (speed < 0) {
            distance *= -1;
        }
        reversed = speed < 0;

        this.distance = distance;
        this.mainSpeed = speed;
        requires(Robot.drivetrain);

    }

    @Override
    protected void initialize() {
        SmartDashboard.putString("currentCommand", "driveForDistance("+distance+")");

        Robot.drivetrain.setEncMode(true);
        Robot.drivetrain.setSuperPMode(true);
        // Robot.drivetrain.resetEncoderPositions();
        leftTargetPos = Robot.drivetrain.getLeftEncoderDistance() - distance;
        rightTargetPos = Robot.drivetrain.getRightEncoderDistance() + distance;
        Robot.drivetrain.setOutsideControl(true);
        done = false;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double leftPos = Robot.drivetrain.getLeftEncoderDistance();
        double rightPos = Robot.drivetrain.getRightEncoderDistance();
        // System.out.println("left");
        double leftSpeed = calcSpeedNeeded(leftPos, leftTargetPos, true);
        // System.out.println("right");
        double rightSpeed = calcSpeedNeeded(rightPos, rightTargetPos, false);
        Robot.drivetrain.tankDriveDirect(leftSpeed, rightSpeed);
        double avgDist = (getDistanceAway(leftPos, leftTargetPos, !reversed)
                + getDistanceAway(rightPos, rightTargetPos, reversed)) / 2;
        if (avgDist < 0 && Math.abs(Robot.drivetrain.getLeftEncoderVelocity()) < 12
                && Math.abs(Robot.drivetrain.getRightEncoderVelocity()) < 12) {
            done = true;
        }
    }

    private double getDistanceAway(double currentPos, double targetPos, boolean reversed) {
        double distance;
        if (reversed) {
            distance = currentPos - targetPos;

        } else {
            distance = targetPos - currentPos;

        }
        // System.out.println("getDistAway:"+distance);

        return distance;
    }

    private double calcSpeedNeeded(double currentPos, double targetPos, boolean thisSideReversed) {
        int reverseCorrection = thisSideReversed ? -1 : 1;
        double correctMainSpeed = mainSpeed * reverseCorrection;
        double distance = getDistanceAway(currentPos, targetPos, correctMainSpeed < 0); // can't just use thisSideReversed,
                                                                                         // depends on if left side and
                                                                                         // speed backward

        // System.out.println("next");

        /*
         * System.out.println("dist:"+distance);
         * System.out.println("current:"+currentPos);
         * System.out.println("target:"+targetPos);
         * System.out.println("correctSpeed:"+correctStartSpeed);
         * System.out.println("rev:"+reversed);
         */

        if (distance > 0) {
            double driveSpeed;

            driveSpeed = correctMainSpeed * distance * pVal;
            if (Math.abs(driveSpeed) > mainSpeed) {
                driveSpeed = Math.copySign(mainSpeed, driveSpeed);
            }
            if (Math.abs(driveSpeed) < minSpeed) {
                driveSpeed = Math.copySign(minSpeed, driveSpeed);
            }

            // System.out.println("drivespeed:"+driveSpeed);

            return driveSpeed;
        } else {
            return 0;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.drivetrain.setSuperPMode(false);
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
