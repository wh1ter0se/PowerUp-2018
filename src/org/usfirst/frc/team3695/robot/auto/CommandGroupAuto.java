package org.usfirst.frc.team3695.robot.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.commands.CommandWait;
import org.usfirst.frc.team3695.robot.commands.CyborgCommandDriveDistance;
import org.usfirst.frc.team3695.robot.commands.CyborgCommandDriveUntilError;
import org.usfirst.frc.team3695.robot.commands.CyborgCommandGrow;
import org.usfirst.frc.team3695.robot.commands.CyborgCommandRotateDegrees;
import org.usfirst.frc.team3695.robot.commands.CyborgCommandSpit;
import org.usfirst.frc.team3695.robot.enumeration.Goal;
import org.usfirst.frc.team3695.robot.enumeration.Mast;
import org.usfirst.frc.team3695.robot.enumeration.Position;

import static org.usfirst.frc.team3695.robot.Constants.AutonomousConstants;

/**
 * the sequence of commands for autonomous
 */
public class CommandGroupAuto extends CommandGroup {

    //Stores the states of the switches and scale
    String gameData;
    Position position;
    Goal goal;
    Goal thirdPriority;

    public CommandGroupAuto(Position position, Goal goal, Goal thirdPriority) {
        this.position = position;
        this.goal = goal;
        this.thirdPriority = thirdPriority;
        //Get the state of the switches and scale for each round
        gameData = DriverStation.getInstance().getGameSpecificMessage();

        // make sure everything is in the right state/position up here
        Robot.SUB_CLAMP.closeArms();
        Robot.SUB_DRIVE.setInverts();

        // EX: making sure flap is closed before auto starts
        switch (position) {
            case LEFT:
                switch (goal) {
                    case RUN_FOR_IT:
                    	runForIt();
                        break;
                    case SWITCH:
                        leftSwitch();
                        break;
                    case ENEMY_SWITCH:
                        leftEnemySwitch();
                        break;
                    case SCALE:
                        leftScale();
                        break;
                    case BEST_OPTION:
                        robotChoice();
                        break;
                }
                break;

            case CENTER:
                switch (goal) {
                    case RUN_FOR_IT:
                    	runForIt();
                        break;
                    case SWITCH:
                        centerSwitch();
                        break;
                    case ENEMY_SWITCH:
                        break;
                    case SCALE:
                        break;
                    case BEST_OPTION:
                        robotChoice();
                        break;
                }
                break;

            case RIGHT:
                switch (goal) {
                    case RUN_FOR_IT:
                        runForIt();
                        break;
                    case SWITCH:
                        rightSwitch();
                        break;
                    case ENEMY_SWITCH:
                        rightEnemySwitch();
                        break;
                    case SCALE:
                        rightScale();
                        break;
                    case BEST_OPTION:
                        robotChoice();
                        break;
                }
            }
        }
    
    private void runForIt() {
    	addSequential(new CyborgCommandDriveUntilError(500, 2));
    }

    private void leftSwitch() {
        if (gameData.charAt(0) == 'L') { //When the switch is on the left
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_TO_SWITCH, 3500));
            addSequential(new CyborgCommandGrow(Mast.SCREW_UP, 1500));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CW, 1250));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandDriveUntilError(500, 2));
            addSequential(new CyborgCommandSpit(500));
        } else if (gameData.charAt(0) == 'R') { //When the switch is on the right
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_PAST_SWITCH, 4000));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandRotateDegrees(75, 750));
            addSequential(new CommandWait(250));
            addParallel(new CyborgCommandGrow(Mast.SCREW_UP, 1250));
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_TO_FOREIGN_SWITCH, 3500));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CW, 1250));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandDriveUntilError(500, 2));
            addSequential(new CyborgCommandSpit(500));
        }
    }
