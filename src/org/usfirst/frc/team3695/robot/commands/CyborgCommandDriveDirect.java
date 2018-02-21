
package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;

public class CyborgCommandDriveDirect extends Command {

    public static final long TIME_WAIT = 1000;
    public final double percent;
    private long time;
    private boolean inRange;

    public CyborgCommandDriveDirect(double percent) {
        this.percent = percent;
        requires(Robot.SUB_DRIVE);
    }

    protected void initialize() {
    	DriverStation.reportWarning("DRIVING BY POWER", false);
    	Robot.SUB_DRIVE.setAuto(true);
        Robot.SUB_DRIVE.reset();
        time = System.currentTimeMillis() + TIME_WAIT;
    }

    protected void execute() {
        inRange = Robot.SUB_DRIVE.driveDistance(percent, percent);
    }

    protected boolean isFinished() {
        if(!inRange) {
            time = System.currentTimeMillis() + TIME_WAIT;
        }
        return time < System.currentTimeMillis();
    }

    protected void end() {
        Robot.SUB_DRIVE.setAuto(false);
        DriverStation.reportWarning("CyborgCommandDriveDirect finished", false);
        Robot.SUB_DRIVE.driveDirect(0, 0);
    }

    protected void interrupted() {
        end();
    }
}