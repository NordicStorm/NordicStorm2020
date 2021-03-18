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

import frc.robot.commands.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class BallIntake extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private WPI_TalonSRX stageOne;
    private WPI_TalonSRX stageTwo;
    private WPI_TalonSRX stageThree;
    private WPI_TalonSRX stageFour;
    private WPI_TalonSRX stageFive;
    private DigitalInput sensorOne;
    private DigitalInput sensorTwo;
    private DigitalInput sensorThree;
    private DigitalInput sensorFour;
    private DigitalInput sensorFive;
    private WPI_TalonSRX kicker;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    WPI_TalonSRX[] stageMotors = new WPI_TalonSRX[5];
    DigitalInput[] stageSensors = new DigitalInput[5];
    long[] timeToStopStageBeingMovedTo = new long[] { 0, 0, 0, 0, 0 };
    long[] timeToStopStageBeingMovedFrom = new long[] { 0, 0, 0, 0, 0 };
    long approxKickerSpeed = 0;// approximation for current kicker speed based on time
    long timeToStopKicker = 0;
    double moveSpeed = 1;
    int moveTime = 500;
    int sensorMoveTime = moveTime - 300;// 200;
    int timeToKick = 200;

    int shooterFeedTime = 800;
    long now = 0;
    int timeToIntake = 500;
    boolean keepRunningIntake=false;
    public BallIntake() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        stageOne = new WPI_TalonSRX(5);

        stageTwo = new WPI_TalonSRX(6);

        stageThree = new WPI_TalonSRX(7);

        stageFour = new WPI_TalonSRX(8);

        stageFive = new WPI_TalonSRX(9);

        sensorOne = new DigitalInput(0);
        addChild("SensorOne", sensorOne);

        sensorTwo = new DigitalInput(1);
        addChild("SensorTwo", sensorTwo);

        sensorThree = new DigitalInput(2);
        addChild("SensorThree", sensorThree);

        sensorFour = new DigitalInput(3);
        addChild("SensorFour", sensorFour);

        sensorFive = new DigitalInput(4);
        addChild("SensorFive", sensorFive);

        kicker = new WPI_TalonSRX(11);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        stageMotors[0] = stageOne;
        stageMotors[1] = stageTwo;
        stageMotors[2] = stageThree;
        stageMotors[3] = stageFour;
        stageMotors[4] = stageFive;
        for (int i = 0; i < stageMotors.length; i++) {
            stageMotors[i].configVoltageCompSaturation(11.5);
            stageMotors[i].enableVoltageCompensation(true);
        }

        stageSensors[0] = sensorOne;
        stageSensors[1] = sensorTwo;
        stageSensors[2] = sensorThree;
        stageSensors[3] = sensorFour;
        stageSensors[4] = sensorFive;
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
        runBallsThroughSystem();
        
        // System.out.println("stage"+stageSensors[0].get());

    }

    private void runBallsThroughSystem() {
        boolean[] stageCanBeMovedInto = new boolean[] { false, false, false, false, false };
        long newTime = System.currentTimeMillis();
        long timePassed = newTime - now;
        now = newTime;

        if (timeToStopKicker > now) { // if the kicker stop time is in the future
            kicker.set(1);
            approxKickerSpeed += timePassed;
        } else {
            if (approxKickerSpeed > 0) {
                approxKickerSpeed -= timePassed;
                if (approxKickerSpeed < 0) {
                    approxKickerSpeed = 0;
                }
            }
            kicker.set(0);
        }

        for (int i = stageMotors.length - 1; i >= 0; i--) {

            boolean runStageMotor = false;
            DigitalInput thisStageSensor = stageSensors[i];
            boolean isOccupied = thisStageSensor.get();
            long timeStopFrom = timeToStopStageBeingMovedFrom[i];
            long timeStopTo = timeToStopStageBeingMovedTo[i];
            SmartDashboard.putBoolean("stage " + (i + 1), isOccupied);
            long timeLeftToMoveFrom = timeStopFrom - now; // the time that the stage the ball is moving from should run
            long timeLeftToMoveTo = timeStopTo - now; // the time the stage the ball is moving to should run
            // This stage will be commanded to move based on either of the above cases.

            boolean kickerReady = true;
            if (timeToStopKicker > now && i == 4 && approxKickerSpeed < 1000) {
                kickerReady = false;
            }
            if ((timeStopFrom > now || timeStopTo > now) && kickerReady) {// if we should run because we are moving a
                                                                          // ball from or to
                runStageMotor = true;
                //System.out.println("run" + i + "keep. MovingFrom:"+(timeStopFrom > now)+" MovingTo:"+(timeStopTo > now));

                //If we are moving to this stage and the
                if (timeLeftToMoveTo <= sensorMoveTime || i == 4) {// if we have gotten beyond the dead time
                    if (isOccupied || timeLeftToMoveTo < 0) { // and if the ball has gotten here, tell the previous
                                                              // stage
                                                              // and this one to stop
                        if (i != 0) {
                            timeToStopStageBeingMovedFrom[i - 1] = 0;
                        }
                        timeToStopStageBeingMovedTo[i] = 0;
                        //System.out.println("stop " + i + ". Sensor:"+isOccupied);
                        if(!isOccupied){
                            double qqq=0;
                        }

                    }
                }
            } else {
                boolean isAvailable = false;
                if (isOccupied) {
                    // System.out.println("occ" + i);
                    if (i != 4 && stageCanBeMovedInto[i + 1]) {// if this is not the top stage and the one above us is
                                                               // open
                        isAvailable = true; // this is now available since it is clearing out

                        if (timeLeftToMoveTo<0){// timeToStopStageBeingMovedFrom[i] < now) {
                            // set both this stage and the next stage moving, as the ball transfers

                            timeToStopStageBeingMovedFrom[i] = now + moveTime;
                            int toStageMoveTime = moveTime;

                            timeToStopStageBeingMovedTo[i + 1] = now + toStageMoveTime;
                            runStageMotor = true;
                            // System.out.println("run" + i + "start");
                        }
                    } else {
                        isAvailable = false;// if it is occupied and not moving out
                    }
                } else {
                    isAvailable = true; // if it is not occupied

                }
                stageCanBeMovedInto[i] = isAvailable;

            }
            WPI_TalonSRX stageMotor = stageMotors[i];

            if(i==0 && keepRunningIntake){
                runStageMotor=true;
            }
            if (runStageMotor) {

                stageMotor.set(moveSpeed);

            } else {
                stageMotor.set(0);
            }

        }
        // System.out.println("to" + Arrays.toString(timeToStopStageBeingMovedTo));

        // System.out.println("from" + Arrays.toString(timeToStopStageBeingMovedFrom));

        // System.out.println(Arrays.toString(stageCanBeMovedInto));

    }

    public void setIntakeRunning(boolean runIntake) {

        if (runIntake) {
            timeToStopStageBeingMovedTo[0] = now + timeToIntake;

        }
    }
    public void setLongRunningIntake(boolean runIntake) {
        keepRunningIntake=runIntake;
    }

    public boolean areBallsPresent() {
        return (stageSensors[0].get() || stageSensors[1].get() || stageSensors[2].get() || stageSensors[3].get()
                || stageSensors[4].get())
                || (timeToStopStageBeingMovedTo[1] > now || timeToStopStageBeingMovedTo[2] > now
                        || timeToStopStageBeingMovedTo[3] > now || timeToStopStageBeingMovedTo[4] > now)
                || // note, the first stage is omitted. This is because the intake may be spinning
                   // without balls
                (timeToStopStageBeingMovedFrom[1] > now || timeToStopStageBeingMovedFrom[2] > now
                        || timeToStopStageBeingMovedFrom[3] > now || timeToStopStageBeingMovedFrom[4] > now
                        || timeToStopKicker > now);
    }

    public void setRawMotors(double a, double b, double c, double d, double e, double f) {
        stageMotors[0].set(a);
        stageMotors[1].set(b);
        stageMotors[2].set(c);
        stageMotors[3].set(d);
        stageMotors[4].set(e);
        kicker.set(f);
    }

    /**
     * 
     * @return True if the ball was sucessfully fed up, false if it was not due to
     *         lack of ball or already-running feeder
     */
    public boolean feedBallToShooter() {
        // System.out.println(isBallAvailableToShoot());
        if (isBallAvailableToShoot()) {
            timeToStopKicker = now + timeToKick;
            timeToStopStageBeingMovedFrom[4] = now + shooterFeedTime;
            return true;
        }
        return false;
    }

    public boolean isBallAvailableToShoot() {
        if (!stageSensors[4].get()) {
            return false;
        }

        boolean lastStageRunning = timeToStopStageBeingMovedFrom[4] > now || timeToStopStageBeingMovedTo[4] > now;
        return !lastStageRunning;
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}
