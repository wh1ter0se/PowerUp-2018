package org.usfirst.frc.team3695.robot.enumeration;

public enum Goal {
	NOTHING("Do nothing"),
	RUN_FOR_IT("Run for it"),
	SWITCH_LEFT("Switch - Left Side"),
	SWITCH_RIGHT("Switch - Right Side"),
	SCALE_LEFT("Scale - Left"),
	SCALE_RIGHT("Scale - Right");

	private final String name;
	Goal(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}
