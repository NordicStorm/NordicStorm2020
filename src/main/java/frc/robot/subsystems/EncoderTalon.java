package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class EncoderTalon extends WPI_TalonSRX{
    public EncoderTalon(int canId){
        super(canId);
        config_kP(0, 0);
        config_kI(0, 0);
        config_kD(0, 0);
        config_kF(0, 0);

    }

    @Override
    public void set(double speed) {
        double encSpeed=speed;
        //super.set(ControlMode.Velocity, encSpeed);
        super.set(encSpeed);//just for testing, percentOut mode
    }
}