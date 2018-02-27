
package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;

public class CyborgCommandDriveDirect extends Command {

    public static final long TIME_WAIT = 1000;
    public double percent;
    private long time;
    private boolean inRange;

    public CyborgCommandDriveDirect(double percent) {
        this.percent = percent;
        requires(Robot.SUB_DRIVE);
    }

    protected void initialize() {
    	DriverStation.reportWarning("DRIVING BY POWER", false);
        Robot.SUB_DRIVE.reset();
        time = System.currentTimeMillis() + TIME_WAIT;
    }

    protected void execute() {
    	percent = Util.getAndSetDouble("Drive Direct Power", 0);
        Robot.SUB_DRIVE.driveDirect(percent, percent);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        DriverStation.reportWarning("CyborgCommandDriveDirect finished", false);
        Robot.SUB_DRIVE.driveDirect(0, 0);
    }

    protected void interrupted() {
        end();
    }
}