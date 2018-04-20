package org.usfirst.frc.team3695.robot.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.commands.CommandWait;
import org.usfirst.frc.team3695.robot.commands.cyborg.*;
import org.usfirst.frc.team3695.robot.enumeration.Goal;
import org.usfirst.frc.team3695.robot.enumeration.Mast;
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
    public CommandGroupAuto(Position position, Goal goal, Goal thirdPriority) {
        this.position = position;
        //Get the state of the switches and scale for each round
        gameData = DriverStation.getInstance().getGameSpecificMessage();

        Robot.SUB_CLAMP.closeArms();
        Robot.SUB_DRIVE.gyro.reset();
        Robot.SUB_DRIVE.gyro.getAngle();
        DriverStation.reportWarning("Generating Paths", false);
//         Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//         		new Waypoint( 1.5, 23.25, 0),
// 				new Waypoint(11.5, 23.25, Pathfinder.d2r(-15)),
// 				new Waypoint(14.0, 20.5, Pathfinder.d2r(-90))
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
//	  			new Waypoint( 1.5, 3.75, 0),
//	  			new Waypoint(11.5, 3.75, Pathfinder.d2r(15)),
//	  			new Waypoint(14.0, 6.5, Pathfinder.d2r(90))
//	          }, Paths.RIGHT_NATIVE_SWITCH.getFileName());
//     
//        Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//    			new Waypoint( 1.5, 13.0,  0),
//    			new Waypoint( 7.0, 16.0,  Pathfinder.d2r(45)),
//    			new Waypoint(11.5, 18.25, 0),
//            }, Paths.CENTER_SWITCH_LEFT.getFileName());
//        
//        Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//    			new Waypoint( 1.5, 13.0, 0),
//    			new Waypoint( 6.0, 11.0, Pathfinder.d2r(-45)),
//    			new Waypoint(11.0,  8.85, 0),
//            }, Paths.CENTER_SWITCH_RIGHT.getFileName());
//        
//        Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//    			new Waypoint( 1.5, 23.25, 0),
//    			new Waypoint(17.5, 24.0, 0),
//    			new Waypoint(23.8, 21.75, Pathfinder.d2r(-45))
//            }, Paths.LEFT_NATIVE_SCALE.getFileName());
//        
//        Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//	  			new Waypoint( 1.5, 3.5, 0),
//	  			new Waypoint(17.0, 3.0, 0),
//	  			new Waypoint(22.8, 5.0, Pathfinder.d2r(20))
//	          }, Paths.RIGHT_NATIVE_SCALE.getFileName());
//
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
//    	
//    	Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//    		new Waypoint( 0.0,  0.0, 0),
//			new Waypoint( 2.0,  2.0, Pathfinder.d2r(115)),
//      	}, Paths.RIGHT_NATIVE_SCALE_ROT.getFileName());

        // THIS TWO CUBE
//    	Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[] {
//        		new Waypoint(  1.5, 23.0, 0),
//    			new Waypoint(16.25, 25.0, 0),
//    			new Waypoint(18.75, 22.0, Pathfinder.d2r(100)),
//    			new Waypoint(17.85, 20.1, Pathfinder.d2r(-135))
//         }, Paths.LEFT_NATIVE_SWITCH.getFileName());

        Robot.SUB_DRIVE.autoDrive.generateAndSaveTrajectory(new Waypoint[]{
                new Waypoint(22.8, 23.0, Pathfinder.d2r(-10)),
                new Waypoint(28, 23.0, 0)
        }, Paths.LEFT_NATIVE_SCALE_ROT.getFileName());

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
            addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.LEFT_NATIVE_SWITCH), 0));
            addSequential(new CyborgCommandSpit(500));

            // dual auto
            addParallel(new CyborgCommandEat(true, 1000));
            addSequential(new CyborgCommandDriveByTimer(-.25, 250));
            addSequential(new CyborgCommandGrow(Mast.SCREW_DOWN, 4000));
            addSequential(new CyborgCommandDriveByTimer(.25, 500));
            addSequential(new CyborgCommandEat(false, 1000));
            addSequential(new CyborgCommandGrow(Mast.SCREW_UP, 4000));
            addSequential(new CyborgCommandSpit(500));
        } else {
            addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.LEFT_SETUP), 0));
        }
    }

    private void rightSwitch() {
        if (gameData.charAt(0) == 'R') {
            addParallel(new CyborgCommandGrow(Mast.SCREW_UP, 3000));
            addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.RIGHT_NATIVE_SWITCH), 0));
            addSequential(new CyborgCommandSpit(500));
        } else {
            addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.RIGHT_SETUP), 0));
        }
    }

    private void centerSwitch() {
        if (gameData.charAt(0) == 'L') {
            addParallel(new CyborgCommandGrow(Mast.SCREW_UP, 3000));
            addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.CENTER_SWITCH_LEFT), 0));
            addSequential(new CyborgCommandSpit(500));
        } else {
            addParallel(new CyborgCommandGrow(Mast.SCREW_UP, 3000));
            addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.CENTER_SWITCH_RIGHT), 0));
            addSequential(new CyborgCommandSpit(500));
        }
    }

    private void leftScale() {
        if (gameData.charAt(1) == 'L') {
            addParallel(new CyborgCommandGrow(Mast.SCREW_UP, 3000));
            addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.LEFT_NATIVE_SCALE), 0));
            addSequential(new CyborgCommandGrow(Mast.PINION_UP, 5000));
            addSequential(new CommandWait(300));
            addSequential(new CyborgCommandSpit(500));
            addSequential(new CommandWait(100));
            addSequential(new CyborgCommandGrow(Mast.PINION_DOWN, 5000));
            addSequential(new CyborgCommandGrow(Mast.SCREW_DOWN, 5000));
            addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.LEFT_NATIVE_SCALE_ROT), -10));
        } else {
            addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.LEFT_SETUP), 0));
        }
    }

    private void rightScale() {
        if (gameData.charAt(1) == 'R') {
            addParallel(new CyborgCommandGrow(Mast.SCREW_UP, 3000));
            addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.RIGHT_NATIVE_SCALE), 0));
            addSequential(new CyborgCommandGrow(Mast.PINION_UP, 5000));
            addSequential(new CommandWait(300));
            addSequential(new CyborgCommandSpit(500));
            addSequential(new CommandWait(100));
            addSequential(new CyborgCommandGrow(Mast.PINION_DOWN, 5000));
            addSequential(new CyborgCommandGrow(Mast.SCREW_DOWN, 5000));
//			addSequential(new CyborgCommandDriveByPath(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.RIGHT_NATIVE_SCALE_ROT)));
        } else {
            addSequential(new CyborgCommandDriveByPathWithGyro(Robot.SUB_DRIVE.autoDrive.getSavedTrajectory(Paths.RIGHT_SETUP), 0));
        }
    }

    private void robotChoice() {
        switch (position) {
            case LEFT:
                if (gameData.charAt(0) == 'L' || (gameData.charAt(0) == 'R' && gameData.charAt(1) == 'R')) {
                    leftSwitch();
                } else {
                    leftScale();
                }
                break;
            case RIGHT:
                if (gameData.charAt(0) == 'R' || (gameData.charAt(0) == 'L' && gameData.charAt(1) == 'L')) {
                    rightSwitch();
                } else {
                    rightScale();
                }
                break;
            case CENTER:
                centerSwitch();
                break;
        }
    }

}
