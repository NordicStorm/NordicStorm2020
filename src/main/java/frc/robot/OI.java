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

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
public JoystickButton trigger;
public JoystickButton startShootModeButton;

public Joystick rightJoystick;
public JoystickButton shootTrigger;
public Joystick leftJoystick;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

leftJoystick = new Joystick(1);

shootTrigger = new JoystickButton(leftJoystick, 10);
shootTrigger.whileHeld(new CenterAndShoot());
rightJoystick = new Joystick(0);

trigger = new JoystickButton(rightJoystick, 1);
trigger.whileHeld(new FollowBall(true, false));
//startShootModeButton = new JoystickButton(rightJoystick, 6);
//startShootModeButton.toggleWhenPressed(new MoveToDistanceFromTarget(150, false));
//new JoystickButton(rightJoystick, 8).whenPressed(new StartAutoShooter(0));
new JoystickButton(rightJoystick, 8).whenPressed(new StartAutoShooter(3));
new JoystickButton(rightJoystick, 5).whenPressed(new ResetHeading());
new JoystickButton(rightJoystick, 10).toggleWhenPressed(new RunVisionCenter(10, false, 1.5));

        
    }

public Joystick getRightJoystick() {
        return rightJoystick;
    }

public Joystick getLeftJoystick() {
        return leftJoystick;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}
