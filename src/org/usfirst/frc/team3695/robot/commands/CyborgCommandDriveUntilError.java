package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;

public class CyborgCommandDriveUntilError extends Command {
    public long errorTime;
    public long runTime;

    final double ERROR_DISTANCE_MARGIN = 2;

    double currentPosLeft;
    double currentPosRight;
    
    private long time = 0;

    public CyborgCommandDriveUntilError(long errorTime) {
        this.errorTime = errorTime;
        runTime = System.currentTimeMillis();
        requires(Robot.SUB_DRIVE);
        currentPosLeft = Robot.SUB_DRIVE.pid.getLeftInches();
        currentPosRight = Robot.SUB_DRIVE.pid.getRightInches();
    }

    protected void initialize() {}

    protected void execute() {
        double speed = Util.getAndSetDouble("SPEED ERROR: Forward", -0.25);
        Robot.SUB_DRIVE.driveDirect(speed, speed);
    }

    protected boolean isFinished() {
        if (((currentPosLeft + ERROR_DISTANCE_MARGIN) > Robot.SUB_DRIVE.pid.getLeftInches() && (currentPosLeft - ERROR_DISTANCE_MARGIN) < Robot.SUB_DRIVE.pid.getLeftInches())
                || ((currentPosRight + ERROR_DISTANCE_MARGIN) > Robot.SUB_DRIVE.pid.getRightInches() && (currentPosRight - ERROR_DISTANCE_MARGIN) < Robot.SUB_DRIVE.pid.getRightInches())){
            currentPosLeft = Robot.SUB_DRIVE.pid.getLeftInches();
            currentPosRight = Robot.SUB_DRIVE.pid.getRightInches();
            runTime = System.currentTimeMillis();
            return false;
        }
        return errorTime + runTime < System.currentTimeMillis();
    }

    protected void end() {
        DriverStation.reportWarning("CyborgCommandDriveUntilError finished", false);
        Robot.SUB_DRIVE.driveDirect(0, 0);
    }

    protected void interrupted() {
        end();
    }
}