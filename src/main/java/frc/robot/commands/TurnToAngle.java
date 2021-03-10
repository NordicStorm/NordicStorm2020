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
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

/**
 *
 */
public class TurnToAngle extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    double pVal = 0.023;
    double maxTurn = 0.2;
    double minTurn = 0.15;//0.15
    double tolerance=2;
    double targetAngle;
    boolean done=false;

    /**
     * 
     * @param targetAngle Degrees. 0=forward, 90=straight left (toward janitor
     *                    stuff).
     */
    public TurnToAngle(double targetAngle) {
        this.targetAngle = targetAngle;
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drivetrain);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        SmartDashboard.putString("currentCommand", "turnToAngle("+targetAngle+")");
        //turningThread.start();
        Robot.drivetrain.setEncMode(true);
    }


    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double currAngle = Robot.drivetrain.getAngle();
        double diff = Drivetrain.angleDiff(currAngle, targetAngle);
        double turnVal = diff * pVal;
        double error = diff;
        double rotationRate=Robot.drivetrain.getRotationalVelocity();
        double absError = Math.abs(error);
        //boolean moving=Robot.drivetrain.getRotationalSpeed();
        if(absError>10 || Math.abs(rotationRate)<absError*0.75){//0.75
            if (Math.abs(turnVal) > maxTurn) {
                turnVal = Math.copySign(maxTurn, turnVal);
            }
            if (Math.abs(turnVal) < minTurn ) {
                turnVal = Math.copySign(minTurn, turnVal);
            }
        }
        if(absError<tolerance){
            turnVal=0;
        }
        
        Robot.drivetrain.directSet(0, -turnVal);
        /*
        double now = System.currentTimeMillis();
        double timeDiff = now - lastTime;
        lastTime = now;
        if (timeDiff < 99999) {// aka not the first one
            //currentAccummulator += timeDiff * error;
            currentAccummulator+=Math.copySign(timeDiff, error);
        }
        if (Math.abs(rotationRate)>10) {
            //if (absError > 10 || absError < 0.5) {
                currentAccummulator = 0;
            //}
        }
        if (Math.abs(currentAccummulator) > maxAccum ) {
            currentAccummulator = Math.copySign(maxAccum, currentAccummulator);
        }*/
        if(absError<tolerance && Math.abs(rotationRate)<0.1){
            done=true;
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
        Robot.drivetrain.drive(0, 0);
        Robot.drivetrain.setEncMode(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
