
package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive.PID;
import org.usfirst.frc.team3695.robot.util.Util;

public class CyborgCommandDriveDistance extends Command {

    public static final long TIME_WAIT = 3000;
    public double inches;
    public static final int ALLOWABLE_ERROR = 1;

    Boolean isFinished;
    long runTime;
    long startTime;

    public CyborgCommandDriveDistance(double inches, int timeoutms) {
        this.inches = inches;
        requires(Robot.SUB_DRIVE);
        Robot.SUB_DRIVE.pid.reset();
        runTime = timeoutms;
    }

    protected void initialize() {
    	Robot.SUB_DRIVE.pid.reset();
//    	inches = Util.getAndSetDouble("Drive Distance Inches", 10); // take out in final version
    	PID.setPIDF(0,
    			Util.getAndSetDouble("Distance-P", .5),
				Util.getAndSetDouble("Distance-I", 0),
                Util.getAndSetDouble("Distance-D", 0),
				Util.getAndSetDouble("Distance-F", 0));
    	Robot.SUB_DRIVE.driveDistance(inches, inches);
    	startTime = System.currentTimeMillis();
    }

    protected void execute() {

    	DriverStation.reportWarning("DRIVING " + inches + " INCHES", false);
    	SmartDashboard.putNumber("Left Encoder Inches", Robot.SUB_DRIVE.pid.getLeftInches());
    	SmartDashboard.putNumber("Right Encoder Inches", Robot.SUB_DRIVE.pid.getRightInches());


        SmartDashboard.putNumber("Error", Robot.SUB_DRIVE.pid.getError());
    }

    protected boolean isFinished() {
        isFinished = startTime + runTime < System.currentTimeMillis();
        boolean leftInRange =
        		Robot.SUB_DRIVE.pid.getLeftInches() > (inches) - ALLOWABLE_ERROR &&
        		Robot.SUB_DRIVE.pid.getLeftInches() < (inches) + ALLOWABLE_ERROR;
        boolean rightInRange =
        		Robot.SUB_DRIVE.pid.getRightInches() > inches - ALLOWABLE_ERROR &&
        		Robot.SUB_DRIVE.pid.getRightInches() < inches + ALLOWABLE_ERROR;
        return (leftInRange && rightInRange) || isFinished;
    }

    protected void end() {
        DriverStation.reportWarning("CyborgCommandDriveDistance finished", false);
        Robot.SUB_DRIVE.driveDirect(0, 0);
        isFinished = false;
    }

    protected void interrupted() {
    	end();
    }
}