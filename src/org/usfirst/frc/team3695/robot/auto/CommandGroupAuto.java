package org.usfirst.frc.team3695.robot.auto;

import edu.wpi.first.wpilibj.DriverStation;
import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.commands.*; // when the commands are ready, load each individually to decrease runtime
import org.usfirst.frc.team3695.robot.enumeration.Goal;
import org.usfirst.frc.team3695.robot.enumeration.Position;

import edu.wpi.first.wpilibj.command.CommandGroup;

/** the sequence of commands for autonomous */
public class CommandGroupAuto extends CommandGroup {

	//Stores the states of the switches and scale
	String gameData;

	public CommandGroupAuto(Position position, Goal goal) {
		//Get the state of the switches and scale for each round
		gameData = DriverStation.getInstance().getGameSpecificMessage();

		// make sure everything is in the right state/position up here
		Robot.SUB_CLAMP.closeArms();
		// EX: making sure flap is closed before auto starts
		switch (position) {
			case LEFT:
				switch (goal){
					case RUN_FOR_IT:
						addSequential(new CyborgCommandDriveUntilError(Position.FORWARD));
						break;
					case SWITCH:
						if (gameData.charAt(0) == 'L'){ //When the switch is on the left
							addParallel(new CyborgCommandDriveDistance(Constants.Autonomous.DIST_TO_SWITCH_FROM_SIDE));
							addSequential(new CyborgCommandGoToMid());
							addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_CLOCKWISE));
						} else { //When the switch is on the right

						}
						break;
					case ENEMY_SWITCH:
						addSequential(new CyborgCommandDriveDistance(Constants.Autonomous.DIST_WALL_TO_ENEMY_BLOCKS));
						addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_CLOCKWISE));
						addSequential(new CyborgCommandDriveUntilError(Position.FORWARD));
						
//						(in parallel) Set mast to proper grabbing height 
//						Code for grabbing block
						
//						Needs distance from enemy switch blocks to robot (going backwards (maybe record the distance using a variable?))
//						addSequential(new CyborgCommandDriveDistance(-1*Constants.Autonomous.))
						addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_COUNTERCLOCKWISE));
						addSequential(new CyborgCommandDriveDistance(Constants.Autonomous.DIST_BLOCK_TO_MIDDLE_OF_SWITCH));
						addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_CLOCKWISE));
						addParallel(new CyborgCommandGoToMid());
						addSequential(new CyborgCommandDriveUntilError(Position.FORWARD));
						
//						Code for dropping block
						break;
					case SCALE:
						addSequential(new CyborgCommandDriveDistance(Constants.Autonomous.DIST_WALL_TO_SWITCH_BLOCKS));
						addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_CLOCKWISE));
						addSequential(new CyborgCommandDriveUntilError(Position.FORWARD));
						
						
//						 (in parallel) Set mast to proper grabbing height 
//						Code for grabbing block
						
//						Needs distance from enemy switch blocks to robot (going backwards (maybe record the distance using a variable?))
//						addSequential(new CyborgCommandDriveDistance(-1*Constants.Autonomous.))
						addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_COUNTERCLOCKWISE));
						addSequential(new CyborgCommandDriveDistance(Constants.Autonomous.DIST_SWITCH_BLOCK_TO_SCALE));
						addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_CLOCKWISE));
//						Don't run into scale plz
						
