package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;

public class CyborgCommandRotateDegrees extends Command {
    public static final double SCALAR = (Constants.DISTANCE_BETWEEN_WHEELS * Math.PI) / 360;
    public static final long TIME_WAIT = 500;

    private boolean inRange;
    private long time;
    public final double inches;

    public CyborgCommandRotateDegrees(double degrees) {
        inches = degrees * SCALAR;
        requires(Robot.SUB_DRIVE);
    }

    protected void initialize() {
    	DriverStation.reportWarning("ROTATING " + (inches / SCALAR) + " DEGREES" + ((inches > 0) ? "CW" : "CCW"), false);
        time = System.currentTimeMillis() + TIME_WAIT;
        Robot.SUB_DRIVE.reset();
    }

    protected void execute() {
        inRange = Robot.SUB_DRIVE.driveDistance(inches, -inches);
    }

    protected boolean isFinished() {
    	DriverStation.reportWarning("DONE ROTATING", false);
        if(!inRange) {
            time = System.currentTimeMillis() + TIME_WAIT;
        }
        return time < System.currentTimeMillis();
    }

    protected void end() {
        DriverStation.reportWarning("CyborgCommandRotateDegrees finished", false);
        Robot.SUB_DRIVE.driveDirect(0, 0);
    }

    protected void interrupted() {
        end();
    }
}