package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.commands.ManualCommandHook;
import org.usfirst.frc.team3695.robot.util.Xbox;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/** VROOM VROOM */
public class SubsystemHook extends Subsystem {
	
	
	private TalonSRX hook;
	
	/** applies left arm motor invert */
	public static final double hookify(double hook) {
		return hook * (Constants.HOOK_MOTOR_INVERT ? -1.0 : 1.0);
	}
	
	/** runs at robot boot */
    public void initDefaultCommand() {
    	setDefaultCommand(new ManualCommandHook());
    }
	
	/** gives birth to the CANTalons */
    public SubsystemHook(){
    	hook = new TalonSRX(Constants.HOOK);
    }

    public void swing(Joystick joy) {
    	double speed = 0;
    	speed = joy.getRawButton(Xbox.A) ? speed++ : speed; 
    	speed = joy.getRawButton(Xbox.B) ? speed-- : speed;
    	hook.set(ControlMode.PercentOutput, speed);
    }
    
    /** configures the voltage of each CANTalon */
    private void voltage(TalonSRX talon) {
    	// talon.configNominalOutputVoltage(0f, 0f);
    	// talon.configPeakOutputVoltage(12.0f, -12.0f);
    	// talon.enableCurrentLimit(true);
    	// talon.configContinuousCurrentLimit(30, 3000);
    }
           
    

}

