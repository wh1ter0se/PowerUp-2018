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
	
	
	//private TalonSRX leftPinion;
	//private TalonSRX rightPinion;

	
	/** runs at robot boot */
    public void initDefaultCommand() {
    	setDefaultCommand(new ManualCommandGrow()); }
	
	/** gives birth to the CANTalons */
    public SubsystemMast(){
    //	leftPinion = new TalonSRX(Constants.LEFT_PINION_MOTOR);
    //	rightPinion = new TalonSRX(Constants.RIGHT_PINION_MOTOR);
    	//voltage(leftPinion);
    	//voltage(rightPinion);
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
    //	leftPinion.set(ControlMode.PercentOutput, pinionate(speed));
    //	rightPinion.set(ControlMode.PercentOutput, screwify(speed));
    }

    /** configures the voltage of each CANTalon */
    private void voltage(TalonSRX talon) {
    	// talon.configNominalOutputVoltage(0f, 0f);
    	// talon.configPeakOutputVoltage(12.0f, -12.0f);
    	// talon.enableCurrentLimit(true);
    	talon.configContinuousCurrentLimit(35, 300);
    }
    
    

}

