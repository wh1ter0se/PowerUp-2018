package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3695.robot.OI;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;

import javax.swing.*;
import java.sql.Driver;

/** manually command the robot with joysticks */
public class ManualCommandDrive extends Command {
	
    public ManualCommandDrive() {
        requires(Robot.SUB_DRIVE);
    }

    protected void initialize() {}

    protected void execute() {
    	
    	Robot.SUB_DRIVE.setInverts();
    	
        SmartDashboard.putBoolean("Forza Dampener (Docking mode)", Robot.SUB_DRIVE.docking);
        SmartDashboard.putBoolean("Reversing mode", Robot.SUB_DRIVE.reversing);
        SmartDashboard.putBoolean("Narrower (Turn inhibitor)", Robot.SUB_DRIVE.narrowing);
        SmartDashboard.putNumber("Right Encoder Inches", Robot.SUB_DRIVE.pid.getRightInches());
        SmartDashboard.putNumber("Left Encoder Inches", Robot.SUB_DRIVE.pid.getLeftInches());
        SmartDashboard.putNumber("Match Time", DriverStation.getInstance().getMatchTime());

    	switch (Robot.SUB_DRIVE.drivetrain) {
    		case ROCKET_LEAGUE:
    			Robot.SUB_DRIVE.driveRLTank(OI.DRIVER, Util.getAndSetDouble("Rocket Ramp", .75), Util.getAndSetDouble("Drive Inhibitor", 1));
    			break;
    		case FORZA: 
    			Robot.SUB_DRIVE.driveForza(OI.DRIVER, Util.getAndSetDouble("Forza Ramp", .75), Util.getAndSetDouble("Radius", 1), Util.getAndSetDouble("Drive Inhibitor", 1));
    			break;
            case BROGAN:
                Robot.SUB_DRIVE.driveBrogan(OI.DRIVER, Util.getAndSetDouble("Brogan Ramp", .35), Util.getAndSetDouble("Drive Inhibitor", 1));
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