//						(in parallel) Set mast height to max
//						Spit cube out
						break;
					case BEST_OPTION:
						break;
				}
				break;

			case CENTER:
				switch (goal){
					case RUN_FOR_IT:
						addSequential(new CyborgCommandDriveUntilError(Position.FORWARD));
						break;
					case SWITCH:
						//Raise the mast to midpoint and pass the portal to avoid collisions
						addParallel(new CyborgCommandGoToMid());
						addSequential(new CyborgCommandDriveDistance(Constants.Autonomous.DIST_PASS_PORTAL));
						if (gameData.charAt(0) == 'L'){ //When the switch is on the left
							addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_COUNTERCLOCKWISE));
							addSequential(new CyborgCommandDriveDistance(Constants.Autonomous.DIST_CENTER_LINE_SWITCH_ALIGN));
							addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_CLOCKWISE));
							addSequential(new CyborgCommandDriveDistance(Constants.Autonomous.DIST_WALL_TO_BLOCKS
																				+ Constants.Autonomous.DIST_BLOCKS_TO_SWITCH
																				- Constants.Autonomous.DIST_PASS_PORTAL));

						} else { //When the switch is on the right
							addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_CLOCKWISE));
							addSequential(new CyborgCommandDriveDistance(Constants.Autonomous.DIST_CENTER_LINE_SWITCH_ALIGN));
							addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_COUNTERCLOCKWISE));
							addSequential(new CyborgCommandDriveDistance(Constants.Autonomous.DIST_WALL_TO_BLOCKS
																				+ Constants.Autonomous.DIST_BLOCKS_TO_SWITCH
																				- Constants.Autonomous.DIST_PASS_PORTAL));
						}
						break;
					case ENEMY_SWITCH:
						break;
					case SCALE:
						break;
					case BEST_OPTION:
						break;
			}
				break;
				
			case RIGHT:
				switch (goal) {
					case RUN_FOR_IT:
						addSequential(new CyborgCommandDriveUntilError(Position.FORWARD));
						break;
					case SWITCH:
						if (gameData.charAt(0) == 'R'){ //When the switch is on the right
							addParallel(new CyborgCommandDriveDistance(Constants.Autonomous.DIST_TO_SWITCH_FROM_SIDE));
							addSequential(new CyborgCommandGoToMid());
							addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_COUNTERCLOCKWISE));
						} else { //When the switch is on the left

						}
						break;
					case ENEMY_SWITCH:
						addSequential(new CyborgCommandDriveDistance(Constants.Autonomous.DIST_WALL_TO_ENEMY_BLOCKS));
						addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_COUNTERCLOCKWISE));
						addSequential(new CyborgCommandDriveUntilError(Position.FORWARD));
						
//						Code for grabbing block
						
//						Needs distance from enemy switch blocks to robot (going backwards (maybe record the distance using a variable?))
//						addSequential(new CyborgCommandDriveDistance(-1*Constants.Autonomous.))
						addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_CLOCKWISE));
						addSequential(new CyborgCommandDriveDistance(Constants.Autonomous.DIST_BLOCK_TO_MIDDLE_OF_SWITCH));
						addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_COUNTERCLOCKWISE));
						addParallel(new CyborgCommandGoToMid());
						addSequential(new CyborgCommandDriveUntilError(Position.FORWARD));
						
//						Code for dropping block
						break;
					case SCALE:
						addSequential(new CyborgCommandDriveDistance(Constants.Autonomous.DIST_WALL_TO_SWITCH_BLOCKS));
						addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_COUNTERCLOCKWISE));
						addSequential(new CyborgCommandDriveUntilError(Position.FORWARD));
						
						
//						 (in parallel) Set mast to proper grabbing height 
//						Code for grabbing block
						
//						Needs distance from enemy switch blocks to robot (going backwards (maybe record the distance using a variable?))
//						addSequential(new CyborgCommandDriveDistance(-1*Constants.Autonomous.))
						addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_CLOCKWISE));
						addSequential(new CyborgCommandDriveDistance(Constants.Autonomous.DIST_SWITCH_BLOCK_TO_SCALE));
						addSequential(new CyborgCommandRotateDegrees(Constants.Autonomous.ROT_90_COUNTERCLOCKWISE));
//						Don't run into scale plz
						
//						(in parallel) Set mast height to max
//						Spit cube out
						break;
					case BEST_OPTION:
						break;
				}
			break;
		}	
	}
}
