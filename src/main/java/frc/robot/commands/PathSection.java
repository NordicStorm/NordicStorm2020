package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public abstract class PathSection extends Command {
    public abstract double getNeededStartSpeed();
    public abstract void finalizeForPath(PathSection previous, PathSection next);
    
}