package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.OI;
import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/** move the mast */
public class ManualCommandGrow extends Command {
	
    public ManualCommandGrow() {
        requires(Robot.SUB_MAST);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.SUB_MAST.moveBySpeed(OI.OPERATOR);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
