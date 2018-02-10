package org.usfirst.frc.team3695.robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive;

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
			REDLINE_END   = 1,
		/// CANDYCANE
			CANDYCANE_SPEED = 1;


	public static final int
		/// DRIVE MOTORS
			RIGHT_MASTER    = 0,
				RIGHT_SLAVE = 6,
			LEFT_MASTER     = 2,
				LEFT_SLAVE  = 7,
		/// MANIPULATOR SPINNY BOIS
			LEFT_FLYWHEEL =  8,
			RIGHT_FLYWHEEL = 3,
		/// MAST MOTORS
			LEFT_PINION_MOTOR  = 4,
			RIGHT_PINION_MOTOR = 5,
			SCREW_MOTOR        = 42,
		/// CANDY CANE
			HOOK = 999,
		/// PID
			RIGHT_PID = 0,
			LEFT_PID = 1,
			TIMEOUT_PID = 10;

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


	/** where all the static final variables for arduino get set */
	public static class ArduinoPatterns {

		public static final int
				SOLID_RED =         1,
				SOLID_BLUE =        2,
				RUNNING_RED =       3,
				RUNNING_BLUE =      4,
				RAINBOW_SLOW =      5,
				RAINBOW_FAST =      6,
				RAINBOW_SONIC =     7, // plz yes
				GOD_BLESS_AMERICA = 8;
	}
}
