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
	
	/// drift variables
	public static int     OFFSET,        // the degrees the bot is tilted inward
	                      METADIRECTION; // on button down, this resets to 0; the angle relative to that initial angle (changes on physical rotation)
	                     			 	 // pretty much the cumulative angle since button down, minus offset
	public static double  ROT_SPEED,     // the speed of rotation (min 0, max 10.00)
						  ROT_RADIUS;    // turn radius in inches
	public static boolean IN_DRIFT,		 // if the bot is currently in a drift
						  DRIFT_IS_CW;   // if the drift is turning right
	
	// instantiate the CANTalons here
		// EX: private CANTalon left1;

	
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
    
    /** configures the voltage of each CANTalon */
    private void voltage(CANTalon talon) {
    	talon.configNominalOutputVoltage(0f, 0f);
    	talon.configPeakOutputVoltage(12.0f, -12.0f);
    	talon.EnableCurrentLimit(true);
    	talon.setCurrentLimit(30);
    }
    
    
    
    
    
    
    /// EVERYTHING BELOW IS DRIFT_DRIVE SHELL CODE
    
    
    /** method called by ManualCommandDrive
     * 
     * @param joy
     */
    public void driftDrive(Joystick joy) {
    	double joyVal = Xbox.LEFT_X(joy);
    	if (!IN_DRIFT) { // initiate drift if it hasn't been initiated already
    		IN_DRIFT = true;
    		if (joyVal > 0) { DRIFT_IS_CW = true; }
    	}
    	if (DRIFT_IS_CW) { joyVal = -1 * joyVal; } // align joyVal to the write negative and positive values

    	
    	ROT_SPEED = (Xbox.LT(joy) + Xbox.LT(joy)) * 10d;
    	Drift status = updateDriftStatus(joyVal);
		 switch(status) {
			 case DONUT:
				 			ROT_RADIUS = (Xbox.LEFT_X(joy)) + Constants.DRIFT_DEADZONE; // put stick on a 0-0.9 scale
				 				ROT_RADIUS  = 1 - ROT_RADIUS; // flip it to a .1-1 scale, outermost being .1 and innermost being 1
				 				ROT_RADIUS *= Constants.MAX_DRIFT_RADIUS; // apply the .1-1 scale to the max radius
			 				OFFSET = (int) (Constants.MAX_DRIFT_OFFSET * Math.abs(Xbox.LEFT_X(joy))); /// sets offset for max on tight radius
				 			driveMecanumRot(ROT_RADIUS, ROT_SPEED, OFFSET);
				 			break;
			 case DEAD:
				 			// this will just continue the last known radius, offset, and speed
				 			break;
			 case POWERSLIDE:
				 			driveAngle(ROT_SPEED, 90 - OFFSET);
				 			break;
			 case TURNOVER:			
				 			DRIFT_IS_CW = !DRIFT_IS_CW;
				 			break;
		 }
    }
    
    /** moved from inside driftDrive; sets up the status switch in driftDrive */
    private Drift updateDriftStatus(double joy) {
    	Drift status;
			 if (joy < (Constants.DRIFT_DEADZONE * -1)) { status = Drift.DONUT; }
		else if (joy < (Constants.DRIFT_DEADZONE))      { status = Drift.DEAD; }
		else if (joy < (Constants.DRIFT_TURNOVER))      { status = Drift.POWERSLIDE; }
		else		 									{ status = Drift.TURNOVER; }
		return status;
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
    	CANTalon FL = null, BL = null, FR = null, BR = null; // remove when CANTalons are instantiated
    	
    	double leftRotPower, rightRotPower;
    	if (radius < 0) { // if turning left, give more power to right side
    		rightRotPower = 1;
    		leftRotPower  = calculateInnerRotPower(radius, Constants.DISTANCE_BETWEEN_WHEELS); }
    	else { // if turning right, give more power to left side
    		rightRotPower = calculateInnerRotPower(radius, Constants.DISTANCE_BETWEEN_WHEELS);
    		leftRotPower  = 1; }

    	/// standard mecanum code (but the front wheels are averaged with the values for rotation)
	    	FL.set(( 
	    			Math.toRadians(
	    			Math.cos(90 - (double) offset)
	    			* speed) 
	    			+ leftRotPower) 
	    			/ 2);
	    	FR.set((
	    			Math.toRadians(
					Math.sin(90 - (double) offset) 
					* speed) 
					+ rightRotPower) 
					/ 2);
	    	BL.set(Math.toRadians(Math.sin(90 - (double) offset)) * speed);
	    	BR.set(Math.toRadians(Math.cos(90 - (double) offset)) * speed);
    }
    
    /** 
     * strafes/moves at an angle 
     * speed should be -1.00 to 1.00
     * offset must be -90 to 90 degrees
     * */
    private void driveAngle(double speed, int angle) { // right = 0, up = 90, left = 180, down = 270 
    	CANTalon FL = null, BL = null, FR = null, BR = null; // remove when CANTalons are instantiated
    	
    	/// standard mecanum strafing code
			FL.set(Math.toRadians(Math.cos(angle)) * speed);
			FR.set(Math.toRadians(Math.sin(angle)) * speed);
			BL.set(Math.toRadians(Math.sin(angle)) * speed);
			BR.set(Math.toRadians(Math.cos(angle)) * speed);
    	
    }
    
    /** calculates inner power in roation, outer being max */
    private double calculateInnerRotPower(double radius, double botWidth) {
    	return radius / (radius + botWidth);
    }

}

