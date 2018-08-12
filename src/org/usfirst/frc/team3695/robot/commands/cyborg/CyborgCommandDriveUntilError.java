package org.usfirst.frc.team3695.robot.commands.cyborg;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;

public class CyborgCommandDriveUntilError extends Command {
    long masterError;
    public long errorTime;
    public long runTime;
    public long startTime;

    double allowableError = 2;

    double currentPosLeft;
    double currentPosRight;
    
    private long time = 0;
    
    double speed;
    public CyborgCommandDriveUntilError(long errorTime, double allowableError, double speed, long masterError) {
        this.errorTime = errorTime;
        this.allowableError = allowableError;
        this.masterError = masterError;
        runTime = System.currentTimeMillis();
        startTime = System.currentTimeMillis();
        requires(Robot.SUB_DRIVE);
        currentPosLeft = Robot.SUB_DRIVE.getLeftInches();
        currentPosRight = Robot.SUB_DRIVE.getRightInches();
        this.speed = speed;
    }

    protected void initialize() {
        runTime = System.currentTimeMillis();
    }

    protected void execute() {
//        double speed = Util.getAndSetDouble("SPEED ERROR: Forward", -0.25);
        Robot.SUB_DRIVE.driveDirect(speed, speed);
    }

    protected boolean isFinished() {
//        if (masterError + startTime < System.currentTimeMillis()) return true;
        if (!((currentPosLeft + allowableError) > Robot.SUB_DRIVE.getLeftInches() && (currentPosLeft - allowableError) < Robot.SUB_DRIVE.getLeftInches())
                || !((currentPosRight + allowableError) > Robot.SUB_DRIVE.getRightInches() && (currentPosRight - allowableError) < Robot.SUB_DRIVE.getRightInches())){
            currentPosLeft = Robot.SUB_DRIVE.getLeftInches();
            currentPosRight = Robot.SUB_DRIVE.getRightInches();
            runTime = System.currentTimeMillis();
            return false;
        }
        return runTime + errorTime < System.currentTimeMillis();
    }

    protected void end() {
        DriverStation.reportWarning("CyborgCommandDriveUntilError finished", false);
        runTime = Long.MAX_VALUE;
        Robot.SUB_DRIVE.driveDirect(0, 0);
    }

    protected void interrupted() {
        end();
    }
}