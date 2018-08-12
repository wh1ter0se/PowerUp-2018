package org.usfirst.frc.team3695.robot.commands.toggle;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

/**
 *  Put the hook up and down
 */
public class ToggleCommandHook extends Command {
    
    public ToggleCommandHook() {
        requires(Robot.SUB_HOOK);
    }

    protected void initialize() {
        Robot.SUB_HOOK.raiseHook();
    }

    protected void execute() {}

    protected boolean isFinished() { return false; }

    protected void end() {
        Robot.SUB_HOOK.lowerHook();
    }

    protected void interrupted() {
    	end();
    }
}
