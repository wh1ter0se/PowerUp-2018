package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Direction;
import org.usfirst.frc.team3695.robot.util.Util;

import edu.wpi.first.wpilibj.command.Command;

/**
 * toggles the state of the clamp
 */
public class CommandArduinoTest1 extends Command {
	
	Direction direction;
	
    public CommandArduinoTest1() {
        requires(Robot.SUB_CLAMP);
    }

    protected void initialize() {
    	Robot.SUB_ARDUINO.test1();
    }

    protected void execute() {}

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {
    	end();
    }
}
