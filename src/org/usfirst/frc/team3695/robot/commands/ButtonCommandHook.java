package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Direction;
import org.usfirst.frc.team3695.robot.util.Util;

/**
 * Commands the gear flaps
 */
public class ButtonCommandHook extends Command {

	Direction direction;

    public ButtonCommandHook() {
        requires(Robot.SUB_CANDYCANE);
    }

    protected void initialize() {
    	Robot.SUB_CANDYCANE.move(Constants.CANDYCANE_SPEED);
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
