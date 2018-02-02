package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Tell the arduino what the current stage is
 */
public class ArduinoCommandSendStage extends Command {
		
    public ArduinoCommandSendStage() {
        requires(Robot.SUB_ARDUINO);
    }

    protected void initialize() {}

    protected void execute() {
    	//Robot.SUB_ARDUINO.sendStage();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {
    	end();
    }
}
