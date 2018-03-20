package org.usfirst.frc.team3695.robot.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.commands.cyborg.CyborgCommandPathfinder;
import org.usfirst.frc.team3695.robot.enumeration.Goal;
import org.usfirst.frc.team3695.robot.enumeration.Position;

/**
 * the sequence of commands for autonomous
 */
@SuppressWarnings("FieldCanBeLocal") //Only necessary until we reimplement robot choice and other options
public class CommandGroupAuto extends CommandGroup {

    //Stores the states of the switches and scale
    private String gameData;
    private Position position;
    private Goal goal;
    private Goal thirdPriority;

    public CommandGroupAuto(Position position, Goal goal, Goal thirdPriority) {
        this.position = position;
        this.goal = goal;
        this.thirdPriority = thirdPriority;
        //Get the state of the switches and scale for each round
        gameData = DriverStation.getInstance().getGameSpecificMessage();

        Robot.SUB_CLAMP.closeArms();
        Robot.SUB_DRIVE.setInverts();

        // EX: making sure flap is closed before auto starts
        switch (position) {
            case LEFT:
                switch (goal) {
                    case RUN_FOR_IT:
                        break;
                    case SWITCH:
                    	leftSwitch();
                        break;
                    case ENEMY_SWITCH:
                        break;
                    case SCALE:
                        break;
                    case BEST_OPTION:
                        break;
                }
                break;

            case CENTER:
                switch (goal) {
                    case RUN_FOR_IT:
                        break;
                    case SWITCH:
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
                        break;
                    case SWITCH:
                        break;
                    case ENEMY_SWITCH:
                        break;
                    case SCALE:
                        break;
                    case BEST_OPTION:
                        break;
                }
            }
        }
    
    public void leftSwitch() {
    	if (gameData.charAt(0) == 'L') {
    		addSequential(new CyborgCommandPathfinder(new Waypoint[] {
    				new Waypoint( 1.5, 22.5, 0),
    				new Waypoint(11.5, 24.0, Pathfinder.d2r(-15)),
    				new Waypoint(14.0, 21.5, Pathfinder.d2r(90))}));
    	} 
    }
    
    public void rightSwitch() {
    	if (gameData.charAt(0) == 'L') {
    		addSequential(new CyborgCommandPathfinder(new Waypoint[] {
    				new Waypoint( 1.5, 4.5, 0),
    				new Waypoint(11.5, 3.0, Pathfinder.d2r(15)),
    				new Waypoint(14.0, 5.5, Pathfinder.d2r(-90))}));
    	} 
    }
    
    
}