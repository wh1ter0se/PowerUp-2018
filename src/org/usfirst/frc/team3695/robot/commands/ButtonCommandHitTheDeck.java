package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;

/**
 * toggles the state of the clamp
 */
public class ButtonCommandHitTheDeck extends Command {
	
	Boolean isFinished;
	
	long startTime;
	
    public ButtonCommandHitTheDeck() {
        requires(Robot.SUB_MAST);
    }
    
    protected void initialize() {
    	startTime = System.currentTimeMillis();
    	Robot.SUB_MAST.setOverride(true);
    }

    protected void execute() {
    	isFinished = Robot.SUB_MAST.dropIt(1) ||  (startTime + Constants.MAST_TIMEOUT >= System.currentTimeMillis());
    }

    protected boolean isFinished() { return isFinished; }

    protected void end() {
    	Robot.SUB_MAST.setOverride(false);
    	isFinished = false;
    }

    protected void interrupted() {}
}
