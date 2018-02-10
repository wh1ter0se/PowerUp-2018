package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Position;

import edu.wpi.first.wpilibj.command.Command;

/**
 * toggles the state of the clamp
 */
public class CyborgCommandGoToMid extends Command {
	
	Boolean isFinished;
	
    public CyborgCommandGoToMid() {
        requires(Robot.SUB_MAST);
    }

    protected void initialize() {
    	isFinished = true;
    }

    protected void execute() {
    	if (!isFinished) {
    		isFinished = Robot.SUB_MAST.goToMiddle();
    	}
    }

    protected boolean isFinished() { 
    	return isFinished; 
	}

    protected void end() {}

    protected void interrupted() {
    	end();
    }
}
