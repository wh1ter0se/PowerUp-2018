package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.OI;
import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/** manually command the robot with joysticks */
public class ManualCommandDrive extends Command {
	
    public ManualCommandDrive() {
        requires(Robot.SUB_DRIVE);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.SUB_DRIVE.driveTank(OI.DRIVER);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
