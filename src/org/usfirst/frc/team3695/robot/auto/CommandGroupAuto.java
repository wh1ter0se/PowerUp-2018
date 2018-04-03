package org.usfirst.frc.team3695.robot.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.commands.CommandWait;
import org.usfirst.frc.team3695.robot.commands.cyborg.CyborgCommandDriveByPathWithGyro;
import org.usfirst.frc.team3695.robot.commands.cyborg.CyborgCommandGrow;
import org.usfirst.frc.team3695.robot.commands.cyborg.CyborgCommandSpit;
import org.usfirst.frc.team3695.robot.enumeration.Goal;
import org.usfirst.frc.team3695.robot.enumeration.Mast;
import org.usfirst.frc.team3695.robot.enumeration.Paths;
import org.usfirst.frc.team3695.robot.enumeration.Position;

//import org.usfirst.frc.team3695.robot.commands.cyborg.CyborgCommandDriveUntilError;

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
        Robot.SUB_DRIVE.gyro.reset();
    	Robot.SUB_DRIVE.gyro.getAngle();
//        DriverStation.reportWarning("Generating Left Tank", false);
//         Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//         		new Waypoint( 1.5, 22.5, 0),
// 				new Waypoint(11.5, 24.0, Pathfinder.d2r(-15)),
// 				new Waypoint(14.0, 21.5, Pathfinder.d2r(-90))
//         }, Paths.LEFT_NATIVE_SWITCH.getFileName());
//    	
//    	Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//         		new Waypoint( 1.5, 22.5, 0),
// 				new Waypoint(17.0, 24.0, 0),
// 				new Waypoint(20.0, 20.0, Pathfinder.d2r(-90)),
// 				new Waypoint(20.0, 10.0, Pathfinder.d2r(-90)),
// 				new Waypoint(17.5,  8.0, Pathfinder.d2r(180))
//         }, Paths.LEFT_FOREIGN_SWITCH.getFileName());
//        
//        Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//	  			new Waypoint( 1.5, 4.5, 0),
//	  			new Waypoint(11.5, 3.0, Pathfinder.d2r(15)),
//	  			new Waypoint(14.0, 6.5, Pathfinder.d2r(90))
//	          }, Paths.RIGHT_NATIVE_SWITCH.getFileName());
////        
//        Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//    			new Waypoint( 1.5, 13.5, 0),
//    			new Waypoint( 6.0, 15.0, Pathfinder.d2r(45)),
//    			new Waypoint(11.5, 18.5, 0),
//            }, Paths.CENTER_SWITCH_LEFT.getFileName());
//        
//        Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//    			new Waypoint( 1.5, 13.5, 0),
//    			new Waypoint( 6.0, 12.0, Pathfinder.d2r(-45)),
//    			new Waypoint(11.5,  10.0, 0),
//            }, Paths.CENTER_SWITCH_RIGHT.getFileName());
//        
//        Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//    			new Waypoint( 1.5, 22.5, 0),
//    			new Waypoint(17.0, 23.0, 0),
//    			new Waypoint(22.8, 23.0, Pathfinder.d2r(-10))
//            }, Paths.LEFT_NATIVE_SCALE.getFileName());
//        
//        Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//	  			new Waypoint( 1.5, 4.5, 0),
//	  			new Waypoint(17.0, 4.0, 0),
//	  			new Waypoint(22.8, 4.0, Pathfinder.d2r(10))
//	          }, Paths.RIGHT_NATIVE_SCALE.getFileName());
////        
//        Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//	  			new Waypoint( 1.5, 22.5, 0),
//	  			new Waypoint(14.0, 24.0, 0),
//	  			new Waypoint(19.5, 20.0, Pathfinder.d2r(-90))
//	          }, Paths.LEFT_SETUP.getFileName());
//        
//        Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//	  			new Waypoint( 1.5, 4.5, 0),
//	  			new Waypoint(14.0, 3.0, 0),
//	  			new Waypoint(19.5, 8.0, Pathfinder.d2r(90))
//	          }, Paths.RIGHT_SETUP.getFileName());
//    	
//    	Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//    		new Waypoint(22.8, 23.0, Pathfinder.d2r(-10)),
//			new Waypoint(19.5, 24.5, Pathfinder.d2r(-90)),
//			new Waypoint(19.5, 21.0, Pathfinder.d2r(-90)),
//			new Waypoint(18.0, 19.5, Pathfinder.d2r(180)),
//      	}, Paths.LEFT_NATIVE_SCALE_ROT.getFileName());
    	
