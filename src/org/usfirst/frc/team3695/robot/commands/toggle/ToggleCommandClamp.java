package org.usfirst.frc.team3695.robot.commands.toggle;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

/** toggles the state of the clamp */
public class ToggleCommandClamp extends Command {
	
    public ToggleCommandClamp() {
        requires(Robot.SUB_CLAMP);
    }

    protected void initialize() {
    	Robot.SUB_CLAMP.closeArms();
    }

    protected void execute() {}

    protected boolean isFinished() { return false; }

    protected void end() {
    	Robot.SUB_CLAMP.openArms();
    }

    protected void interrupted() {
    	end();
    }
}
