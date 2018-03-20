package org.usfirst.frc.team3695.robot;

/** where all the static final variables get set */
public class Constants {

	public static final double
		/// BOT SPECS
			DISTANCE_BETWEEN_WHEELS = 26, // inches between wheels; used for turn calculations
			WHEEL_DIAMETER          = 6, // wheel diameter in inches
		/// REDLINE CURVE POINTS
			REDLINE_START = 0,
			REDLINE_MID   = .125,
			REDLINE_END   = 1;
	
	public static final double
			MAST_TIMEOUT = 3000; // timeout for HitTheDick in ms


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
			PIDF_LOOP_ID = 0,
			LEFT_PID = 0,
			PIDF_TIMEOUT = 10000;

	public static final int
		/// RPM'S
			REDLINE = 6000,
			IDLE    = 500;

	
	public static final int
		/// MANIPULATOR
			OPEN_ARMS  = 4,
			CLOSE_ARMS = 5,
		/// CANDY CANE
			RAISE_HOOK = 6,
			LOWER_HOOK = 7;
	
	public static class OOF {
		public static final boolean
	
			LEFT_PINION_MOTOR_INVERT  = false,
			RIGHT_PINION_MOTOR_INVERT = true,
			SCREW_MOTOR_INVERT        = true,
	
			LEFT_FLYWHEEL_MOTOR_INVERT     = true,
			RIGHT_FLYWHEEL_MOTOR_INVERT    = false,

			SCREW_CAM_FLIP = false,
			FRAME_CAM_FLIP = false,

            LEFT_MASTER_INVERT = false,
    		LEFT_SLAVE_INVERT = false,
            RIGHT_MASTER_INVERT = true,
            RIGHT_SLAVE_INVERT = true;
	}
	
	public static class TEUFELSKIND {
		public static final boolean
	
			LEFT_PINION_MOTOR_INVERT  = true,
			RIGHT_PINION_MOTOR_INVERT = false,
			SCREW_MOTOR_INVERT        = false,
	
			LEFT_FLYWHEEL_MOTOR_INVERT     = true,
			RIGHT_FLYWHEEL_MOTOR_INVERT    = false,

			SCREW_CAM_FLIP = false,
			FRAME_CAM_FLIP = false,

			LEFT_MASTER_INVERT = false,
            LEFT_SLAVE_INVERT = false,
            RIGHT_MASTER_INVERT = false,
            RIGHT_SLAVE_INVERT = false;
	}

	public static class VisionConstants {
		
		public static final int CAM_HEIGHT = 1080;
		public static final int CAM_WIDTH  = 1920;

		public static final int SCREW_ID = 0;
		public static final int HOOK_ID = 1;
		public static final int	LEFT_ID = 2;
		public static final int RIGHT_ID = 3;

	}
}
