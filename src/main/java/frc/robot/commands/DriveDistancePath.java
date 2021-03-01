
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
import frc.robot.Util;
import frc.robot.subsystems.Drivetrain;

/**
 *
 */
public class DriveDistancePath extends PathSection {

    double leftTargetPos;
    double rightTargetPos;

    double totalDistance;
    boolean reversed;
    double startSpeed;
    double mainSpeed;
    double endSpeed;
    double startSpeedDonePoint=0;
    double mainSpeedDonePoint=0;

    boolean done = false;
    double minSpeed = 0.1;// 0.25
    double keepStraightAngle=0;

    /**
     * 
     * @param distance distance in encoder units. 913=1 ft
     */
    public DriveDistancePath(double distance, boolean backward) {
        if (backward) {
            distance *= -1;
        }
        reversed = backward;

        this.totalDistance = distance;
        this.mainSpeed = 1;
        requires(Robot.drivetrain);

    }

    @Override
    protected void initialize() {
        Robot.drivetrain.setEncMode(true);
        Robot.drivetrain.setSuperPMode(true);
        // Robot.drivetrain.resetEncoderPositions();
        leftTargetPos = Robot.drivetrain.getLeftEncoderDistance() - totalDistance;
        rightTargetPos = Robot.drivetrain.getRightEncoderDistance() + totalDistance;
        Robot.drivetrain.setOutsideControl(true);
        done = false;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double leftPos = Robot.drivetrain.getLeftEncoderDistance();
        double rightPos = Robot.drivetrain.getRightEncoderDistance();
        double distanceAway=Math.min(getDistanceAway(leftPos, leftTargetPos, !reversed), getDistanceAway(rightPos, rightTargetPos, reversed));
        double percentDone=Util.clamp(1-(distanceAway/Math.abs(totalDistance)), 0, 1);
        double driveSpeed=mainSpeed;
        System.out.println(percentDone);
        if(percentDone>mainSpeedDonePoint){
            driveSpeed = Util.map(percentDone, mainSpeedDonePoint, 1, mainSpeed, endSpeed);
        }
        if(percentDone<startSpeedDonePoint){
            driveSpeed = Util.map(percentDone, 0, startSpeedDonePoint, startSpeed, mainSpeed);
        }

        if (Math.abs(driveSpeed) < minSpeed) {
            driveSpeed = Math.copySign(minSpeed, driveSpeed);
        }
        double leftSpeed = driveSpeed;
        double rightSpeed = driveSpeed;

        double currentAngle=Robot.drivetrain.getAngle();
        double angleDiff=Drivetrain.angleDiff(currentAngle, keepStraightAngle);
        double useDiff=Util.absClamp(angleDiff*0.1, 1);
        double adjustment=Util.absClamp(useDiff*0.8*(driveSpeed), driveSpeed);//0.01
        leftSpeed+=adjustment;
        rightSpeed-=adjustment;
        System.out.println("drivespeed:"+driveSpeed);

        System.out.println("err:"+angleDiff);

        System.out.println("adj:"+adjustment);

        System.out.println("left:"+leftSpeed);
        System.out.println("right:"+rightSpeed);

        Robot.drivetrain.tankDriveDirect(-leftSpeed, rightSpeed);
        if (distanceAway < 0) {
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
   
    

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        // Robot.drivetrain.setSuperPMode(false);
        // Robot.drivetrain.setOutsideControl(false);
        // Robot.drivetrain.drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }

    @Override
    public double modifyAngle(double oldAngle) {
        keepStraightAngle=oldAngle;
        return oldAngle;
    }
    @Override
    public double getRequestedStartSpeed() {
        return mainSpeed;
    }
    
    @Override
    public void finalizeForPath(PathSection previous, PathSection next) {
        startSpeed=previous.getProvidedEndSpeed();
        endSpeed = next.getRequestedStartSpeed();
        double speedDiffStart=mainSpeed-startSpeed;
        double neededAccelDist=speedDiffStart*913*1.5;
        startSpeedDonePoint=Util.clamp(neededAccelDist/totalDistance, 0, 1);

        double speedDiffEnd=mainSpeed-endSpeed;
        double neededDecelDist=(speedDiffEnd*speedDiffEnd)*913*18;//3
        mainSpeedDonePoint=Util.clamp(1-(neededDecelDist/totalDistance), 0, 1);
        System.out.println("startdone"+startSpeedDonePoint);
        System.out.println("maindone"+mainSpeedDonePoint);
    }

    @Override
    public double getProvidedEndSpeed() {
        return endSpeed;
    }
}
