package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;

import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Position;

import edu.wpi.first.wpilibj.command.Command;

/**
 * toggles the state of the clamp
 */
public class CyborgCommandHitTheDeck extends Command {
	
	Boolean isFinished;
	
    public CyborgCommandHitTheDeck() {
        requires(Robot.SUB_MAST);
    }
    
    protected void initialize() {
    	Robot.SUB_MAST.override = true;
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
