package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import org.usfirst.frc.team3695.robot.OI;
import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/** move the mast */
public class ButtonCommandHook extends Command {
    
    public ButtonCommandHook() {
        requires(Robot.SUB_HOOK);
    }

    protected void initialize() {
    	Robot.SUB_HOOK.lowerHook();
    }

    protected void execute() {}

    protected boolean isFinished() { return false; }

    protected void end() {
    	Robot.SUB_HOOK.raiseHook();
    }

    protected void interrupted() {
    	end();
    }
}
