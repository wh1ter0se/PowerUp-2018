package org.usfirst.frc.team3695.robot.util;

import org.usfirst.frc.team3695.robot.enumeration.Direction;

import edu.wpi.first.wpilibj.DriverStation;

public class Field {
	public static Direction getOurScale() {
		if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			return Direction.LEFT;
		} else {
			return Direction.RIGHT;
		}
	}
	
	public static Direction getThierScale() {
		if (DriverStation.getInstance().getGameSpecificMessage().charAt(2) == 'L') {
			return Direction.LEFT;
		} else {
			return Direction.RIGHT;
		}
	}
	
	public static Direction getSwitch() {
		if (DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L') {
			return Direction.LEFT;
		} else {
			return Direction.RIGHT;
		}
	}
}
