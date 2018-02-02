package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import org.usfirst.frc.team3695.robot.OI;
import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/** move the mast */
public class ManualCommandHook extends Command {

    
    public ManualCommandHook() {
        requires(Robot.SUB_HOOK);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.SUB_HOOK.swing(OI.OPERATOR);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
