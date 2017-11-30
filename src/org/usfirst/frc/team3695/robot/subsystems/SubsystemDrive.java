package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.commands.ManualCommandDrive;
import org.usfirst.frc.team3695.robot.enumeration.Drift;
import org.usfirst.frc.team3695.robot.util.Xbox;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/** VROOM VROOM */
public class SubsystemDrive extends Subsystem {
	
	/// diameter of the wheel in inches
	public static final double WHEEL_DIAM_INCHES = 8.1;

	
	/// error variables
	public static final long START_READ_ERROR_DELAY = 500;
	public static final int TARGET = 300;
	
	
	/// drift variables
	public static int    OFFSET,        // the degrees
	                     METADIRECTION; // on button down, this resets to 0; the angle relative to that initial angle (changes on physical rotation)
	                     				// pretty much the cumulative angle since button down, minus offset
	public static double ROT_SPEED,     // the speed of rotation (min 0, max 10.00)
						 ROT_RADIUS;    // turn radius in inches
	
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
    		// slave EX:  left2.changeControlMode(CANTalon.TalonControlMode.Follower);
    		//			  left2.set(left1.getDeviceID());
    }
    
    public void driftDrive(Joystick joy) {
    	// TODO oops, wrote this only for turning left. fix that.
    	// TODO for compatibility, add a boolean IN_DRIFT
    	Drift status;
    		 if (Xbox.LEFT_X(joy) < (Constants.DRIFT_DEADZONE * -1)) { status = Drift.DONUT; }
    	else if (Xbox.LEFT_X(joy) < (Constants.DRIFT_DEADZONE))      { status = Drift.DEAD; }
    	else if (Xbox.LEFT_X(joy) < (Constants.DRIFT_TURNOVER))      { status = Drift.POWERSLIDE; }
    	else														 { status = Drift.TURNOVER; }
    	
    	ROT_SPEED = (Xbox.LT(joy) + Xbox.LT(joy)) * 10d;
    	
		 switch(status) {
			 case DONUT:
				 			ROT_RADIUS = (Xbox.LEFT_X(joy)) + Constants.DRIFT_DEADZONE; // put stick on a 0-0.9 scale
				 				ROT_RADIUS  = 1 - ROT_RADIUS; // flip it to a .1-1 scale, outermost being .1 and innermost being 1
				 				ROT_RADIUS *= Constants.MAX_DRIFT_RADIUS; // apply the .1-1 scale to the max radius
			 				OFFSET = (int) (Constants.MAX_DRIFT_OFFSET * Math.abs(Xbox.LEFT_X(joy))); /// GOOD FOR EITHER DIRECTION; sets offset for max on tight radius
				 			driveMecanumRot(ROT_RADIUS, ROT_SPEED, OFFSET);
				 			break;
			 case DEAD:
				 			// this will just continue the last known radius and speed
				 			break;
			 case POWERSLIDE:
				 			strafeWithOffset(ROT_SPEED, OFFSET);
				 			break;
			 case TURNOVER:
				 			
				 			break;
		 }
    }
    
    
    /**
     *  drives in an arc, partially strafing
     *  negative radius means left, positive radius means right
     *  	(these still apply going backwards)
     *  speed is 0-10.0
     *  off is the degrees offset the front of the bot is
     *  	(negative offset is pointed out, positive is pointed in)
     * */
    private void driveMecanumRot(double radius, double speed, int offset) {
    	
    }
    
    /** move sideways at an angle */
    private void strafeWithOffset(double speed, int offset) {
    	
    }
    /** configures the voltage of each CANTalon */
    private void voltage(CANTalon talon) {
    	talon.configNominalOutputVoltage(0f, 0f);
    	talon.configPeakOutputVoltage(12.0f, -12.0f);
    	talon.EnableCurrentLimit(true);
    	talon.setCurrentLimit(30);
    }
}

