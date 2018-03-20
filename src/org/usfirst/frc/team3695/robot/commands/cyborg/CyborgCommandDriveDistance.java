
package org.usfirst.frc.team3695.robot.commands.cyborg;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

@Deprecated //Can continue being used for runForIt, but should be replaced with pathfinder
public class CyborgCommandDriveDistance extends Command {

    private static final long TIME_WAIT = 3000;
    private double inches;
    private static final int ALLOWABLE_ERROR = 2;

    private Boolean isFinished;
    private long runTime;
    private long startTime;

    public CyborgCommandDriveDistance(double inches, int timeoutms) {
        this.inches = inches;
        requires(Robot.SUB_DRIVE);
//        Robot.SUB_DRIVE.autoDrive.reset();
        runTime = timeoutms;
    }

    protected void initialize() {
//    	Robot.SUB_DRIVE.autoDrive.reset();
//    	inches = Util.getAndSetDouble("Drive Distance Inches", 10); // take out in final version
//    	AutoDrive.setPIDF(0);
    	Robot.SUB_DRIVE.driveDistance(inches, inches);
    	startTime = System.currentTimeMillis();
    }

    protected void execute() {

    	DriverStation.reportWarning("DRIVING " + inches + " INCHES", false);
//    	SmartDashboard.putNumber("Left Encoder Inches", Robot.SUB_DRIVE.autoDrive.getLeftInches());
//    	SmartDashboard.putNumber("Right Encoder Inches", Robot.SUB_DRIVE.autoDrive.getRightInches());
//        Robot.SUB_DRIVE.autoDrive.getError();
    }

    protected boolean isFinished() {
        isFinished = startTime + runTime < System.currentTimeMillis();
//        boolean leftInRange =
//        		Robot.SUB_DRIVE.autoDrive.getLeftInches() > (inches) - ALLOWABLE_ERROR &&
//        		Robot.SUB_DRIVE.autoDrive.getLeftInches() < (inches) + ALLOWABLE_ERROR;
//        boolean rightInRange =
//        		Robot.SUB_DRIVE.autoDrive.getRightInches() > inches - ALLOWABLE_ERROR &&
//        		Robot.SUB_DRIVE.autoDrive.getRightInches() < inches + ALLOWABLE_ERROR;
//        return (leftInRange && rightInRange) || isFinished;
        return isFinished;
    }

    protected void end() {
        DriverStation.reportWarning("CyborgCommandDriveDistance finished", false);
        Robot.SUB_DRIVE.driveDirect(0, 0);
//        DriverStation.reportWarning("LEFT:" + Robot.SUB_DRIVE.autoDrive.getLeftInches() + ", " + "RIGHT:" + Robot.SUB_DRIVE.autoDrive.getRightInches(), false);
        isFinished = false;
    }

    protected void interrupted() {
    	end();
    }
}