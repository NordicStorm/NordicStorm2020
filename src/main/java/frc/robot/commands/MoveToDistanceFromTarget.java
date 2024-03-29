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

import java.util.List;

import frc.robot.Robot;
import frc.robot.Util;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.PixyObject;

/**
 *
 */
public class MoveToDistanceFromTarget extends Command {

   double targetDistance;
   boolean backward;

   /**
    * 
    * @param targetDistance
    * @param backward Backward means away from target!
    */
    public MoveToDistanceFromTarget(double targetDistance, boolean backward) {

        requires(Robot.drivetrain);
        this.targetDistance=targetDistance;
        this.backward=backward;
    }

    
    double startDistance=0;
    double totalDistanceToMove=0;
    @Override
    protected void initialize() {
        Robot.drivetrain.setDirectP(1);
        Robot.drivetrain.setOutsideControl(true);
        Robot.drivetrain.setEncMode(true);
        Robot.shooter.setFlywheelOn(true);
        startDistance=Robot.shooter.getVisionDistance();
        totalDistanceToMove=Math.abs(targetDistance-startDistance);
        SmartDashboard.putString("currentCommand", "moveToDistanceFromTarget("+targetDistance+", "+backward+")");
        startTime=System.currentTimeMillis();

    }
    double startTime=0;
    double camWidth=320;
    double offset=5;
    double minSpeed = 0.1;
    double maxSpeed = 0.5;
    boolean shouldStop=false;
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double timeSoFar=System.currentTimeMillis()-startTime;
        double currentDistance=Robot.shooter.getVisionDistance();


        double movedSoFar=Math.abs(currentDistance-startDistance);
        double distanceAway;
        //System.out.println("dist:"+currentDistance);

        if(backward){
            distanceAway=targetDistance-currentDistance;
        }else{
            distanceAway=currentDistance-targetDistance;
        }
        System.out.println("dist:"+distanceAway);
        distanceAway-=Math.abs(Robot.drivetrain.getLeftEncoderVelocity())*0.142;
        System.out.println("predDist:"+distanceAway);

        if(currentDistance==-1){
            Robot.drivetrain.tankDriveDirect(0, 0);
            return;
        }
        
        if(distanceAway<=0){
            Robot.drivetrain.tankDriveDirect(0, 0);
            //if(Math.abs(Robot.drivetrain.getRightEncoderVelocity())<4 && Math.abs(Robot.drivetrain.getLeftEncoderVelocity())<4){
                shouldStop=true;
            //}
            Robot.ballIntake.feedBallToShooter();
            return;
        }
        double percentDone=Util.clamp(1-(distanceAway/totalDistanceToMove), 0, 1);
        double distancePidSpeed;//=(1-percentDone)*0.85;
        distancePidSpeed=distanceAway*distanceAway*0.000213+distanceAway*-0.00155+0.05;
        if(distanceAway>50){
            distancePidSpeed=0.5;
        }
        double accelSpeed;
        accelSpeed=timeSoFar*0.002;
        //distancePidSpeed=0;
        //System.out.println("dist: "+distanceAway);

        System.out.println("distSpeed: "+distancePidSpeed);

        
        double driveSpeed=Math.min(accelSpeed, distancePidSpeed);
        
        //System.out.println(percentDone);
        
        if (Math.abs(driveSpeed) < minSpeed) {
            driveSpeed = Math.copySign(minSpeed, driveSpeed);
        }
        if (Math.abs(driveSpeed) > maxSpeed) {
            driveSpeed = Math.copySign(maxSpeed, driveSpeed);
        }
        
        double leftSpeed = driveSpeed;
        double rightSpeed = driveSpeed;

        double currentAngle=Robot.drivetrain.getAngle();

        double target=(camWidth/2)-offset;
        double currentX=Robot.shooter.getVisionXValue();
        //currentX+=sidewaysCorrection;
        double horizError=currentX-target;
            
        //System.out.println("xOfVision:"+currentX);
        //System.out.println("currAng:"+currentAngle);
        //System.out.println("perc:"+percentDone);
        double horizAdjusted=-6.7468411303383064e+001*horizError*horizError+3.4200421298358008e-001*horizError-4.8669109240412925e-004;
        double horizDistance=Math.tan(Math.toRadians(horizAdjusted))*currentDistance;

        double targetAngle=0;
        double changeTargetVal=horizError;

        if(System.currentTimeMillis()%200<=100){
            //System.out.println(currentDistance+" "+horizError);
        }

        if (Math.abs(changeTargetVal) > 20) {
            changeTargetVal = Math.copySign(20, changeTargetVal);
        }
        if(backward){
            //changeTargetVal*=-1;
        }
        SmartDashboard.putNumber("horizdist", horizDistance);
        SmartDashboard.putNumber("horizerr", horizError);
        SmartDashboard.putNumber("distance", currentDistance);


        //System.out.println("horizDist:"+horizDistance);

        //System.out.println("changeval:"+changeTargetVal);
        //changeTargetVal=0;
        //targetAngle+=changeTargetVal;
        double angleDiff=Drivetrain.angleDiff(currentAngle, targetAngle);
        double useDiff=Util.absClamp(angleDiff*0.1, 1);
        double pVal=0;
        if(backward){
            pVal=0.9;
        }else{
            pVal=0.4;
        }
        double angAdjustment=Util.absClamp(useDiff*pVal*driveSpeed, Math.abs(driveSpeed));//0.01
        percentDone=1;

        double adjustment=(angAdjustment*percentDone);
        
        
        
        //leftSpeed+=0.05;
       // adjustment=0;
        if(backward){

        }else{
            leftSpeed*=-1;
            rightSpeed*=-1;
        }
        
        leftSpeed+=adjustment;
        rightSpeed-=adjustment;

        //leftSpeed=0;
        //rightSpeed=0;
        //System.out.println("dist:"+currentDistance);

        //System.out.println("drivespeed:"+driveSpeed);

        //System.out.println("angErr:"+angleDiff);
        //System.out.println("horizErr:"+horizError);


        //System.out.println("left:"+leftSpeed);
        //System.out.println("right:"+rightSpeed);
        
        Robot.drivetrain.tankDriveDirect(-leftSpeed, rightSpeed);

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {

        return shouldStop;
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
