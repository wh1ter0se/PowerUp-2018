package org.usfirst.frc.team3695.robot;

/** where all the static final variables get set */
public class Constants {
	
	public static final double
		/// BOT SPECS
			DISTANCE_BETWEEN_WHEELS = 20, // inches between wheels; used for turn calculations 
			WHEEL_DIAMETER          = 6, // wheel diameter in inches
		/// MANIPULATOR
			SPINNY_SPEED = 1,
		/// RAMPS
			DRIVETRAIN_RAMP = 1,
		/// REDLINE CURVE POINTS
			REDLINE_START = 0,
			REDLINE_MID   = .125,
			REDLINE_END   = 1;

	
	public static final int
		/// DRIVE MOTORS
			RIGHT_MASTER    = 0,
				RIGHT_SLAVE = 6,
			LEFT_MASTER     = 2,
				LEFT_SLAVE  = 7,
		/// MANIPULATOR SPINNY BOIS
			LEFT_FLYWHEEL =  42,
			RIGHT_FLYWHEEL = 42,
		/// MAST MOTORS
			LEFT_PINION_MOTOR  = 1,
			RIGHT_PINION_MOTOR = 8,
			SCREW_MOTOR        = 42,
		/// CANDY CANE
			HOOK = 42;
			
	
	public static final int
		/// CAMERA SPECS
			CAM_HEIGHT = 1080,
			CAM_WIDTH  = 1920,
		/// I2C
			I2C_DEVICE_ADDRESS = 4,
		/// MANIPULATOR
			OPEN_ARMS  = 0,
			CLOSE_ARMS = 1,
		/// RPM'S
			REDLINE = 6000,
			IDLE    = 500;

	public static final boolean 
			LEFT_MOTOR_INVERT         = false,
			RIGHT_MOTOR_INVERT        = true,
			
			LEFT_PINION_MOTOR_INVERT  = false,
			RIGHT_PINION_MOTOR_INVERT = true,
			SCREW_MOTOR_INVERT        = false,
			
			LEFT_ARM_MOTOR_INVERT     = true,
			RIGHT_ARM_MOTOR_INVERT    = false,
			
			HOOK_MOTOR_INVERT		  = false;



}
