package org.usfirst.frc.team3695.robot.enumeration;

/** the possible autonomous modes */
public enum Autonomous {
	NOTHING("Do Nothing"),
	RUN_FOR_IT("Run straight forward"),
	LEFT_THEN_RUN("Move left from the center, then turn and run"),
	RIGHT_THEN_RUN("Move right from the center, then turn and run"),
	RUN_AND_PLACE_LEFT("Run forward, then place the cube on the scale to the left"),
	RUN_AND_PLACE_RIGHT("un forward, then place the cube on the scale to the right");
	
	private final String name;
	Autonomous(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}

