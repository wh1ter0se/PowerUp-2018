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
    private static long startTime;

    private boolean isFinished;
    public double inches;

    public CyborgCommandRotateDegrees(double degrees, long timeout) {
        inches = degrees * SCALAR;
        startTime = System.currentTimeMillis();
        RUN_TIME = timeout;
        requires(Robot.SUB_DRIVE);
    }

    protected void initialize() {
    	DriverStation.reportWarning("ROTATING " + (inches / SCALAR) + " DEGREES" + ((inches > 0) ? "CW" : "CCW"), false);
        isFinished = false;
        Robot.SUB_DRIVE.pid.reset();
//      inches = Util.getAndSetDouble("Rotate Degrees", 0) * SCALAR; // take out in final version
        PID.setPIDF(1,
        		Util.getAndSetDouble("Rotation-P", .11),
                Util.getAndSetDouble("Rotation-I", 0),
                Util.getAndSetDouble("Rotation-D", 0),
                Util.getAndSetDouble("Rotation-F", 0));
        Robot.SUB_DRIVE.driveDistance(inches, -1* inches);
    }

    protected void execute() {
        DriverStation.reportWarning("ROTATING " + (inches / SCALAR) + " DEGREES" + ((inches > 0) ? "CW" : "CCW"), false);
        SmartDashboard.putNumber("Left Encoder Inches", Robot.SUB_DRIVE.pid.getLeftInches());
        SmartDashboard.putNumber("Right Encoder Inches", Robot.SUB_DRIVE.pid.getRightInches());
        SmartDashboard.putNumber("Error", Robot.SUB_DRIVE.pid.getError());
    }

    protected boolean isFinished() {
        if (startTime + RUN_TIME >= System.currentTimeMillis()){
            Robot.SUB_MANIPULATOR.spit();
        } else {
            isFinished = true;
        }

        boolean leftInRange =
        		Robot.SUB_DRIVE.pid.getLeftInches() > Robot.SUB_DRIVE.leftify(inches) - Robot.SUB_DRIVE.leftify(2) &&
        		Robot.SUB_DRIVE.pid.getLeftInches() < Robot.SUB_DRIVE.leftify(inches) + Robot.SUB_DRIVE.leftify(2);
        boolean rightInRange =
        		Robot.SUB_DRIVE.pid.getRightInches() > Robot.SUB_DRIVE.rightify(inches) - Robot.SUB_DRIVE.rightify(2) &&
        		Robot.SUB_DRIVE.pid.getRightInches() < Robot.SUB_DRIVE.rightify(inches) + Robot.SUB_DRIVE.rightify(2);
        return leftInRange && rightInRange || isFinished;
    }

    protected void end() {
        DriverStation.reportWarning("CyborgCommandRotateDegrees finished", false);
        Robot.SUB_DRIVE.driveDirect(0, 0);
        isFinished = false;
    }

    protected void interrupted() {}
}