//    	Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//    		new Waypoint( 0.0,  0.0, 0),
//			new Waypoint( 2.0,  2.0, Pathfinder.d2r(115)),
//      	}, Paths.RIGHT_NATIVE_SCALE_ROT.getFileName());
        
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
                        break;
                    case SWITCH:
                        rightSwitch();
                        break;
                    case ENEMY_SWITCH:
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
    
    private void leftSwitch() {
    	if (gameData.charAt(0) == 'L') {
    		addParallel(new CyborgCommandGrow(Mast.SCREW_UP, 3000));
			addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.LEFT_NATIVE_SWITCH)));
//            addSequential(new CyborgCommandDriveUntilError(500, 2, 0.5, 3));
			addSequential(new CyborgCommandSpit(500));
    	} else {
    		addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.LEFT_SETUP)));
    	}
    }
    
    private void rightSwitch() {
    	if (gameData.charAt(0) == 'R') {
    		addParallel(new CyborgCommandGrow(Mast.SCREW_UP, 3000));
			addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.RIGHT_NATIVE_SWITCH)));
//            addSequential(new CyborgCommandDriveUntilError(500, 2, 0.5, 3));
			addSequential(new CyborgCommandSpit(500));
    	} else {
    		addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.RIGHT_SETUP)));
    	}
    }
    
    private void centerSwitch() {
    	if (gameData.charAt(0) == 'L') {
    		addParallel(new CyborgCommandGrow(Mast.SCREW_UP, 3000));
			addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.CENTER_SWITCH_LEFT)));
//            addSequential(new CyborgCommandDriveUntilError(500, 2, 0.5, 3));
			addSequential(new CyborgCommandSpit(500));
    	} else {
    		addParallel(new CyborgCommandGrow(Mast.SCREW_UP, 3000));
			addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.CENTER_SWITCH_RIGHT)));
//            addSequential(new CyborgCommandDriveUntilError(500, 2, 0.5, 3));
			addSequential(new CyborgCommandSpit(500));
    	}
    }
    
    private void leftScale() {
    	if (gameData.charAt(1) == 'L') {
	    	addParallel(new CyborgCommandGrow(Mast.SCREW_UP, 3000));
			addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.LEFT_NATIVE_SCALE)));
			addSequential(new CyborgCommandGrow(Mast.PINION_UP, 5000));
			addSequential(new CommandWait(300));
			addSequential(new CyborgCommandSpit(500));
			addSequential(new CommandWait(100));
			addSequential(new CyborgCommandGrow(Mast.PINION_DOWN,5000));
			addSequential(new CyborgCommandGrow(Mast.SCREW_DOWN, 5000));
//			addSequential(new CyborgCommandDriveByPath(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.LEFT_NATIVE_SCALE_ROT)));
    	} else {
    		addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.LEFT_SETUP)));
    	}
    }
    
    private void rightScale() {
    	if (gameData.charAt(1) == 'R') {
	    	addParallel(new CyborgCommandGrow(Mast.SCREW_UP, 3000));
			addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.RIGHT_NATIVE_SCALE)));
			addSequential(new CyborgCommandGrow(Mast.PINION_UP, 5000));
			addSequential(new CommandWait(300));
			addSequential(new CyborgCommandSpit(500));
			addSequential(new CommandWait(100));
			addSequential(new CyborgCommandGrow(Mast.PINION_DOWN,5000));
			addSequential(new CyborgCommandGrow(Mast.SCREW_DOWN, 5000));
//			addSequential(new CyborgCommandDriveByPath(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.RIGHT_NATIVE_SCALE_ROT)));
    	} else {
    		addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.RIGHT_SETUP)));
    	}
    }
    
    private void robotChoice() {
    	
    }
    
}
