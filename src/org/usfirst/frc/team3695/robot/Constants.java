package org.usfirst.frc.team3695.robot;

/** where all the static final variables get set */
public class Constants {
	
	
	
	/**  motor inverts */
	
	public static final double
		/// BOT SPECS
			DISTANCE_BETWEEN_WHEELS = 20, // inches between wheels; used for turn calculations 
			WHEEL_DIAMETER = 8.1; // wheel diameter in inches

	public static final int
		/// MOTOR VALUES
			RIGHT_MASTER = 0,
				RIGHT_SLAVE = 1,
			LEFT_MASTER = 2,
				LEFT_SLAVE = 3,
		/// CAMERA SPECS
			CAM_HEIGHT = 1080,
			CAM_WIDTH = 1920;
}
