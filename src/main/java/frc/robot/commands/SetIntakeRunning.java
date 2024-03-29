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
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 *
 */
public class SetIntakeRunning extends InstantCommand {

    boolean run;
    public SetIntakeRunning(boolean run) {
        this.run=run;
    }

    @Override
    protected void execute() {
        Robot.ballIntake.setLongRunningIntake(run);
    }

}
