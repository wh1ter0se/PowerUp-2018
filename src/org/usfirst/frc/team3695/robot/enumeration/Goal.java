package org.usfirst.frc.team3695.robot.enumeration;

public enum Goal {
	NOTHING("Do nothing"),
	RUN_FOR_IT("Run for it"),
	SWITCH("Switch"),
	ENEMY_SWITCH("Enemy Switch"),
	SCALE("Scale"),
	BEST_OPTION("Robot Choice");

	private final String name;
	Goal(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}
