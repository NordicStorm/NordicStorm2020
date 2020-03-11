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
import frc.robot.subsystems.PixyObject;

/**
 *
 */
public class FollowBall extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public FollowBall() {

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    DriveToObject targetTracker;

    @Override
    protected void initialize() {

        targetTracker = new DriveToObject(pVal, forwardMod, maxTurn, stopWidth, proxPVal, camWidth, camHeight);
        targetTracker.setOffset(0);
        
    }

    int camWidth = 315;
    int camHeight = 207;

    double turnValue = 0;
    double forwardValue = 0;
    double maxTurn = 0.75;
    double pVal = 1.25;
    double proxPVal = 0.07 * 0;
    double stopWidth = 40 * 0;
    double forwardMod = 0.75;
    boolean fullAuto = true;
    boolean shouldStop = false;
    double minAspect=0.35;
    double maxAspect=1.2;
    int currentFollowingID = -1;



    private PixyObject findTarget(List<PixyObject> possibleTargets){
        if(currentFollowingID!=-1){
            for (PixyObject possible : possibleTargets) { 
                if (possible.trackingIndex == currentFollowingID) {
                    return possible;
                }
            }
            currentFollowingID=-1; // the target was not found in the list, so reset what we're following.
            return findTarget(possibleTargets); //find a new target
        }else{// not locked on
            for(PixyObject possible : possibleTargets){
                double aspect=possible.width/possible.height;
                //System.out.println(aspect);
                //if(!(aspect>=minAspect && aspect<=maxAspect)){continue;}//skip if it is too far from square
                currentFollowingID=possible.trackingIndex;
                return possible;
            }
        }
        
        return null;
    }
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        List<PixyObject> objects = Robot.pixy.readObjects();
        PixyObject object = findTarget(objects);
        
        if (object != null) {
            double[] speeds = targetTracker.execute(object.x, object.width);
            turnValue = speeds[0];
            forwardValue = speeds[1];
        } else {
            if (turnValue < 0) {
                turnValue = -maxTurn * 0.75;
            } else if (turnValue > 0) {
                turnValue = maxTurn * 0.75;
            }
            forwardValue = 0;
            
        }
        if(Math.abs(turnValue)>0.1 && Math.abs(turnValue)<0.4){
            if(turnValue<0){
                turnValue=-0.4;
            }else{
                turnValue=0.4;
            }
        }
        Robot.drivetrain.drive(forwardValue, turnValue);
        Robot.ballIntake.setIntakeRunning(true);

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {

        return shouldStop;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
