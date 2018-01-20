package org.usfirst.frc.team3695.robot;

/** where all the static final variables get set */
public class Constants {
	
	
	
	/**  motor inverts */
	
	public static final double
		/// BOT SPECS
			DISTANCE_BETWEEN_WHEELS = 20, // inches between wheels; used for turn calculations 
			WHEEL_DIAMETER = 6, // wheel diameter in inches
		/// MANIPULATOR
			SPINNY_SPEED = 1;

	public static final int
		/// MOTOR VALUES
			RIGHT_MASTER = 0,
				RIGHT_SLAVE = 1,
			LEFT_MASTER = 2,
				LEFT_SLAVE = 3,
		/// MANIPULATOR SPINNY BOIS
			LEFT_ARM = 4,
			RIGHT_ARM = 5,
		/// CAMERA SPECS
			CAM_HEIGHT = 1080,
			CAM_WIDTH = 1920;

	public static final boolean LEFT_MOTOR_INVERT  = false,
								RIGHT_MOTOR_INVERT = true;

}
