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

import frc.robot.Util;

/**
 *
 */
public class DriveToObject{

    double pVal;
    double forwardMod;
    double maxTurn;
    double stopProximity;
    double proxPVal;
    double offset=0;
    boolean proxAdjust;
    double camWidth=315;
    double camHeight=207;
    public DriveToObject(double pVal, double forwardMod, double maxTurn, double stopProximity, double proxPVal, double camWidth, double camHeight) {
        this.pVal=pVal;
        this.forwardMod=forwardMod;
        this.maxTurn=maxTurn;
        this.stopProximity=stopProximity;
        this.proxAdjust=stopProximity>0;
        this.proxPVal=proxPVal;
        this.camHeight=camHeight;
        this.camWidth=camWidth;
    }    

    

    /**
     * 
     * @param xVal the x of the target
     * @param width the width of the target
     * @return speeds for wheels as [turn, forward]
     */
    protected double[] execute(double xVal, double width) {
        
        
        double offsetFromMiddle=xVal-(offset+(camWidth/2));
        double asPercent=offsetFromMiddle/(camWidth/2);
        double proximitySlow;
        if(proxAdjust){
            
            proximitySlow=Math.min(1,Math.max(0,(stopProximity-width)*proxPVal));
        }else{
            proximitySlow=1;
        }

        System.out.println("perc"+asPercent);
        //System.out.println(pVal);
        //System.out.println(maxTurn);

      


        double turnValue=Util.absClamp(asPercent*pVal, maxTurn);
 
        
        double forwardValue=(1-Math.abs(asPercent))*forwardMod*proximitySlow;

        //System.out.println(proximitySlow);

        
        System.out.println("turn"+turnValue);

        return new double[]{turnValue,forwardValue};
        

    }

/**
 * 
 * @param offsetPixels if positive, how far the target will end up to the right
 */
	public void setOffset(double offsetPixels) {
        offset=offsetPixels;
	}


}
