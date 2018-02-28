package org.usfirst.frc.team3695.robot;

/** where all the static final variables get set */
public class Constants {

	public static final double
		/// BOT SPECS
			DISTANCE_BETWEEN_WHEELS = 24, // inches between wheels; used for turn calculations
			WHEEL_DIAMETER          = 6, // wheel diameter in inches
		/// MANIPULATOR
			SPINNY_SPEED = 1,
		/// RAMPS
			DRIVETRAIN_RAMP = 1,
		/// REDLINE CURVE POINTS
			REDLINE_START = 0,
			REDLINE_MID   = .125,
			REDLINE_END   = 1;
	
	public static final double
		/// ENCODER MAGIC UNIT TRANSLATIONS
			LEFT_MAGIC_PER_INCHES = -214.560,
			RIGHT_MAGIC_PER_INCHES = 208.847,
		///SAFETY
			RECOVERY_SPEED = 0,
			DOCK_INHIBITOR = 1,
			TILT_ANGLE = 10;


	public static final int
		/// DRIVE MOTORS
			RIGHT_MASTER    = 6,
				RIGHT_SLAVE = 20,
			LEFT_MASTER     = 7,
				LEFT_SLAVE  = 2,
		/// MANIPULATOR SPINNY BOIS
			LEFT_FLYWHEEL =  43,
			RIGHT_FLYWHEEL = 42,
		/// MAST MOTORS
			LEFT_PINION_MOTOR  = 1,
			RIGHT_PINION_MOTOR = 4,
			SCREW_MOTOR        = 5,
		/// PID
			RIGHT_PID = 0,
			LEFT_PID = 0,
			TIMEOUT_PID = 10000;

	public static final int
		/// I2C
			I2C_DEVICE_ADDRESS = 4,
		/// RPM'S
			REDLINE = 6000,
			IDLE    = 500;

	
	public static final int
		/// MANIPULATOR
			OPEN_ARMS  = 5,
			CLOSE_ARMS = 4,
		/// CANDY CANE
			RAISE_HOOK = 6,
			LOWER_HOOK = 7;
	
	public static class OOF {
		public static final boolean
			LEFT_MOTOR_INVERT         = false,
			RIGHT_MOTOR_INVERT        = true, 
	
			LEFT_PINION_MOTOR_INVERT  = false,
			RIGHT_PINION_MOTOR_INVERT = true,
			SCREW_MOTOR_INVERT        = true,
	
			LEFT_ARM_MOTOR_INVERT     = true,
			RIGHT_ARM_MOTOR_INVERT    = false,
	
			HOOK_MOTOR_INVERT		  = false;
		public static final double
            P = 0.5,
            I = 0,
            D = 0,
            F = 0;
	}
	
	public static class SWISS {
		public static final boolean
			LEFT_MOTOR_INVERT         = false,
			RIGHT_MOTOR_INVERT        = false,
	
			LEFT_PINION_MOTOR_INVERT  = false,
			RIGHT_PINION_MOTOR_INVERT = true,
			SCREW_MOTOR_INVERT        = false,
	
			LEFT_ARM_MOTOR_INVERT     = true,
			RIGHT_ARM_MOTOR_INVERT    = false,
	
			HOOK_MOTOR_INVERT		  = false;
        public static final double
                P = 0.5,
                I = 0,
                D = 0,
                F = 0;
	}
	

	public static class AutonomousConstants {
		//Save distances in inches
		public static final int DIST_TO_SWITCH_FROM_SIDE = 168;
		public static final int DIST_PASS_PORTAL = 36;
		public static final int DIST_CENTER_LINE_SWITCH_ALIGN = 48;
		public static final int DIST_BLOCKS_TO_SWITCH = 42;
		public static final int DIST_WALL_TO_LINE = 120;
		public static final int DIST_WALL_TO_BLOCKS = 98;
		public static final int DIST_BLOCK_TO_MIDDLE_OF_SWITCH = 28;
		public static final int DIST_WALL_TO_ENEMY_BLOCKS = 463;
		public static final int DIST_WALL_TO_SWITCH_BLOCKS = 196;
		public static final int DIST_SWITCH_BLOCK_TO_SCALE = 128;
		public static final int DIST_WALL_TO_SCALE = 324; //Goes right in front of the scale
		public static final double DIST_WALL_TO_SWITCH_BLOCK_MID = 228.74;

		public static final int ROT_90_CLOCKWISE = 90;
		public static final int ROT_90_COUNTERCLOCKWISE = -90;
		public static final int ROT_180 = 180;

		public static final int SPIT_LENGTH = 500;
		public static final int WAIT_TO_ADJUST_MAST = 300;
	}

	public static class VisionConstants {
		public static final int CAM_HEIGHT = 1080;
		public static final int CAM_WIDTH  = 1920;

		public static final int SCREW_ID = 0;
		public static final int HOOK_ID = 1;
		public static final int	LEFT_ID = 2;
		public static final int RIGHT_ID = 3;

	}

//	/** where all the static final variables for arduino get set */
//	public static class ArduinoPatterns {
//
//		public static final int
//				SOLID_RED =         1,
//				SOLID_BLUE =        2,
//				RUNNING_RED =       3,
//				RUNNING_BLUE =      4,
//				RAINBOW_SLOW =      5,
//				RAINBOW_FAST =      6,
//				RAINBOW_SONIC =     7, // plz yes
//				GOD_BLESS_AMERICA = 8;
//	}
}
