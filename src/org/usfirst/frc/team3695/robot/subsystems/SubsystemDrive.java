package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.commands.ManualCommandDrive;
import org.usfirst.frc.team3695.robot.util.Xbox;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/** VROOM VROOM */
public class SubsystemDrive extends Subsystem {
	
	
	private TalonSRX left1;
	private TalonSRX left2;
	private TalonSRX right1;
	private TalonSRX right2;

	
	/** runs at robot boot */
    public void initDefaultCommand() {
    	setDefaultCommand(new ManualCommandDrive()); }
    
    
    /** converts RPM to inches per second */
    public static final double rpm2ips(double rpm) {
    	return rpm / 60.0 * Constants.WHEEL_DIAMETER * Math.PI; }
    
    
    /** converts an inches per second number to RPM */
    public static final double ips2rpm(double ips) {
    	return ips * 60.0 / Constants.WHEEL_DIAMETER / Math.PI; }
    
    
    /** converts rotations to distance traveled in inches */
    public static final double rot2in(double rot) {
    	return rot * Constants.WHEEL_DIAMETER * Math.PI; }
    
    
    /** converts distance traveled in inches to rotations */
    public static final double in2rot(double in) {
    	return in / Constants.WHEEL_DIAMETER / Math.PI; }
    
    /** apply left motor invert */
    public static final double leftify(double left) {
		return left * (Constants.LEFT_MOTOR_INVERT ? -1.0 : 1.0);
	}

    /** apply right motor invert */
	public static final double rightify(double right) {
		return right * (Constants.RIGHT_MOTOR_INVERT ? -1.0 : 1.0);
	}
	
	/** gives birth to the CANTalons */
    public SubsystemDrive(){
    	// masters
	    	left1 = new TalonSRX(Constants.LEFT_MASTER);
	    	right1 = new TalonSRX(Constants.RIGHT_MASTER);
    	
    	// slaves
	    	left2 = new TalonSRX(Constants.LEFT_SLAVE);
	    	right2 = new TalonSRX(Constants.RIGHT_SLAVE); 	
    }
    
    /** simple rocket league drive code; independent rotation and acceleration */
    public void driveRLTank(Joystick joy) {
    	double adder = Xbox.RT(joy) - Xbox.LT(joy);
    	double left = adder + (Xbox.LEFT_X(joy) / 1.333333);
    	double right = adder - (Xbox.LEFT_X(joy) / 1.333333);
    	
    	//Quick Truncate
    	left = (left > 1.0 ? 1.0 : (left < -1.0 ? -1.0 : left));
    	right = (right > 1.0 ? 1.0 : (right < -1.0 ? -1.0 : right));
    	    	
    	left1.set(ControlMode.PercentOutput, leftify(left));
    		left2.set(ControlMode.PercentOutput, leftify(left));
    	right1.set(ControlMode.PercentOutput, rightify(right));
    		right2.set(ControlMode.PercentOutput, rightify(right));
    	
    }
    
    /** drive code where rotation is dependent on acceleration */
    public void driveForza(Joystick joy, double ramp) {
    	double left = 0, 
    		   right = 0;
    	double acceleration = Xbox.RT(joy) - Xbox.LT(joy);
    	
    	if (Xbox.LEFT_X(joy) < 0) {
    		right = acceleration;
    		left = acceleration * ((2 * (1 - Math.abs(Xbox.LEFT_X(joy)))) - 1); 
    	} else if (Xbox.LEFT_X(joy) > 0) {
    		left = acceleration;
    		right = acceleration * ((2 * (1 - Math.abs(Xbox.LEFT_X(joy)))) - 1); 
    	} else {
    		left = acceleration;
    		right = acceleration;
    	}
    	
    	/// ramps
	    	left1.configOpenloopRamp(ramp, 0);
	    		left2.configOpenloopRamp(ramp, 0);
	    	right1.configOpenloopRamp(ramp, 0);
	    		right2.configOpenloopRamp(ramp, 0);
    	
	    left1.set(ControlMode.PercentOutput, leftify(left));
			left2.set(ControlMode.PercentOutput, leftify(left));
		right1.set(ControlMode.PercentOutput, rightify(right));
			right2.set(ControlMode.PercentOutput, rightify(right));
    }
    
    /** configures the voltage of each CANTalon */
    private void voltage(TalonSRX talon) {
    	// talon.configNominalOutputVoltage(0f, 0f);
    	// talon.configPeakOutputVoltage(12.0f, -12.0f);
    	// talon.enableCurrentLimit(true);
    	// talon.configContinuousCurrentLimit(30, 3000);
    }
    
    

}

