package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Direction;

import edu.wpi.first.wpilibj.command.Command;

/**
 * toggles the state of the clamp
 */
public class CyborgCommandAscend extends Command {
	
	Boolean isFinished;
	
    public CyborgCommandAscend() {
        requires(Robot.SUB_MAST);
    }

    protected void initialize() {
    	isFinished = Robot.SUB_MAST.goToMiddle();
    }

    protected void execute() {}

    protected boolean isFinished() { return isFinished; }

    protected void end() {

    }

    protected void interrupted() {
    	end();
    }
}
