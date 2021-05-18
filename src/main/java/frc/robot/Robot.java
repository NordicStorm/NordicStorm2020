// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot;

import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

    AutoWithInit autonomousCommand;

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
public static Drivetrain drivetrain;
public static Vision vision;
public static Pixy pixy;
public static Pneumatics pneumatics;
public static Shooter shooter;
public static BallIntake ballIntake;
//public static ControlSpinner controlSpinner;
//public static Climber climber;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    static NetworkTable visionNetTable;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
drivetrain = new Drivetrain();
vision = new Vision();
pixy = new Pixy();
pneumatics = new Pneumatics();
shooter = new Shooter();
ballIntake = new BallIntake();
//controlSpinner = new ControlSpinner();
//climber = new Climber();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        // (which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_RobotBuilder);
        pixy.setBrightness(100);
        pixy.startUpdatingPixy();
        pneumatics.startCompressor();
        drivetrain.shift(true);
        visionNetTable=NetworkTableInstance.getDefault().getTable("vision");
        shooter.setVisionNetTable(visionNetTable);
    }


    /**
     * This function is called when the disabled button is hit. You can use it to
     * reset subsystems before shutting down.
     */
    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        Robot.ballIntake.setLongRunningIntake(false);

        Robot.drivetrain.setSuperPMode(false);

        Robot.drivetrain.resetHeading();
        Robot.shooter.resetHeading();
        Robot.shooter.setFlywheelOn(true);
        autonomousCommand=new AutonomousBouncePath();
        autonomousCommand.initializeCommands();
        autonomousCommand.start();
        
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null){
            autonomousCommand.cancel();
        }
        Robot.shooter.setFlywheelOn(false);
        Robot.ballIntake.setLongRunningIntake(false);
        Robot.drivetrain.setOutsideControl(false);
        Robot.drivetrain.setEncMode(true);
        Robot.drivetrain.setSuperPMode(false);
        //Robot.drivetrain.resetHeading();
        //Robot.shooter.resetHeading();
            
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    public static NetworkTable getVisionNetTable() {
        return visionNetTable;
    }
}
