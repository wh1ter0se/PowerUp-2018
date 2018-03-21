package org.usfirst.frc.team3695.robot.commands.button;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;

/**
 * Forcefully brings the pinion and screw all the way down in the case of the bot being close to tipping
 */
public class ButtonCommandHitTheDeck extends Command {

    private long startTime;
	
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
        return startTime + Constants.MAST_TIMEOUT > System.currentTimeMillis()
                || !Robot.SUB_MAST.lowerScrewLimit.get() && !Robot.SUB_MAST.lowerPinionLimit.get();
    }

    protected void end() {}

    protected void interrupted() {}
}