// TODO right switch terminates early
    private void rightSwitch() {
        if (gameData.charAt(0) == 'R') { //When the switch is on the right
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_TO_SWITCH, 3500));
            addSequential(new CyborgCommandGrow(Mast.SCREW_UP, 1500));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CCW, 1250), 1500);
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandDriveUntilError(500, 2));
            addSequential(new CyborgCommandSpit(500L));
        } else { //When the switch is on the left
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_PAST_SWITCH, 4000));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CCW, 1250));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_TO_FOREIGN_SWITCH, 4000));
            addSequential(new CyborgCommandGrow(Mast.SCREW_UP, 1500));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CCW, 1250));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandDriveUntilError(500, 2));
            addSequential(new CyborgCommandSpit(500));
        }
    }

    private void centerSwitch() {
        addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_PASS_PORTAL, 5000));
        if (gameData.charAt(0) == 'L') { //When the switch is on the left
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CCW, 5000));
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_CENTER_LINE_SWITCH_ALIGN, 5000));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CW, 5000));
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_ALLIANCE_WALL_TO_BLOCKS
                    + AutonomousConstants.DIST_BLOCKS_TO_SWITCH
                    - AutonomousConstants.DIST_PASS_PORTAL, 5000));

        } else if (gameData.charAt(0) == 'L') { //When the switch is on the right
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CW, 5000));
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_CENTER_LINE_SWITCH_ALIGN, 5000));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CCW, 5000));
            //			addParallel(new CyborgCommandGoToMid());
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_ALLIANCE_WALL_TO_BLOCKS
                    + AutonomousConstants.DIST_BLOCKS_TO_SWITCH
                    - AutonomousConstants.DIST_PASS_PORTAL, 5000));
        } else {
        	addSequential(new CyborgCommandDriveUntilError(500, 2));
        }
    }

    private void leftScale() {
        if (gameData.charAt(1) == 'L') { //When scale is on the left
        	addParallel(new CyborgCommandGrow(Mast.SCREW_UP, 3000));
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_TO_SCALE, 4000));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandGrow(Mast.PINION_UP, 3500));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CW, 1250));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandDriveUntilError(500, 2));
            addSequential(new CyborgCommandSpit(500));
        } else { //When scale is on the right
        	addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_PAST_SWITCH, 4000));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CW, 1250));
            addSequential(new CommandWait(250));
            addParallel(new CyborgCommandGrow(Mast.SCREW_UP, 1500));
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_PAST_SCALE, 4000));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CCW, 1250));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_SCALE_LINEUP, 4000));
            addSequential(new CyborgCommandGrow(Mast.PINION_UP, 3500));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CCW, 1250));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandDriveUntilError(500, 2));
            addSequential(new CyborgCommandSpit(500));
        }
    }

    private void rightScale() {
        if (gameData.charAt(1) == 'R') { //When scale is on the right
        	addParallel(new CyborgCommandGrow(Mast.SCREW_UP, 3000));
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_TO_SCALE, 4000));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandGrow(Mast.PINION_UP, 3500));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CCW, 1250));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandDriveUntilError(500, 2));
            addSequential(new CyborgCommandSpit(500));
        } else { //When scale is on the left
        	addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_PAST_SWITCH, 4000));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CCW, 1250));
            addSequential(new CommandWait(250));
            addParallel(new CyborgCommandGrow(Mast.SCREW_UP, 1500));
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_PAST_SCALE, 4000));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CW, 1250));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_SCALE_LINEUP, 4000));
            addSequential(new CyborgCommandGrow(Mast.PINION_UP, 3500));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CW, 1250));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandDriveUntilError(500, 2));
            addSequential(new CyborgCommandSpit(500));
        }
    }

    private void leftEnemySwitch() {
        if (gameData.charAt(2) == 'L') { //When switch is on the left
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_TO_ENEMY_SWITCH, 9000));
            addSequential(new CyborgCommandGrow(Mast.SCREW_UP, 3000));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CW, 1250));
            addSequential(new CyborgCommandSpit(500));
        } else { //When switch is on the right

        }
    }

    private void rightEnemySwitch() {
        if (gameData.charAt(2) == 'R') { //When the enemy switch is on the left
            addSequential(new CyborgCommandDriveDistance(AutonomousConstants.DIST_TO_ENEMY_SWITCH, 9000));
            addSequential(new CyborgCommandGrow(Mast.SCREW_UP, 3000));
            addSequential(new CommandWait(250));
            addSequential(new CyborgCommandRotateDegrees(AutonomousConstants.ROT_90_CCW, 1250));
            addSequential(new CyborgCommandSpit(500));
        } else {//When the enemy switch is on the right

        }
    }

    private void robotChoice() {
        switch (position){
            case LEFT:
                if (gameData.charAt(0) == 'L') {
                    leftSwitch();
                } else if (gameData.charAt(1) == 'L') {
                    leftScale();
                } else {
                	switch (thirdPriority) {
                		case SWITCH:
                			leftSwitch();
                			break;
                		case SCALE:
                			leftScale();
                			break;
                		case ENEMY_SWITCH:
                			leftEnemySwitch();
                			break;
                	}
                }
                break;
            case CENTER:
                centerSwitch();
                break;
            case RIGHT:
            	if (gameData.charAt(0) == 'R') {
                    rightSwitch();
                } else if (gameData.charAt(1) == 'R') {
                    rightScale();
                } else {
                	switch (thirdPriority) {
	            		case SWITCH:
	            			rightSwitch();
	            			break;
	            		case SCALE:
	            			rightScale();
	            			break;
	            		case ENEMY_SWITCH:
	            			rightEnemySwitch();
	            			break;
                	}
                }
                break;
        }
    }
}