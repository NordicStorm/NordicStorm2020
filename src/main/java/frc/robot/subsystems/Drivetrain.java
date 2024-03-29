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
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.Servo;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.Solenoid;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class Drivetrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private Solenoid shiftingPiston;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    AHRS navx;
    EncoderTalon leftMasterTalon;
    EncoderTalon rightMasterTalon;
    EncoderTalon leftSlaveTalon;
    EncoderTalon rightSlaveTalon;

    DifferentialDrive robotDrive;

    double currentForwardSpeed = 0;
    double currentTurnSpeed = 0;
    /*
     * Measured 1/21/21: 0.65 high gear 1 low gear
     */
    double fastShiftPos = 0.65;
    double slowShiftPos = 1;
    boolean shiftPosIsFast = false;
    boolean encMode = true;
    boolean isControlledOutside = false;

    public Drivetrain() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        shiftingPiston = new Solenoid(0, 0);
        addChild("ShiftingPiston", shiftingPiston);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        leftMasterTalon = new EncoderTalon(1);
        rightMasterTalon = new EncoderTalon(2);
        leftSlaveTalon = new EncoderTalon(3);
        rightSlaveTalon = new EncoderTalon(4);

        rightMasterTalon.setNeutralMode(NeutralMode.Brake);
        leftMasterTalon.setNeutralMode(NeutralMode.Brake);
        leftSlaveTalon.setNeutralMode(NeutralMode.Brake);
        rightSlaveTalon.setNeutralMode(NeutralMode.Brake);

        leftSlaveTalon.follow(leftMasterTalon);
        rightSlaveTalon.follow(rightMasterTalon);

        robotDrive = new DifferentialDrive(leftMasterTalon, rightMasterTalon);
        robotDrive.setSafetyEnabled(true);
        robotDrive.setExpiration(0.1);
        robotDrive.setDeadband(0.0);
        robotDrive.setMaxOutput(1.0);
        navx = new AHRS(Port.kMXP); /* Alternatives: SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */
        setEncMode(true);
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new OperatorControl());

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("angleDrive", navx.getAngle());
        SmartDashboard.putNumber("leftmasterspeed", -leftMasterTalon.getSelectedSensorVelocity());
        SmartDashboard.putNumber("rightmasterspeed", rightMasterTalon.getSelectedSensorVelocity());
        SmartDashboard.putNumber("leftMasterPower", leftMasterTalon.getMotorOutputPercent());
        SmartDashboard.putNumber("rightMasterPower", rightMasterTalon.getMotorOutputPercent());
        SmartDashboard.putNumber("LeftEncPos", leftMasterTalon.getSelectedSensorPosition());
        if (Robot.oi.getRightJoystick().getRawButton(6)) {
            leftMasterTalon.setSelectedSensorPosition(0);
        }

        if (!isControlledOutside) {
            robotDrive.arcadeDrive(-currentForwardSpeed, currentTurnSpeed, false);
        }
        shiftingPiston.set(!shiftPosIsFast);// true means slow
        // double target = Util.map(Robot.oi.leftJoystick.getZ(), 1, -1, 0, 1);
        /*
         * double target = SmartDashboard.getNumber("shiftVal", 0); if(shiftPosIsFast){
         * target=fastShiftPos; }else{ target=slowShiftPos; } shiftingServo.set(target);
         * SmartDashboard.putNumber("shiftReadout", target);
         * 
         * // System.out.println("target:" + target);
         */

    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void drive(double forwardSpeed, double turnLeftSpeed) {
        currentForwardSpeed = forwardSpeed;
        currentTurnSpeed = turnLeftSpeed;
    }

    public void setEncMode(boolean newEncMode) {
        if (encMode != newEncMode) {
            leftMasterTalon.setEncMode(newEncMode);
            rightMasterTalon.setEncMode(newEncMode);
        }
        encMode = newEncMode;
    }
    /**
     * 
     * @param superP set if super-P mode should be enabled, mainly for autonomous
     */
    public void setSuperPMode(boolean superP){
        leftMasterTalon.setSuperPMode(superP);
        rightMasterTalon.setSuperPMode(superP);
    }
    public void setDirectP(double p){
        leftMasterTalon.setP(p);
        rightMasterTalon.setP(p);
    }
    public void directSet(double forwardSpeed, double turnLeftSpeed) {
        drive(forwardSpeed, turnLeftSpeed);
        robotDrive.arcadeDrive(-forwardSpeed, turnLeftSpeed, false);
    }

    public void setOutsideControl(boolean outsideControl) {
        isControlledOutside = outsideControl;
    }

    public void tankDriveDirect(double left, double right) {
        robotDrive.feedWatchdog();
        leftMasterTalon.set(left);
        rightMasterTalon.set(right);
    }

    private void setMotorsFromArcadeDrive(double forwardSpeed, double turnLeftSpeed) {
        double leftMotorOutput = 0;
        double rightMotorOutput = 0;
        if (forwardSpeed > 0.0) {
            if (turnLeftSpeed > 0.0) {
                leftMotorOutput = forwardSpeed - turnLeftSpeed;
                rightMotorOutput = Math.max(forwardSpeed, turnLeftSpeed);
            } else {
                leftMotorOutput = Math.max(forwardSpeed, -turnLeftSpeed);
                rightMotorOutput = forwardSpeed + turnLeftSpeed;
            }
        } else {
            if (turnLeftSpeed > 0.0) {
                leftMotorOutput = -Math.max(-forwardSpeed, turnLeftSpeed);
                rightMotorOutput = forwardSpeed + turnLeftSpeed;
            } else {
                leftMotorOutput = forwardSpeed - turnLeftSpeed;
                rightMotorOutput = -Math.max(-forwardSpeed, -turnLeftSpeed);
            }
        }
        leftMasterTalon.set(leftMotorOutput);
        rightMasterTalon.set(rightMotorOutput);

    }

    public void shift(boolean isFast) {
        shiftPosIsFast = isFast;
    }

    public void shiftToggle() {
        shift(!shiftPosIsFast);
    }

    public double getAngle() {
        return navx.getAngle();
    }

    public double getRotationalVelocity() {
        return navx.getRate() * navx.getActualUpdateRate();
    }

    public double getLeftEncoderVelocity(){
        return leftMasterTalon.getSelectedSensorVelocity();
    }
    public double getRightEncoderVelocity(){
        return rightMasterTalon.getSelectedSensorVelocity();
    }
    public double getLeftEncoderVelocityPercent(){
        return getLeftEncoderVelocity()/1550;
    }
    public double getRightEncoderVelocityPercent(){
        return getRightEncoderVelocity()/1550;
    }


    public double getLeftEncoderDistance() {
        return leftMasterTalon.getSelectedSensorPosition();
    }

    public double getRightEncoderDistance() {
        return rightMasterTalon.getSelectedSensorPosition();
    }

    public void resetEncoderPositions() {
        leftMasterTalon.setSelectedSensorPosition(0);
        rightMasterTalon.setSelectedSensorPosition(0);
    }

    /**
     * Get degrees difference, wrapped around so you just follow the sign of the return value to turn.
     * @param current
     * @param target
     * @return
     */
    public static double angleDiff(double current, double target) {
        current = current % 360 - 180;
        target = target % 360 - 180;
        double diff = (target - current);
        if (diff > 180) {
            diff = -360 + diff;
        }
        if (diff < -180) {
            diff = 360 + diff;
        }
        return diff;
    }

    /**
     * Angle difference not wrapped. Will give number -360 to 360, depending if {@code goingRight}
     * @param current
     * @param target
     * @param goingRight
     * @return
     */
    public static double fullAngleDiff(double current, double target, boolean goingRight){
      
        double diff = angleDiff(current, target);
        if(goingRight){
          if(diff<0){
            diff+=360;
          }
        }else{
          if(diff>0){
            diff-=360;
          }
        }
        return diff;
      }
    public void resetHeading() {
        navx.zeroYaw();
    }
    public void setAngleAdjustment(double adjustment){
        navx.setAngleAdjustment(adjustment);
    }
}
