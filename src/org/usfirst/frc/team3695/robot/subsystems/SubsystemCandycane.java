package org.usfirst.frc.team3695.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3695.robot.Constants;

public class SubsystemCandycane extends Subsystem{

    private TalonSRX motor;

    protected void initDefaultCommand() { }

    public SubsystemCandycane() {
        motor = new TalonSRX(Constants.CANDYCANE_MOTOR);
        voltage(motor);
    }

    //Move the candycane
    public void move(double speed){
        motor.set(ControlMode.PercentOutput, speed);
    }

    /** configures the voltage of each TalonSRX */
    private void voltage(TalonSRX talon) {
        // talon.configNominalOutputVoltage(0f, 0f);
        // talon.configPeakOutputVoltage(12.0f, -12.0f);
        // talon.enableCurrentLimit(true);
        talon.configContinuousCurrentLimit(35, 300);
    }
}
