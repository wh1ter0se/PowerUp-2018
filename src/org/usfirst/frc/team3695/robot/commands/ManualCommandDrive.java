package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.OI;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Drivetrain;
import org.usfirst.frc.team3695.robot.util.Util;

import edu.wpi.first.wpilibj.command.Command;

/** manually command the robot with joysticks */
public class ManualCommandDrive extends Command {
	
    public ManualCommandDrive() {
        requires(Robot.SUB_DRIVE);
        requires(Robot.SUB_MANIPULATOR);
    }

    protected void initialize() {}

    protected void execute() {
    	switch (Robot.SUB_DRIVE.drivetrain) {
    		case ROCKET_LEAGUE:
    			Robot.SUB_DRIVE.driveRLTank(OI.DRIVER);
    			break;
    		case FORZA: 
    			Robot.SUB_DRIVE.driveForza(OI.DRIVER, Util.getAndSetDouble("Ramp", .25), Util.getAndSetDouble("Radius", 1));
    			break;
    		case REV:
    			Robot.SUB_MANIPULATOR.rev(OI.DRIVER);
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
