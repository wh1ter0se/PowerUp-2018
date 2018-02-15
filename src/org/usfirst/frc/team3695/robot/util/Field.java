package org.usfirst.frc.team3695.robot.util;

import org.usfirst.frc.team3695.robot.enumeration.Position;

import edu.wpi.first.wpilibj.DriverStation;

public class Field {
	public static Position getOurScale() {
		if (DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			return Position.LEFT;
		} else {
			return Position.RIGHT;
		}
	}
	
	public static Position getThierScale() {
		if (DriverStation.getInstance().getGameSpecificMessage().charAt(2) == 'L') {
			return Position.LEFT;
		} else {
			return Position.RIGHT;
		}
	}
	
	public static Position getSwitch() {
		if (DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L') {
			return Position.LEFT;
		} else {
			return Position.RIGHT;
		}
	}
}
