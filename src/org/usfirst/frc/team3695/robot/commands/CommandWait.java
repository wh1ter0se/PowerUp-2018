package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Wait for the number of milliseconds passed at instantiation
 */
public class CommandWait extends Command {

	private long wait;
	private long startTime;
	
    public CommandWait(long time) {
        this.wait = time;
    }

    protected void initialize() {
    	DriverStation.reportWarning("WAITING " + wait + " MILISECONDS", false);
    	startTime = System.currentTimeMillis();
    }

    protected void execute() {}

    protected boolean isFinished() {
        return startTime + wait < System.currentTimeMillis();
    }

    protected void end() {
    	DriverStation.reportWarning("DONE WAITING", false);
    }

    protected void interrupted() {
    	end();
    }
}
