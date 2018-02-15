package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Enables and disables the shooter
 */
public class CommandWait extends Command {

	private long wait;
	private long startTime;
	
    public CommandWait(long time) {
        this.wait = time;
    }

    protected void initialize() {
    	startTime = System.currentTimeMillis();
    }

    protected void execute() {}

    protected boolean isFinished() {
        return startTime + wait < System.currentTimeMillis();
    }

    protected void end() {}

    protected void interrupted() {
    	end();
    }
}
