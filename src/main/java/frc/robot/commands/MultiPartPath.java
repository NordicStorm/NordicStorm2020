
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

import java.util.ArrayList;
import java.util.List;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

/**
 *
 */
public class MultiPartPath extends CommandGroup {

    boolean hasFinalized=false;
    boolean endStop=true;
    boolean startStop=true;
    double startingAngle;
    List<PathSection> sections = new ArrayList<>();
    /**
     * Used for multi step movements, keeping speeds good.
     * @param startingAngle what angle the robot is at at the start of the path.
     * It does not turn to there, it is just used for information.
     */
    public MultiPartPath(double startingAngle) {
        this(startingAngle, true, true);
    }
    public MultiPartPath(double startingAngle, boolean stopAtStart, boolean stopAtEnd) {
        this.startStop=stopAtStart;
        this.endStop=stopAtEnd;
        this.startingAngle=startingAngle;
        if(startStop){
            sections.add(new StopMovement());//start path stopped
        }
        requires(Robot.drivetrain);
    }


     /**
     * Add a straight segment to the path
     * @param distance distance in feet
     * @param backward true or false. false=forward
     */
    public MultiPartPath addStraight(double distance, boolean backward){
        sections.add(new DriveDistancePath(distance*913, backward, false, 0));
        return this;
    }
    /**
     * 
     * @param distance distance in feet
     * @param backward false=forward
     * @param targetAngle angle that the **front** of the robot should try to be facing
     * @return
     */
    public MultiPartPath addStraight(double distance, boolean backward, double targetAngle){
        sections.add(new DriveDistancePath(distance*913, backward, true, targetAngle));
        return this;
    }

    /**
     * Drive in an arc with raw numbers. Example: gridTurn is innerSpeed=0.051, outerSpeed=0.4025
     * @param targetAngle target angle in degrees
     * @param innerSpeed inner wheel speed
     * @param outerSpeed outer speed. Please make sure this is bigger than innerSpeed
     * @param arcRight if it should go right. This refers to the actual rotation direction of the robot, so ignore backward/forward motion. Imagine it was pivoting.
     * @param backward true=arc backward, false=arc forward
     * @return
     */
    public MultiPartPath addRawArc(double targetAngle, double innerSpeed, double outerSpeed, boolean arcRight, boolean backward){
        sections.add(new DriveArcPath(targetAngle, innerSpeed, outerSpeed, arcRight, backward));
        return this;
    }
    /**
     * Drive in an arc with raw numbers. Example: gridTurn is innerSpeed=0.051, outerSpeed=0.4025
     * @param targetAngle target angle in degrees
     * @param innerSpeed inner wheel speed
     * @param outerSpeed outer speed. Please make sure this is bigger than innerSpeed
     * @param arcRight if it should go right
     * @return
     */
    public MultiPartPath addRawArc(double targetAngle, double innerSpeed, double outerSpeed, boolean arcRight){
        return addRawArc(targetAngle, innerSpeed, outerSpeed, arcRight, false);
    }

    /**
     * Drive in an arc on the grid(radius 2.5ft)
     * @param targetAngle In degrees
     * @param arcRight true=right, false=left
     * @param backward true=backward, false=forward
     * @return
     */
    public MultiPartPath addGridArc(double targetAngle, boolean arcRight, boolean backward){
        double gridTurnInner=0.051;//0.041
        double gridTurnOuter=0.4025;//0.4025
        return addRawArc(targetAngle, gridTurnInner, gridTurnOuter, arcRight, backward);
        //sections.add(new DriveArcPath(targetAngle, gridTurnInner, gridTurnOuter, arcRight, is360, false));
        //return this;
    }
/**
     * Drive in an arc on the grid(radius 2.5ft)
     * @param targetAngle In degrees
     * @param arcRight true=right, false=left
     * @return
     */
    public MultiPartPath addGridArc(double targetAngle, boolean arcRight){
        return addGridArc(targetAngle, arcRight, false);
    }

    /**
     * Runs the previous and the next commands at max speed.
     */
    public MultiPartPath atMaxSpeed(){
        sections.add(new AtMaxSpeed());
        return this;
    }

    public MultiPartPath addStop(){
        sections.add(new StopMovement());
        return this;
    }
    /**
     * Make it so this does not wait to stop before moving out of the path
     * @return
     */
    public MultiPartPath coastOut(){
        endStop=false;
        return this;
    }

    /**
     * Make it so this does not wait to stop before moving into the path
     * @return
     */
    public MultiPartPath coastIn(){
        if(startStop){
            sections.remove(0);
            startStop=false;
        }
        return this;
    }

    /**
     * Finalize and calculate speeds for path segments. Must be called before execution.
     * @return this same path for chaining
     */
    public MultiPartPath finalizePath(){
        double currentAngle=startingAngle;
        if(endStop){
            sections.add(new StopMovement()); // add stop at end for calculations of speed
        }
        for(PathSection section : sections){
            currentAngle=section.modifyAngle(currentAngle);
        }
        for(int i=1; i<sections.size()-1; i++){//iterate over all except first and last (the stops)
            sections.get(i).finalizeForPath(sections.get(i-1), sections.get(i+1));
        }
        for(PathSection section : sections){
            addSequential(section);
        }
        this.hasFinalized=true;
        return this;
    }
    @Override
    protected void initialize() {
        if(!hasFinalized){
            throw new IllegalStateException("Path has not been finalized!");
        }
    }
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        
    }
    
    
    

    // Called once after isFinished returns true
    @Override
    protected void end() {
        
    }


    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
