package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;

/**
 * toggles the state of the clamp
 */
public class ButtonCommandHitTheDeck extends Command {

	long startTime;
	
    public ButtonCommandHitTheDeck() {
        requires(Robot.SUB_MAST);
    }
    
    protected void initialize() {
    	startTime = System.currentTimeMillis();
    }

    protected void execute() {
    	Robot.SUB_MAST.dropIt(Util.getAndSetDouble("Drop Speed", .5));
    }

    protected boolean isFinished() {
        if (startTime + Constants.MAST_TIMEOUT > System.currentTimeMillis()) return true;
        return !Robot.SUB_MAST.lowerScrewLimit.get() && !Robot.SUB_MAST.lowerPinionLimit.get();
    }

    protected void end() {}

    protected void interrupted() {}
}
