package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3695.robot.OI;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;

/** manually command the robot with joysticks */
public class ManualCommandDrive extends Command {
	
    public ManualCommandDrive() {
        requires(Robot.SUB_DRIVE);
    }

    protected void initialize() {}

    protected void execute() {
        SmartDashboard.putNumber("Tilt Angle", Robot.SUB_DRIVE.getYAngle());
        SmartDashboard.putBoolean("Docked", Robot.SUB_DRIVE.docking);
        SmartDashboard.putBoolean("Reversed", Robot.SUB_DRIVE.reversing);
        SmartDashboard.putNumber("Right Encoder Inches", Robot.SUB_DRIVE.pid.getRightInches());
        SmartDashboard.putNumber("Left Encoder Inches", Robot.SUB_DRIVE.pid.getLeftInches());

    	switch (Robot.SUB_DRIVE.drivetrain) {
    		case ROCKET_LEAGUE:
    			Robot.SUB_DRIVE.driveRLTank(OI.DRIVER, Util.getAndSetDouble("Rocket Ramp", .75), Util.getAndSetDouble("Drive Inhibitor", 1));
    			break;
    		case FORZA: 
    			Robot.SUB_DRIVE.driveForza(OI.DRIVER, Util.getAndSetDouble("Forza Ramp", .75), Util.getAndSetDouble("Radius", 1), Util.getAndSetDouble("Drive Inhibitor", 1));
    			break;
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
