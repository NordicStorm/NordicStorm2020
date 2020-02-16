// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.subsystems;

import frc.robot.Util;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.Servo;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class Shooter extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
private Servo hoodServo;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    CANPIDController flywheelVelocityControl;
    CANSparkMax flywheel;

    int rpmTolerance = 20;
    double hoodAngle = 20;
    int servoMaxTimeDelay = 1000;// note: this is scaled based on how far the servo moves
    int timeLeftToMoveServo = 0;

    double targetRPM = 0;
    long lastTime;

    NetworkTable visionNetTable;

    public Shooter() {
        visionNetTable=NetworkTableInstance.getDefault().getTable("vision");

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
hoodServo = new Servo(0);
addChild("HoodServo",hoodServo);

        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        flywheel = new CANSparkMax(13, MotorType.kBrushless);
        flywheelVelocityControl = flywheel.getPIDController();

        flywheelVelocityControl.setP(1);
        flywheelVelocityControl.setI(0.0001);
        flywheelVelocityControl.setD(0);
        flywheelVelocityControl.setIZone(1000);
        flywheelVelocityControl.setFF(0);

        lastTime = System.currentTimeMillis();

        setAngle(25);
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    // 4300
    @Override
    public void periodic() {
        long currTime = System.currentTimeMillis();
        if (timeLeftToMoveServo > 0) {
            long elapsed = currTime - lastTime;
            timeLeftToMoveServo -= elapsed;
            if (timeLeftToMoveServo < 0) {
                timeLeftToMoveServo = 0;
            }
        }

        double visRPM=getVisionRPMValue();
        if(visRPM!=-1){
            targetRPM=visRPM;
        }
        double visAngle=getVisionAngleValue();
        if(visAngle!=-1){
            setAngle(visAngle);
        }
        // System.out.println(flywheel.getEncoder().getVelocity());
        // flywheelVelocityControl.setReference(targetRPM, ControlType.kVelocity);

        // setVelocity(3000);
        // Put code here to be run every loop

    }

    public void setPower(double power) {
        flywheel.set(power);

    }

    public void setVelocity(double rpm) {
        // flywheelVelocityControl.setReference(rpm, ControlType.kVelocity);
        targetRPM = rpm;
    }

    public void setAngle(double angle) {
        angle = Math.min(Math.max(angle, 25), 45); // constrain from 25-45
        if (hoodAngle == angle) {
            return;
        }
        double difference = Math.abs(angle - hoodAngle);
        timeLeftToMoveServo += servoMaxTimeDelay * (difference / 20.0);
        hoodAngle = angle;
    }

    public void rawSet(double angle) {
        //System.out.println(angle);
        hoodServo.set(angle);
    }

    public boolean isReadyToShoot() {
        return getFlywheelVelocityAbsoluteError() <= rpmTolerance && timeLeftToMoveServo == 0;
    }

    public double getFlywheelVelocityError() {
        return flywheel.getEncoder().getVelocity() - targetRPM;
    }

    public double getFlywheelVelocityAbsoluteError() {
        return Math.abs(getFlywheelVelocityError());
    }
    public double getVisionRPMValue(){
		
		return visionNetTable.getEntry("rpm").getDouble(-1);
    }
    public double getVisionAngleValue(){
		
		return visionNetTable.getEntry("angle").getDouble(-1);
    }
    public double getVisionXValue(){
		
		return visionNetTable.getEntry("x").getDouble(-1);
	}
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}
