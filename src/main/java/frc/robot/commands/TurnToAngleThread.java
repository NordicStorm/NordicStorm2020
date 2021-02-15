package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

public class TurnToAngleThread extends Thread {
    double currentAccummulator = 0;
    double lastTime = 0;
    double pVal = 0.023;
    double iVal = 0.00045;
    double maxTurn = 0.2;
    double minTurn = 0.15;
    double maxAccum=600*999;
    double targetAngle;
    boolean done=false;

    public TurnToAngleThread(double targetAngle) {
        this.targetAngle = targetAngle;
        currentAccummulator = 0;
        lastTime = 0;
    }

    public void setDone(boolean done){
        this.done=done;
    }
    public boolean getIsDone(){
        return done;
    }
    @Override
    public void run() {
        while (!done && !(Robot.oi.getLeftJoystick().getRawButton(9))) {

            double currAngle = Robot.drivetrain.getAngle();
            double diff = Drivetrain.angleDiff(currAngle, targetAngle);
            double turnVal = diff * pVal;
            double error = diff;
            double rotationRate=Robot.drivetrain.getRotationalSpeed();
            double desiredSpeed=diff*0.1;
            double absError = Math.abs(error);
            
            //boolean moving=Robot.drivetrain.getRotationalSpeed();
            turnVal += iVal * currentAccummulator;
            if(absError>10 || Math.abs(rotationRate)<3){
                if (Math.abs(turnVal) > maxTurn) {
                    turnVal = Math.copySign(maxTurn, turnVal);
                }
                if (Math.abs(turnVal) < minTurn ) {
                    turnVal = Math.copySign(minTurn, turnVal);
                }
            }
            if(absError<0.5){
                turnVal=0;
            }
            
            Robot.drivetrain.directSet(0, -turnVal);
            /*
            double now = System.currentTimeMillis();
            double timeDiff = now - lastTime;
            lastTime = now;
            if (timeDiff < 99999) {// aka not the first one
                //currentAccummulator += timeDiff * error;
                currentAccummulator+=Math.copySign(timeDiff, error);
            }
            if (Math.abs(rotationRate)>10) {
                //if (absError > 10 || absError < 0.5) {
                    currentAccummulator = 0;
                //}
            }
            if (Math.abs(currentAccummulator) > maxAccum ) {
                currentAccummulator = Math.copySign(maxAccum, currentAccummulator);
            }*/
            if(absError<0.5 && Math.abs(rotationRate)<0.1){
                done=true;
            }
            SmartDashboard.putNumber("accum", currentAccummulator);

        }
        Robot.drivetrain.drive(0,0);
        Robot.drivetrain.setEncMode(true);
    }

}