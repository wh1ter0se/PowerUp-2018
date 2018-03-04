package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team3695.robot.OI;
import org.usfirst.frc.team3695.robot.Robot;

/** toggles the state of the clamp */
public class ToggleCommandIntimidate extends Command {

    public ToggleCommandIntimidate() {
    	requires(Robot.SUB_DRIVE);
    	requires(Robot.SUB_MANIPULATOR);
    }

    protected void initialize() {}

    protected void execute() {
    	Robot.SUB_MANIPULATOR.rev(OI.DRIVER);
    }

    protected boolean isFinished() { return false; }

    protected void end() {}

    protected void interrupted() {}
}
