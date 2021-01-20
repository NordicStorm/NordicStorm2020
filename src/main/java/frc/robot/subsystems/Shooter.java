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

import frc.robot.Robot;
import frc.robot.Util;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.controller.PIDController;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SerialPort.Port;

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

    WPI_TalonSRX pivot;
    int rpmTolerance = 7;
    double hoodAngle = 20;
    int servoMaxTimeDelay = 1000;// note: this is scaled based on how far the servo moves
    int timeLeftToMoveServo = 0;

    double targetRPM = 0;
    long lastTime;
    AHRS navx;

    NetworkTable visionNetTable;

    double currentRequestedPivotPower=0;
    PIDController pivotPID;
    boolean enableAutoUnwind=true;
    boolean unwindingPivot=false;
    double unwindedTargetAngle=0;

    public Shooter() {

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        hoodServo = new Servo(0);
        addChild("HoodServo", hoodServo);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        flywheel = new CANSparkMax(13, MotorType.kBrushless);
        flywheelVelocityControl = flywheel.getPIDController();
        flywheelVelocityControl.setP(0.0007);
        flywheelVelocityControl.setI(0.0000009);
        flywheelVelocityControl.setD(0.00);
        flywheelVelocityControl.setIZone(200);
        flywheelVelocityControl.setFF(0.00021);

        pivot = new WPI_TalonSRX(10);
        pivot.configVoltageCompSaturation(11.5);
        pivot.enableVoltageCompensation(true);
        // flywheel.burnFlash();
        lastTime = System.currentTimeMillis();
        navx = new AHRS(Port.kUSB); /* Alternatives: SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */

        setAngle(45);

        pivotPID=new PIDController(0, 0, 0);

    }

    public void setVisionNetTable(NetworkTable visTable) {
        visionNetTable = visTable;
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

        SmartDashboard.putNumber("angleShooter", navx.getAngle());
        long currTime = System.currentTimeMillis();
        if (timeLeftToMoveServo > 0) {
            long elapsed = currTime - lastTime;
            timeLeftToMoveServo -= elapsed;
            if (timeLeftToMoveServo < 0) {
                timeLeftToMoveServo = 0;
            }
        }

        double visRPM = getVisionRPMValue();
        // System.out.println(visRPM);
        if (visRPM != -1) {
            targetRPM = visRPM;
        }
        double visAngle = getVisionAngleValue();
        if (visAngle != -1) {
            setAngle(visAngle);

        }

        SmartDashboard.putNumber("Should be", targetRPM);
        SmartDashboard.putNumber("RPM", flywheel.getEncoder().getVelocity());
        hoodServo.set(Util.map(hoodAngle, 25, 45, 0.34645669162273407, 0));
        // hoodServo.set(Util.map(hoodAngle, 25, 45, 0.44645669162273407, 0));
        SmartDashboard.putNumber("Temp", flywheel.getMotorTemperature());
        // System.out.println(hoodAngle);
        // targetRPM=2500;

        flywheelVelocityControl.setReference(-targetRPM, ControlType.kVelocity);
        // setPower(1);
        flywheel.enableVoltageCompensation(11.5);

        
        setPivotPower(Robot.oi.getLeftJoystick().getX());
        if(enableAutoUnwind && !Robot.ballIntake.areBallsPresent() && Math.abs(getTotalShooterRotation())>360){
            unwindingPivot=true;
            double currentAng=getTotalShooterRotation();
            if(currentAng>0){

            }
        }

        if(unwindingPivot){
            //setPivotPower(pivotPID.calculate(getTotalShooterRotation(), unwindedTargetAngle));
        }
        pivot.set(currentRequestedPivotPower);

        
        // setVelocity(3400);
        // Put code here to be run every loop

    }

    public void setPower(double power) {
        flywheel.set(power);

    }

    public void setPivotPower(double power) {
        currentRequestedPivotPower=power;
    }

    public double getTotalShooterRotation(){
        return Robot.drivetrain.getAngle()-this.getHeading();
    }
    public void setAutoUnwindEnabled(boolean enable) {
        enableAutoUnwind=enable;
    }

    public void setVelocity(double rpm) {
        // flywheelVelocityControl.setReference(rpm, ControlType.kVelocity);
        targetRPM = rpm;
    }

    public void setAngle(double angle) {
        // System.out.println("ang"+angle);
        angle = Math.min(Math.max(angle, 25), 45); // constrain from 25-45
        hoodAngle = angle;

        if (Math.abs(hoodAngle - angle) < 5) {
            return;
        }
        double difference = Math.abs(angle - hoodAngle);
        timeLeftToMoveServo += servoMaxTimeDelay * (difference / 20.0);
    }

    public void rawSet(double angle) {
        // System.out.println(angle);
        hoodServo.set(angle);
    }

    public boolean isReadyToShoot() {
        // return getVelocity()>2000;
        // System.out.println(timeLeftToMoveServo);
        if (getVelocity() < 400) {
            return false;
        }
        return getFlywheelVelocityAbsoluteError() <= rpmTolerance && timeLeftToMoveServo <= 30;
    }

    public double getVelocity() {
        return -flywheel.getEncoder().getVelocity();
    }

    public double getFlywheelVelocityError() {
        return getVelocity() - targetRPM;
    }

    public double getFlywheelVelocityAbsoluteError() {
        return Math.abs(getFlywheelVelocityError());
    }

    public double getVisionRPMValue() {

        return visionNetTable.getEntry("rpm").getDouble(-1);
    }

    public double getVisionAngleValue() {

        return visionNetTable.getEntry("angle").getDouble(-1);
    }

    public double getVisionXValue() {

        return visionNetTable.getEntry("x").getDouble(-1);
    }
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    public void resetHeading() {
        navx.zeroYaw();
    }

    public double getHeading(){
        return navx.getAngle();
    }

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}
