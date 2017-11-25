package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.commands.ManualCommandDrive;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/** VROOM VROOM */
public class SubsystemDrive extends Subsystem {
	
	/// diameter of the wheel in inches
	public static final double WHEEL_DIAM_INCHES = 8.1;

	
	/// error variables
	public static final long START_READ_ERROR_DELAY = 500;
	public static final int TARGET = 300;
	
	
	// instantiate the CANTalons here
		// EX: private CANTalon left1;

	
	/** runs at robot boot */
    public void initDefaultCommand() {
    	setDefaultCommand(new ManualCommandDrive()); }
    
    
    /** converts RPM to inches per second */
    public static final double rpm2ips(double rpm) {
    	return rpm / 60.0 * WHEEL_DIAM_INCHES * Math.PI; }
    
    
    /** converts an inches per second number to RPM */
    public static final double ips2rpm(double ips) {
    	return ips * 60.0 / WHEEL_DIAM_INCHES / Math.PI; }
    
    
    /** converts rotations to distance traveled in inches */
    public static final double rot2in(double rot) {
    	return rot * WHEEL_DIAM_INCHES * Math.PI; }
    
    
    /** converts distance traveled in inches to rotations */
    public static final double in2rot(double in) {
    	return in / WHEEL_DIAM_INCHES / Math.PI; }
    
    
    /** applies left motor invert from constants */
    public static final double leftify(double left) {
		return left * (Constants.LEFT_MOTOR_INVERT ? -1.0 : 1.0); }

    
    /** applies right motor invert from constants */
	public static final double rightify(double right) {
		return right * (Constants.RIGHT_MOTOR_INVERT ? -1.0 : 1.0); }

	
	/** gives birth to the CANTalons */
    public SubsystemDrive(){
    	// assign initial values to CANTalons
    		// EX: left1 = new CANTalon(Constants.LEFT_MOTOR);
    	// call voltage for each instantiated CANTalon
    		// EX: voltage(left1);
    	// train each CANTalon
    		// master EX: left1.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
    		//			  left1.setEncPosition(0);
    		//			  left1.reverseSensor(false);
    		//  slave EX: left2.changeControlMode(CANTalon.TalonControlMode.Follower);
    		//			  left2.set(left1.getDeviceID());
    }
    
    
    /** configures the voltage of each CANTalon */
    private void voltage(CANTalon talon) {
    	talon.configNominalOutputVoltage(0f, 0f);
    	talon.configPeakOutputVoltage(12.0f, -12.0f);
    	talon.EnableCurrentLimit(true);
    	talon.setCurrentLimit(30);
    }
}

