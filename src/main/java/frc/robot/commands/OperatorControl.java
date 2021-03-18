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
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.Util;

/**
 *
 */
public class OperatorControl extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public OperatorControl() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drivetrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }
    double tightTurnTurnVal=0.5184;
    double tightTurnForwardval=0.5625;
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    boolean shiftWasPressed=false;
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        
        Joystick rightJoystick = Robot.oi.getRightJoystick();
        Joystick leftJoystick = Robot.oi.getLeftJoystick();
        double forwardPower= -rightJoystick.getY();
        double turnPower= -rightJoystick.getZ();
        //turnPower=Math.copySign(turnPower*turnPower, turnPower); //square the input

        //System.out.println(Robot.drivetrain.getRotationalSpeed());
        //System.out.println(turnPower);
        //System.out.println(forwardPower);
        if(forwardPower<0.008 && turnPower>0 && turnPower<=0.10){//weird thing with joystick
            turnPower=0;
        }

        if(Math.abs(forwardPower)<0.05){
            forwardPower=0;
        }
        if(Math.abs(turnPower)<0.05){
            turnPower=0;
        }

        double throttle=rightJoystick.getThrottle();
        throttle=Util.map(throttle, 1, -1, 0.1, 1);
        SmartDashboard.putNumber("NumBalls",Robot.pixy.getNumObs());

        forwardPower*=throttle;
        turnPower*=throttle;
        if(rightJoystick.getX()<=-0.75){
            forwardPower=tightTurnForwardval;
            turnPower=tightTurnTurnVal;
        }
        if(rightJoystick.getX()>=0.75){
            forwardPower=tightTurnForwardval;
            turnPower=-tightTurnTurnVal;
        }
        Robot.drivetrain.drive(forwardPower, turnPower);

        if(rightJoystick.getRawButton(4)){
            if(!shiftWasPressed){
                Robot.drivetrain.shiftToggle();
            }
            shiftWasPressed=true;
        }else{
            shiftWasPressed=false;
        }
        if(rightJoystick.getRawButton(6)){
            Robot.drivetrain.resetEncoderPositions();
        }
        double leftY=leftJoystick.getY();
        
        //Robot.pixy.setLamps(rightJoystick.getRawButton(5));
        //Robot.pixy.setLamps(System.currentTimeMillis()%1000>500);

        //Robot.shooter.setPower(leftJoystick.getX());
        //Robot.shooter.rawSet(Util.map(leftJoystick.getZ(), 1, -1, 0, 0.34645669162273407));
        double kick=0;
        if(leftJoystick.getRawButton(11)){
            kick=1;
        }
        if(Math.abs(leftY)>0.7){
            Robot.ballIntake.setRawMotors(leftY, leftY, leftY, leftY, leftY,leftY*0.1);
        }
        if(Math.abs(leftJoystick.getX())>0.7){
            //Robot.shooter.setPower(leftJoystick.getX());
        }


        if(leftJoystick.getTrigger()){
            if(Robot.shooter.isReadyToShoot()){
                Robot.ballIntake.feedBallToShooter();
            }
        }
        Robot.ballIntake.setIntakeRunning(leftJoystick.getRawButton(4));

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.drivetrain.drive(0, 0);
        Robot.ballIntake.setIntakeRunning(false);
    }
}
