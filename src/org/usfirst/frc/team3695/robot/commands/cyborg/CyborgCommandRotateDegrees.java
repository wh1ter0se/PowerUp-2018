package org.usfirst.frc.team3695.robot.commands.cyborg;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;

@Deprecated //Use pathfinder to rotate
public class CyborgCommandRotateDegrees extends Command {
    private static final double SCALAR = (Constants.DISTANCE_BETWEEN_WHEELS * Math.PI) / 360;
    private static long runTime = 3000; //lol parametric parameters
    private static int ALLOWABLE_ERROR = 2;
    private static long startTime;

    private boolean isFinished;
    private double inches;

    public CyborgCommandRotateDegrees(double degrees, long timeout) {
        isFinished = false;
        inches = degrees * SCALAR;
        runTime = timeout;
        requires(Robot.SUB_DRIVE);
//        Robot.SUB_DRIVE.autoDrive.reset();
    }

    protected void initialize() {
//        Robot.SUB_DRIVE.autoDrive.reset();
//        AutoDrive.setPIDF(inches > 0 ? 1 : 2);
//        inches = Util.getAndSetDouble("Rotate Degrees", 0) * SCALAR; // take out in final version
        DriverStation.reportWarning("ROTATING " + (inches / SCALAR) + " DEGREES" + ((inches > 0) ? "CW" : "CCW") + " AND INCHES: " +inches, false);
        Robot.SUB_DRIVE.driveDistance(inches, -1 * inches);
        startTime = System.currentTimeMillis();
        DriverStation.reportWarning("Rotate Init Complete", true);
    }

    protected void execute() {
        DriverStation.reportWarning("ROTATING " + (inches / SCALAR) + " DEGREES" + ((inches > 0) ? "CW" : "CCW"), false);
//        SmartDashboard.putNumber("Left Encoder Inches", Robot.SUB_DRIVE.autoDrive.getLeftInches());
//        SmartDashboard.putNumber("Right Encoder Inches", Robot.SUB_DRIVE.autoDrive.getRightInches());
//        Robot.SUB_DRIVE.autoDrive.getError();
    }

    protected boolean isFinished() {
        isFinished = startTime + runTime < System.currentTimeMillis();
//        boolean leftInRange =
//                Robot.SUB_DRIVE.autoDrive.getLeftInches() > (inches) - ALLOWABLE_ERROR &&
//                Robot.SUB_DRIVE.autoDrive.getLeftInches() < (inches) + ALLOWABLE_ERROR;
//        boolean rightInRange =
//                Robot.SUB_DRIVE.autoDrive.getRightInches() > inches - ALLOWABLE_ERROR &&
//                Robot.SUB_DRIVE.autoDrive.getRightInches() < inches + ALLOWABLE_ERROR;
        return /*(leftInRange && rightInRange) ||*/ isFinished;
    }

    protected void end() {
        DriverStation.reportWarning("CyborgCommandRotateDegrees finished", false);
        Robot.SUB_DRIVE.driveDirect(0, 0);
        isFinished = false;
    }

    protected void interrupted() {
    	end();
    }
}