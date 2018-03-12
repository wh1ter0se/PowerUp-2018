package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive.PIDF;
import org.usfirst.frc.team3695.robot.util.Util;

/** Toggle PID */
public class ToggleCommandKillPID extends Command {
	
	public static Boolean PID_ENABLED;

    public ToggleCommandKillPID() {
        requires(Robot.SUB_DRIVE);
        PID_ENABLED = true;
    }

    protected void initialize() {
    	PID_ENABLED = !PID_ENABLED;
    	
    	if (PID_ENABLED) {
    		PIDF.setPIDF();
    	} else {
    		PIDF.setPIDF(0, new double[] {0,0,0,0}, new double[] {0,0,0,0});
    		PIDF.setPIDF(1, new double[] {0,0,0,0}, new double[] {0,0,0,0});
    	}
    }

    protected void execute() { }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
