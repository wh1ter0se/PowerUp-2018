package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.OI;
import org.usfirst.frc.team3695.robot.Robot;

/** move the mast */
public class ManualCommandSpin extends Command {

    
    public ManualCommandSpin() {
        requires(Robot.SUB_MANIPULATOR);
    	DriverStation.reportWarning("Spin Construct", false);
    }

    protected void initialize() {
    	DriverStation.reportWarning("Spin Init", false);
    }

    protected void execute() {
    	DriverStation.reportWarning("Spin Execute", false);
    	Robot.SUB_MANIPULATOR.spinByJoystick(OI.OPERATOR);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
