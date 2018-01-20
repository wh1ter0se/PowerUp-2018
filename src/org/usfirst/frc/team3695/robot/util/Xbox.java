package org.usfirst.frc.team3695.robot.util;

import edu.wpi.first.wpilibj.Joystick;

/** all of the stuff you need to implement X360 controllers */
public class Xbox {
	
	public static final double DEADZONE = 0.25;
	
	public static final int
			A = 1,
			B = 2,
			X = 3,
			Y = 4,
			LB = 5,
			RB = 6,
			BACK = 7,
			START = 8,
			LSTICK = 9,
			RSTICK = 10;

	private static double deadzone(double rawAxis) { // deadzone value is in constants
		boolean positive = rawAxis > 0.0;
		rawAxis *= (positive ? 1.0 : -1.0); //flip if needed
		rawAxis -= DEADZONE; //clip dead zone
		if(rawAxis < 0.0) rawAxis = 0.0; //trim if less than 0
		rawAxis /= (1.0 - DEADZONE); //scale back to 1.0
		rawAxis *= (positive ? 1.0 : -1.0); //flip back
		return rawAxis;
	}
	
	public static double LEFT_X(Joystick joy) {return deadzone(joy.getRawAxis(0));}
	public static double LEFT_Y(Joystick joy) {return deadzone(joy.getRawAxis(1));}
	public static double RIGHT_X(Joystick joy) {return deadzone(joy.getRawAxis(4));}
	public static double RIGHT_Y(Joystick joy) {return deadzone(joy.getRawAxis(5));}
	public static double LT(Joystick joy) {return joy.getRawAxis(2);}
	public static double RT(Joystick joy) {return joy.getRawAxis(3);}
}
