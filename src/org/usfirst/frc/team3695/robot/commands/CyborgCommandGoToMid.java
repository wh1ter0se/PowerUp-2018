package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Direction;

import edu.wpi.first.wpilibj.command.Command;

/**
 * toggles the state of the clamp
 */
public class CyborgCommandGoToMid extends Command {
	
	Direction direction;
	
    public CyborgCommandGoToMid() {
        requires(Robot.SUB_HOOK);
        requires(Robot.SUB_MAST);
    }

    protected void initialize() {

    }

    protected void execute() {}

    protected boolean isFinished() { return false; }

    protected void end() {

    }

    protected void interrupted() {
    	end();
    }
}
