
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
        sections.add(new DriveDistancePath(distance*913, backward, false, 0, 1));
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
        return addStraight(distance, backward, targetAngle, 1);
    }
    /**
     * 
     * @param distance distance in feet
     * @param backward false=forward
     * @param targetAngle angle that the **front** of the robot should try to be facing
     * @param speed the max speed that the robot will try to achieve. 0 to 1. One by default.
     * @return
     */
    public MultiPartPath addStraight(double distance, boolean backward, double targetAngle, double speed){
        sections.add(new DriveDistancePath(distance*913, backward, true, targetAngle, speed));
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
     * Follow and pick up a single ball
     * @param handleOwnIntake if it should control starting and stopping the intake
     * @param maxSpeed max speed to drive
     * @return this path for chaining
     */
    public MultiPartPath addBallGrab(boolean handleOwnIntake, double maxSpeed){
        sections.add(new FollowBall(handleOwnIntake, true, maxSpeed));
        return this;
    }
    /**
     * Follow and pick up a single ball
     * @param handleOwnIntake if it should control starting and stopping the intake
     * @return this path for chaining
     */
    public MultiPartPath addBallGrab(boolean handleOwnIntake){
        return addBallGrab(handleOwnIntake, 0.75);
    }
    /**
     * Follow and pick up a single ball
     * @return this path for chaining
     */
    public MultiPartPath addBallGrab(){
        return addBallGrab(false);
    }

    /**
     * Shoot balls that are already in the shooter
     * @param numShots
     * @return
     */
    public MultiPartPath addBallShoot(int numShots){
        sections.add(new ShootBalls(numShots));
        return this;
    }
    public MultiPartPath addShooterAdjustment(int numShots){
        sections.add(new ShootBalls(numShots));
        return this;
    }
    /**
     * Mostly just like the arc, except it pivots in place
     * @param targetAngle
     * @param turnSpeed Wheel speeds during pivot, should probably be 0.25
     * @param turnRight
     * @param stopBefore If the robot should come to a stop before the pivot. Should normally be true.
     */
    public MultiPartPath addPivot(double targetAngle, double turnSpeed, boolean turnRight, boolean stopBefore){
        if(stopBefore){
            addStop();
        }
        addRawArc(targetAngle, -turnSpeed, turnSpeed, turnRight);
        return this;
    }
    /**
     * Runs the previous and the next commands at max speed.
     */
    public MultiPartPath atMaxSpeed(){
        sections.add(new AtMaxSpeed(1));
        return this;
    }
    public MultiPartPath atSpeed(double speed){
        sections.add(new AtMaxSpeed(speed));
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
     * @return this same path so it can go into an addSequential
     */
    public MultiPartPath finalizePath(){
        double currentAngle=startingAngle;
        if(endStop){
            sections.add(new StopMovement()); // add stop at end for calculations of speed
        }
        for(PathSection section : sections){
            if(section.isPassive()){ continue; }
            currentAngle=section.modifyAngle(currentAngle);
        }
        for(int i=1; i<sections.size()-1; i++){//iterate over all except first and last (the stops)
            PathSection section = sections.get(i);
            PathSection prev = sections.get(i-1);
            PathSection next = sections.get(i+1);
            section.finalizeForPath(seekToNonPassive(i, false), seekToNonPassive(i, true));
        }
        for(PathSection section : sections){
            addSequential(section);
        }
        this.hasFinalized=true;
        return this;
    }
    public PathSection seekToNonPassive(int startI, boolean seekForward){
        PathSection section;
        int iOffset=1;
        if(seekForward){
            section=sections.get(startI+iOffset);
            while(section.isPassive()){
                iOffset+=1;
                section = sections.get(startI+iOffset);
            }
        }else{
            section = sections.get(startI-iOffset);
            while(section.isPassive()){
                iOffset+=1;
                section = sections.get(startI-iOffset);
            }
        }
        return section;
    }
    @Override
    protected void initialize() {
        if(!hasFinalized){
            throw new IllegalStateException("MultiPartPath has not been finalized!");
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
