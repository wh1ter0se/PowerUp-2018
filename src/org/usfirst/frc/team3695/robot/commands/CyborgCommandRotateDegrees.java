package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive.PID;
import org.usfirst.frc.team3695.robot.util.Util;

public class CyborgCommandRotateDegrees extends Command {
    public static final double SCALAR = (Constants.DISTANCE_BETWEEN_WHEELS * Math.PI) / 360;
    public static long RUN_TIME = 3000; //lol parametrics
    public static int ALLOWABLE_ERROR = 8;
    private static long startTime;

    private boolean isFinished;
    public double inches;

    public CyborgCommandRotateDegrees(double degrees, long timeout) {
        isFinished = false;
        inches = degrees * SCALAR;
        startTime = System.currentTimeMillis();
        RUN_TIME = timeout;
        requires(Robot.SUB_DRIVE);
        Robot.SUB_DRIVE.pid.reset();
    }

    protected void initialize() {
        Robot.SUB_DRIVE.pid.reset();
        DriverStation.reportWarning("ROTATING " + (inches / SCALAR) + " DEGREES" + ((inches > 0) ? "CW" : "CCW"), false);
//      inches = Util.getAndSetDouble("Rotate Degrees", 0) * SCALAR; // take out in final version
        PID.setPIDF(1,
        		Util.getAndSetDouble("Rotation-P", 0.585),
                Util.getAndSetDouble("Rotation-I", 0),
                Util.getAndSetDouble("Rotation-D", 0.001),
                Util.getAndSetDouble("Rotation-F", 0));
        Robot.SUB_DRIVE.driveDistance(inches, -1 * inches);
    }

    protected void execute() {
        DriverStation.reportWarning("ROTATING " + (inches / SCALAR) + " DEGREES" + ((inches > 0) ? "CW" : "CCW"), false);
        SmartDashboard.putNumber("Left Encoder Inches", Robot.SUB_DRIVE.pid.getLeftInches());
        SmartDashboard.putNumber("Right Encoder Inches", Robot.SUB_DRIVE.pid.getRightInches());
        SmartDashboard.putNumber("Error", Robot.SUB_DRIVE.pid.getError());
    }

    protected boolean isFinished() {
        if (startTime + RUN_TIME >= System.currentTimeMillis()) {
            isFinished = false;
        } else {
            isFinished = true;
        }
        boolean leftInRange =
                Robot.SUB_DRIVE.pid.getLeftInches() > (inches) - ALLOWABLE_ERROR &&
                        Robot.SUB_DRIVE.pid.getLeftInches() < (inches) + ALLOWABLE_ERROR;
        boolean rightInRange =
                Robot.SUB_DRIVE.pid.getRightInches() > inches - ALLOWABLE_ERROR &&
                        Robot.SUB_DRIVE.pid.getRightInches() < inches + ALLOWABLE_ERROR;
        return (leftInRange && rightInRange) || isFinished;
    }

    protected void end() {
        DriverStation.reportWarning("CyborgCommandRotateDegrees finished", false);
        Robot.SUB_DRIVE.driveDirect(0, 0);
        isFinished = false;
    }

    protected void interrupted() {
    }
}