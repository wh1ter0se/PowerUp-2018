package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

public class CyborgCommandDriveUntilError extends Command {
    private long masterError;
    private long errorTime;
    private long runTime;
    private long startTime;

    private double allowableError;

    private double currentPosLeft;
    private double currentPosRight;
    
    private long time = 0;
    
    private double speed;

    public CyborgCommandDriveUntilError(long errorTime, double allowableError, double speed, long masterError) {
        this.errorTime = errorTime;
        this.allowableError = allowableError;
        this.masterError = masterError;
        runTime = System.currentTimeMillis();
        startTime = System.currentTimeMillis();
        requires(Robot.SUB_DRIVE);
        //Print PID values to smartdash?
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
        if ((masterError + startTime) < System.currentTimeMillis()) return true;
        //This will all need to be rewritten for the new code.
//        if (!((currentPosLeft + allowableError) > Robot.SUB_DRIVE.pidf.getLeftInches() && (currentPosLeft - allowableError) < Robot.SUB_DRIVE.pidf.getLeftInches())
//                || !((currentPosRight + allowableError) > Robot.SUB_DRIVE.pidf.getRightInches() && (currentPosRight - allowableError) < Robot.SUB_DRIVE.pidf.getRightInches())){
//            currentPosLeft = Robot.SUB_DRIVE.pidf.getLeftInches();
//            currentPosRight = Robot.SUB_DRIVE.pidf.getRightInches();
//            runTime = System.currentTimeMillis();
//            return false;
//        }
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