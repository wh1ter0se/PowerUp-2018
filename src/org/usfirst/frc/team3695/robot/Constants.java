package org.usfirst.frc.team3695.robot;

/** where all the static final variables get set */
public class Constants {
	
	
	
	/**  motor inverts */
	
	public static final double
		/// DRIFT VALUES
			DRIFT_DEADZONE   = .1,
			DRIFT_TURNOVER   = .6,
			MAX_DRIFT_RADIUS = 120, // this is in inches
									// min will be 1/10th of this
			MAX_DRIFT_OFFSET = 80,  // max angle to turn into drift
	
			
		/// BOT SPECS
			DISTANCE_BETWEEN_WHEELS = 20, // inches between wheels; used for turn calculations 
			WHEEL_DIAMETER = 8.1; // wheel diameter in inches

	public static final int
		/// CAMERA SPECS
			CAM_HEIGHT = 1080,
			CAM_WIDTH = 1920;
}
