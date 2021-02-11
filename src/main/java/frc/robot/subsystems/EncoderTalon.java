package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class EncoderTalon extends WPI_TalonSRX{
    public EncoderTalon(int canId){
        super(canId);
        configVoltageCompSaturation(12);
        enableVoltageCompensation(true);
        //Fast gear:
        config_kP(0, 1);
        config_kI(0, 0.0);
        //config_IntegralZone(0, 200);
        config_kD(0, 0);
        config_kF(0, 0.66);
        selectProfileSlot(0, 0);

    }

    @Override
    public void set(double speed) {
        //Fast gear
        double encSpeed=speed*1550;
        super.set(ControlMode.Velocity, encSpeed);
        //super.set(ControlMode.PercentOutput, speed);//just for testing, percentOut mode
    }
}