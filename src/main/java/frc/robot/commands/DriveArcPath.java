
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
public class DriveArcPath extends PathSection {

   
    
    double robotWidth=1.833; //22 inches = 1.833 feet
    
    double pVal = 0.023;
    double maxTurn = 0.2;
    double minTurn = 0.15;
    double tolerance=2;

    double targetAngle;
    double turnRadius;
    boolean arcRight;
    double mainSpeed;
    double startSpeed;
    double endSpeed;
    double turnDistance;
    double prevAngle;

    double leftSpeedProportion;
    double rightSpeedProportion;

    double targetLeftSpeed=0;
    double targetRightSpeed=0;
    boolean done=false;
    boolean veloPredictStop;
    int lastTickLocation=0; //-1, 0, 1
    int timesLeftToPass;

    
    
    public DriveArcPath(double targetAngle, double innerSpeed, double outerSpeed, boolean arcRight) {
        this.targetAngle=targetAngle;
        this.arcRight=arcRight;
        //this.veloPredictStop=veloPredictStop;
        requires(Robot.drivetrain);

        /*if(is360){
            timesLeftToPass=2;//todo think more
        }else{
            timesLeftToPass=1;
        }*/
        if(arcRight){
            targetLeftSpeed= -outerSpeed;
            targetRightSpeed= innerSpeed;
        }else{
            targetLeftSpeed= -innerSpeed;
            targetRightSpeed= outerSpeed;
        }


        
        /*
        double radLeft;
        double radRight;
        if(arcRight){
            radLeft=turnRadius+robotWidth;
            radRight=turnRadius;

            double circLeft=2*Math.PI*radLeft;
            double circRight=2*Math.PI*radRight;
            leftSpeedProportion=1;
            rightSpeedProportion=circRight/circLeft;
            if(turnRadius==2.5){
                targetLeftSpeed= -gridTurnOuter;
                targetRightSpeed= gridTurnInner;
            }
        }else{
            radLeft=turnRadius;
            radRight=turnRadius+robotWidth;

            double circLeft=2*Math.PI*radLeft;
            double circRight=2*Math.PI*radRight;
            leftSpeedProportion=circLeft/circRight;
            rightSpeedProportion=1;
            if(turnRadius==2.5){
                targetLeftSpeed= -gridTurnInner;
                targetRightSpeed= gridTurnOuter;
            }
        }
        */
        

    }

    @Override
    protected void initialize() {
        Robot.drivetrain.setEncMode(true);
        Robot.drivetrain.setDirectP(1);
        //Robot.drivetrain.setSuperPMode(true);
        //leftTargetPos=Robot.drivetrain.getLeftEncoderDistance()-distance;
        //rightTargetPos=Robot.drivetrain.getRightEncoderDistance()+distance;
        Robot.drivetrain.setOutsideControl(true);
        double currentAngle = Robot.drivetrain.getAngle();
        double diff=Drivetrain.fullAngleDiff(currentAngle, prevAngle, arcRight);
        SmartDashboard.putString("currentCommand", "addArc("+targetAngle+", "+arcRight+")");

        if(diff>180){//we overshot on the last command
            //timesLeftToPass+=1;
        }
        System.out.println("supposedPrev:"+prevAngle);

        System.out.println("curr:"+currentAngle);
        System.out.println("target:"+targetAngle);


        System.out.println("diff:"+diff);

        System.out.println("turnDist:"+turnDistance);

        
        
        
        done=false;
    }
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double currentAngle = Robot.drivetrain.getAngle();
        
        double angDiff=Drivetrain.fullAngleDiff(currentAngle, targetAngle, arcRight);
        if(veloPredictStop){
            double rotSpeed=Robot.drivetrain.getRotationalVelocity();
            angDiff=angDiff-rotSpeed*0.5;
            System.out.println("rot:"+rotSpeed);
        }
        if(Math.abs(angDiff)>270){
            if(lastTickLocation==1){//cross going forward. last small (near target), now big (far away)
                timesLeftToPass-=1;
                //System.out.println("godown");
            }
            lastTickLocation=-1;
        }else if(Math.abs(angDiff)<90){
            if(lastTickLocation==-1){
                timesLeftToPass+=1;
                //System.out.println("goup");
            }
            lastTickLocation=1;

        }else{
            lastTickLocation=0;
        }
        if(timesLeftToPass==0){
            angDiff=0;
            done=true;
        }
        //System.out.println("timesleft:"+timesLeftToPass);
        //System.out.println("diff:"+angDiff);

        //System.out.println("curr:"+currentAngle);
        //System.out.println("target:"+targetAngle);


        //System.out.println("leftProp"+ leftSpeedProportion);
        //System.out.println("rightProp"+ rightSpeedProportion);

        
        double currentLeft=Robot.drivetrain.getLeftEncoderVelocityPercent()/leftSpeedProportion;
        double currentRight=Robot.drivetrain.getRightEncoderVelocityPercent()/rightSpeedProportion;
        double driveSpeed=0;
        
        driveSpeed=Math.max(1, Math.min(1, angDiff*0.01));
        double leftSpeed=targetLeftSpeed*driveSpeed; //-driveSpeed*leftSpeedProportion;
        double rightSpeed = targetRightSpeed*driveSpeed;//driveSpeed*rightSpeedProportion;
        Robot.drivetrain.tankDriveDirect(leftSpeed, rightSpeed);
        //System.out.println("leftspeed"+leftSpeed);

    }

    
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return done;
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
    public double modifyAngle(double oldAngle) {
        turnDistance=Drivetrain.fullAngleDiff(oldAngle, targetAngle, arcRight);
        prevAngle=oldAngle;
        return targetAngle;
        
    }

    @Override
    public double getRequestedStartSpeed() {
        return Math.max(Math.abs(targetLeftSpeed),Math.abs(targetRightSpeed));
        //return Math.max(leftSpeedProportion, rightSpeedProportion)*mainSpeed;
    }
    
    @Override
    public void finalizeForPath(PathSection previous, PathSection next) {
        double lastSpeed=previous.getProvidedEndSpeed();
        startSpeed=lastSpeed;
        //if(previous.getNeededStartSpeed)
    }
    @Override
    public double getProvidedEndSpeed() {
        return 0;
    }
    
}
