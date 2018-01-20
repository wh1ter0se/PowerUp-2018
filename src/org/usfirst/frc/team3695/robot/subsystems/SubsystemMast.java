package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.commands.ButtonCommandSpit;
import org.usfirst.frc.team3695.robot.commands.ManualCommandDrive;
import org.usfirst.frc.team3695.robot.commands.ManualCommandGrow;
import org.usfirst.frc.team3695.robot.enumeration.Direction;
import org.usfirst.frc.team3695.robot.util.Util;
import org.usfirst.frc.team3695.robot.util.Xbox;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/** the big, metal pole */
public class SubsystemMast extends Subsystem {
	
	
	private TalonSRX pinionMast;
	private TalonSRX screwMast;

	
	/** runs at robot boot */
    public void initDefaultCommand() {
    	setDefaultCommand(new ManualCommandGrow()); }
	
	/** gives birth to the CANTalons */
    public SubsystemMast(){
    	pinionMast = new TalonSRX(Constants.PINION_MOTOR);
    	screwMast = new TalonSRX(Constants.SCREW_MOTOR);
    }
    
    /** apply screw motor invert */
   	public static final double screwify(double right) {
   		return right * (Constants.SCREW_MOTOR_INVERT ? -1.0 : 1.0);
   	}
   	
   	/** apply pinion motor invert */
   	public static final double pinionate(double right) {
   		return right * (Constants.PINION_MOTOR_INVERT ? -1.0 : 1.0);
   	}
    
   	/** raise the mast at RT-LR trigger speed */
    public void moveBySpeed(Joystick joy) {
    	double speed = Xbox.RT(joy) - Xbox.LT(joy);
    	pinionMast.set(ControlMode.PercentOutput, pinionate(speed));
    	screwMast.set(ControlMode.PercentOutput, screwify(speed));
    }

    /** configures the voltage of each CANTalon */
    private void voltage(TalonSRX talon) {
    	// talon.configNominalOutputVoltage(0f, 0f);
    	// talon.configPeakOutputVoltage(12.0f, -12.0f);
    	// talon.enableCurrentLimit(true);
    	// talon.configContinuousCurrentLimit(30, 3000);
    }
    
    

}

