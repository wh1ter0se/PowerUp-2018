package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Direction;
import org.usfirst.frc.team3695.robot.util.Util;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Commands the gear flaps
 */
public class ButtonCommandSpin extends Command {
	
	Direction direction;
	
    public ButtonCommandSpin(Direction direction) {
    	this.direction = direction;
        requires(Robot.SUB_MANIPULATOR);
    }

    protected void initialize() {
    	Robot.SUB_MANIPULATOR.startSpinning(direction, Util.getAndSetDouble("Spinning Speed", Constants.SPINNY_SPEED));
    }

    protected void execute() {}

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	
    }

    protected void interrupted() {
    	end();
    }
}
