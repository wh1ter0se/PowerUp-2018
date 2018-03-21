package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;

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
    	isFinished = false;
    	startTime = System.currentTimeMillis();
    }

    protected void execute() {
    	isFinished = Robot.SUB_MAST.dropIt(Util.getAndSetDouble("Drop Speed", .5)) ||  (startTime + Constants.MAST_TIMEOUT >= System.currentTimeMillis());
    }

    protected boolean isFinished() { return isFinished; }

    protected void end() {
    }

    protected void interrupted() {}
}
