package frc.robot;

public class Util {
    public static double map(double x, double in_min, double in_max, double out_min, double out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
    public static double absClamp(double val, double max){
        if(val<-max){
            return -max;
        }
        if(val>max){
            return max;
        }
        return val;
    }
    public static double clamp(double n, double min, double max){
        return Math.max(Math.min(n, max), min);
    }
    public static double minWithAbs(double a, double b){
        return Math.abs(a) < Math.abs(b) ? a : b;
    }
    public static double maxWithAbs(double a, double b){
        return Math.abs(a) > Math.abs(b) ? a : b;
    }
    public static double signedSquare(double x) { 
        if(x<0) return -x*x; 
        else return x*x; 
    }

}