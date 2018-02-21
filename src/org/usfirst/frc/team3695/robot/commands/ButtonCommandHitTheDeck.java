package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

/**
 * toggles the state of the clamp
 */
public class ButtonCommandHitTheDeck extends Command {
	
	Boolean isFinished;
	
    public ButtonCommandHitTheDeck() {
        requires(Robot.SUB_MAST);
    }
    
    protected void initialize() {
    	Robot.SUB_MAST.setOverride(true);
    	//isFinished = Robot.SUB_MAST.goToMiddle();
    }

    protected void execute() {
    	isFinished = Robot.SUB_MAST.dropIt();
    	if (isFinished) 
			end();
    }

    protected boolean isFinished() { return isFinished; }

    protected void end() {
    	
    }

    protected void interrupted() {
    	end();
    }
}
