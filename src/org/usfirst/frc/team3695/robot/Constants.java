package org.usfirst.frc.team3695.robot;

/** where all the static final variables get set */
public class Constants {
	
	public static final double
		/// BOT SPECS
			DISTANCE_BETWEEN_WHEELS = 20, // inches between wheels; used for turn calculations 
			WHEEL_DIAMETER = 6, // wheel diameter in inches
		/// MANIPULATOR
			SPINNY_SPEED = 1,
		// RAMPS
			DRIVETRAIN_RAMP = 1;

	
	public static final int
		/// DRIVE MOTORS
			RIGHT_MASTER = 0,
				RIGHT_SLAVE = 1,
			LEFT_MASTER = 2,
				LEFT_SLAVE = 3,
		/// MANIPULATOR SPINNY BOIS
			LEFT_ARM = 4,
			RIGHT_ARM = 5,
		/// MAST MOTORS
			PINION_MOTOR = 6,
			SCREW_MOTOR = 7;
			
	
	public static final int
		/// CAMERA SPECS
			CAM_HEIGHT = 1080,
			CAM_WIDTH = 1920,
		/// I2C
			I2C_DEVICE_ADDRESS = 0,
		/// MANIPULATOR
			OPEN_ARMS = 0,
			CLOSE_ARMS = 1;

	public static final boolean 
			LEFT_MOTOR_INVERT  = false,
			RIGHT_MOTOR_INVERT = true,
			PINION_MOTOR_INVERT = false,
			SCREW_MOTOR_INVERT = false;



}
