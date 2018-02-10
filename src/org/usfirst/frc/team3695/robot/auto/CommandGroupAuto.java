package org.usfirst.frc.team3695.robot.auto;

import org.usfirst.frc.team3695.robot.commands.*; // when the commands are ready, load each individually to decrease runtime
import org.usfirst.frc.team3695.robot.enumeration.Autonomous;
import org.usfirst.frc.team3695.robot.enumeration.Goal;
import org.usfirst.frc.team3695.robot.enumeration.Position;

import edu.wpi.first.wpilibj.command.CommandGroup;

/** the sequence of commands for autonomous */
public class CommandGroupAuto extends CommandGroup {
		
	public CommandGroupAuto(Position position, Goal goal) {
		// make sure everything is in the right state/position up here
		// EX: making sure flap is closed before auto starts
		
		switch (position) {
			case LEFT:
				switch (goal){
					case RUN_FOR_IT:
						break;
					case SWITCH_LEFT:
						break;
					case SWITCH_RIGHT:
						break;
					case SCALE_LEFT:
						break;
					case SCALE_RIGHT:
						break;
				}
				break;
				
			case CENTER:
				switch (goal){
					case RUN_FOR_IT:
						break;
					case SWITCH_LEFT:
						break;
					case SWITCH_RIGHT:
						break;
					case SCALE_LEFT:
						break;
					case SCALE_RIGHT:
						break;
			}
				break;
				
			case RIGHT:
				switch (goal){
					case RUN_FOR_IT:
						break;
					case SWITCH_LEFT:
						break;
					case SWITCH_RIGHT:
						break;
					case SCALE_LEFT:
						break;
					case SCALE_RIGHT:
						break;
				}
				
			break;
		}	
	}
}
