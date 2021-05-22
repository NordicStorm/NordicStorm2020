
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
public class RunVisionCenter extends PathSection {

    double camWidth=320;
    boolean canEnd;
    int goodFrames = 0;
    double targetOffset;
    double targetX;
    /**
     * 
     * @param offset how much it will target to the right
     * @param canEnd
     */
    public RunVisionCenter(double offset, boolean canEnd) {
        this.targetOffset=offset;
        this.canEnd=canEnd;
        targetX=(camWidth/2);
    }


    @Override
    protected void initialize() {
        SmartDashboard.putString("currentCommand", "RunVisionCenter()");
        SmartDashboard.putNumber("visOffset", 0);
        
    }
    double lastFrameID=0;
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double thisID=Robot.shooter.getVisionFrameID();
        if(!canEnd){ // manual
            //targetOffset=SmartDashboard.getNumber("visOffset", 0);
        }
        if(thisID!=lastFrameID){
            lastFrameID=thisID;
            double error = (Robot.shooter.getVisionXValue()+targetOffset)-targetX;
            double newVal=Robot.shooter.getHeading()+error*0.1;
            Robot.shooter.setAutoSeekHeading(true, newVal);
            System.out.println("err:"+error);
            System.out.println("newval:"+newVal);
            if(error<=1.5){
                goodFrames+=1;
            }else{
                goodFrames=0;
            }
        }
    }

    
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return goodFrames>=3 && canEnd;
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

    @Override
    public double getRequestedStartSpeed() {
        return 0;
    }

    @Override
    public double getProvidedEndSpeed() {
        return 0;
    }

    @Override
    public double modifyAngle(double oldAngle) {
        return oldAngle;
    }

    @Override
    public void finalizeForPath(PathSection previous, PathSection next) {
        //nothing to do
    }

    
}
