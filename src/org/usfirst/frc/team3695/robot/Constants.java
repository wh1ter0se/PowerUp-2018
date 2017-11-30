package org.usfirst.frc.team3695.robot;

/** where all the static final variables get set */
public class Constants {
	
	
	
	/**  motor inverts */
	public static final boolean 
			LEFT_MOTOR_INVERT  = true, // this assumes master-slave tank drive only, change amount as needed
			RIGHT_MOTOR_INVERT = false;
	
	public static final double
			DRIFT_DEADZONE   = .1,
			DRIFT_TURNOVER   = .6,
			MAX_DRIFT_RADIUS = 120, // this is in inches
									// min will be 1/10th of this
			MAX_DRIFT_OFFSET = 80,  // max angle to turn into drift
	
	
			DISTANCE_BETWEEN_WHEELS = 20; // inches between wheels; used for turn calculations 
			
}
