package org.usfirst.frc.team3695.robot.enumeration;

/** the possible autonomous modes */
public enum Autonomous {
	NOTHING,             // do nothing
	RUN_FOR_IT,          // RUN FOREST RUN (just go straight til telopInit)
	LEFT_THEN_RUN,       // move left from the center, then turn and run
	RIGHT_THEN_RUN,      // move right from the center, then turn and run
	RUN_AND_PLACE_LEFT,  // run forward, then place the cube on the scale to the left
	RUN_AND_PLACE_RIGHT; // run forward, then place the cube on the scale to the right
}
