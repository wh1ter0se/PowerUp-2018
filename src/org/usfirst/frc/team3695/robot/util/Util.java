package org.usfirst.frc.team3695.robot.util;

import edu.wpi.first.wpilibj.Preferences;


/** if our code is colonial America, this class is Rhode Island */
public class Util {
	
	/**
	 *	Use this mainly as a get method to retrieve values the user types into the smart dash
	 *	Use the command within an iterative function such as execute in a command to ensure values get updated
	 *	It is unlikely this will work correctly if not called iteratively
	 *	(the 'Set' part is only in case the value doesn't exist, backup is a default to use and set if it isn't there)
	 */
	private static Preferences pref = Preferences.getInstance();
	public static double getAndSetDouble(String key, double backup) {
		if(!pref.containsKey(key)) pref.putDouble(key, backup);
		return pref.getDouble(key, backup);
	}
}
