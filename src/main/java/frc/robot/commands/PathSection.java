package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public abstract class PathSection extends Command {
    
    /**
     * 
     * @return the speed that this command wants to start at.
     */
    public abstract double getRequestedStartSpeed();
    /**
     * Only to be called on a previous command.
     * @return the speed that this was able to provide at the end.
     */
    public abstract double getProvidedEndSpeed();
    public abstract double modifyAngle(double oldAngle);
    public abstract void finalizeForPath(PathSection previous, PathSection next);
}
