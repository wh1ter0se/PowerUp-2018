package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import org.usfirst.frc.team3695.robot.OI;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;

import edu.wpi.first.wpilibj.command.Command;

/** move the mast */
public class ManualCommandSpin extends Command {

    
    public ManualCommandSpin() {
        requires(Robot.SUB_MANIPULATOR);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.SUB_MANIPULATOR.spinByJoystick(OI.OPERATOR);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
