package org.usfirst.frc.team3695.robot.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.commands.cyborg.CyborgCommandDriveUntilError;
import org.usfirst.frc.team3695.robot.commands.cyborg.CyborgCommandSpit;
import org.usfirst.frc.team3695.robot.commands.cyborg.CyborgCommandDriveByPath;
import org.usfirst.frc.team3695.robot.enumeration.Goal;
import org.usfirst.frc.team3695.robot.enumeration.Paths;
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
        DriverStation.reportWarning("Generating Left Tank", false);
        
        
//         Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//         		new Waypoint( 1.5, 22.5, 0),
// 				new Waypoint(11.5, 24.0, Pathfinder.d2r(-15)),
// 				new Waypoint(14.0, 21.5, Pathfinder.d2r(90))
//         }, Paths.LEFT_NATIVE_SWITCH.getTank());
	    
	    
//        Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//        		new Waypoint( 1.5, 0, 0),
//				new Waypoint(16.5, 0, 0)
//        }, Paths.LEFT_NATIVE_SWITCH.getTank());
	    
    	Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
			new Waypoint( 1.5, 22.5, 0),
			new Waypoint(11.5, 23.5, Pathfinder.d2r(5)),
			new Waypoint(19.5, 19.0, Pathfinder.d2r(-90)),
			new Waypoint(19.5,  9.5, Pathfinder.d2r(-90)),
			new Waypoint(19.0,  8.0, Pathfinder.d2r(180))
        }, Paths.LEFT_NATIVE_SWITCH.getTank());
    	
    	
        DriverStation.reportWarning("Left Tank Generated", false);
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
                        rightSwitch();
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
    
    private void leftSwitch() {
    	if (gameData.charAt(0) == 'L') {
        	Robot.SUB_DRIVE.gyro.reset();
//        	Robot.SUB_DRIVE.gyro.calibrate();
    		addSequential(new CyborgCommandDriveByPath(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.LEFT_NATIVE_SWITCH)));
    		addSequential(new CyborgCommandDriveUntilError(500, 2, 0.5, 3));
    		addSequential(new CyborgCommandSpit(500));
    	} 
    }
    
    private void rightSwitch() {
    	if (gameData.charAt(0) == 'R') {
//    		addSequential(new CyborgCommandDriveByPath(new Waypoint[] {
//    				new Waypoint( 1.5, 4.5, 0),
//    				new Waypoint(11.5, 3.0, Pathfinder.d2r(15)),
//    				new Waypoint(14.0, 5.5, Pathfinder.d2r(-90))}));
    	} 
    }
    
    
}
