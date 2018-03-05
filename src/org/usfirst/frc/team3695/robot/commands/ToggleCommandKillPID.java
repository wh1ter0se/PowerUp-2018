package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive.PID;
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
    		PID.setPIDF(0,
    				Util.getAndSetDouble("Distance-P", .5),
					Util.getAndSetDouble("Distance-I", 0),
					Util.getAndSetDouble("Distance-D", 0),
					Util.getAndSetDouble("Distance-F", 0));
    		PID.setPIDF(1,
    				Util.getAndSetDouble("Rotation-P", .5),
					Util.getAndSetDouble("Rotation-I", 0),
					Util.getAndSetDouble("Rotation-D", 0),
					Util.getAndSetDouble("Rotation-F", 0));
    	} else {
    		PID.setPIDF(0,0,0,0,0);
    		PID.setPIDF(1,0,0,0,0);
    	}
    }

    protected void execute() { }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
