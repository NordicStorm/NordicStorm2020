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

    Servo shiftingServo;
    AHRS navx;
    WPI_TalonSRX leftMasterTalon;
    WPI_TalonSRX rightMasterTalon;
    WPI_TalonSRX leftSlaveTalon;
    WPI_TalonSRX rightSlaveTalon;

    DifferentialDrive robotDrive;

    double fastShiftPos = 0.661086;
    double slowShiftPos = 0.106335;
    boolean shiftPosIsFast = false;

    public Drivetrain() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        shiftingPiston = new Solenoid(0, 0);
        addChild("ShiftingPiston", shiftingPiston);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        leftMasterTalon = new WPI_TalonSRX(1);
        rightMasterTalon = new WPI_TalonSRX(2);
        leftSlaveTalon = new WPI_TalonSRX(3);
        rightSlaveTalon = new WPI_TalonSRX(4);

        rightMasterTalon.setNeutralMode(NeutralMode.Brake);
        leftSlaveTalon.setNeutralMode(NeutralMode.Brake);
        rightSlaveTalon.setNeutralMode(NeutralMode.Brake);

        leftSlaveTalon.follow(leftMasterTalon);
        rightSlaveTalon.follow(rightMasterTalon);

        robotDrive = new DifferentialDrive(leftMasterTalon, rightMasterTalon);
        robotDrive.setSafetyEnabled(false);
        robotDrive.setSafetyEnabled(true);
        robotDrive.setExpiration(0.1);
        robotDrive.setDeadband(0.02);
        robotDrive.setMaxOutput(1.0);
        SmartDashboard.putNumber("shiftVal", 0.5);
        navx = new AHRS(Port.kMXP); /* Alternatives: SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */
        shiftingServo = new Servo(2);
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

        shiftingPiston.set(!shiftPosIsFast);// true means slow
        // double target = Util.map(Robot.oi.leftJoystick.getZ(), 1, -1, 0, 1);
        double target = SmartDashboard.getNumber("shiftVal", 0);
        /*if(shiftPosIsFast){
            target=fastShiftPos;
        }else{
            target=slowShiftPos;
        }*/
        shiftingServo.set(target);
        
        // System.out.println("target:" + target);
        
        // Put code here to be run every loop

    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void drive(double forwardSpeed, double turnRightSpeed) {
        robotDrive.arcadeDrive(-forwardSpeed, turnRightSpeed);
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

    public void resetHeading() {
        navx.zeroYaw();
    }
}
