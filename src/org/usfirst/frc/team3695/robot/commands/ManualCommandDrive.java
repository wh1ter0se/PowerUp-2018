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
    	Robot.SUB_DRIVE.OFFSET = 0;
    	Robot.SUB_DRIVE.METADIRECTION = 0;
    	Robot.SUB_DRIVE.IN_DRIFT = false;
    }

    protected void execute() {
    	Robot.SUB_DRIVE.driftDrive(OI.DRIVER);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
