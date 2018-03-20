package org.usfirst.frc.team3695.robot.commands.toggle;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

/** Toggle PID */
@Deprecated //No longer using PID directly through the Talons
public class ToggleCommandKillPID extends Command {

	private static Boolean PID_ENABLED;

    public ToggleCommandKillPID() {
        requires(Robot.SUB_DRIVE);
        PID_ENABLED = true;
    }

    protected void initialize() {
    	PID_ENABLED = !PID_ENABLED;
    	//Need to rewrite for new system.
//    	if (PID_ENABLED) {
//    		AutoDrive.setPIDF(0);
//    	} else {
//    		AutoDrive.setPIDF(0, new double[] {0,0,0,0}, new double[] {0,0,0,0});
//    		AutoDrive.setPIDF(1, new double[] {0,0,0,0}, new double[] {0,0,0,0});
//    	}
    }

    protected void execute() { }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
