package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Send miscellaneous data to the arduino
 */
public class ArduinoCommandSendArbitraryData extends Command {
		
    public ArduinoCommandSendArbitraryData() {
        requires(Robot.SUB_ARDUINO);
    }

    protected void initialize() {}

    protected void execute() {
    	Util.getAndSetDouble("Arduino Data", 0);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {
    	end();
    }
}
