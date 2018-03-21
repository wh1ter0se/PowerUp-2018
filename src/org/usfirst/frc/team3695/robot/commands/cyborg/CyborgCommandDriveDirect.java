
package org.usfirst.frc.team3695.robot.commands.cyborg;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;
@Deprecated //We shouldn't ever be driving direct.
//If we want to continue using drive direct it should be updated to use the setTalons in AutoDrive
public class CyborgCommandDriveDirect extends Command {

    private static final long TIME_WAIT = 1000;
    private double percent;
    private boolean inRange;

    public CyborgCommandDriveDirect(double percent) {
        this.percent = percent;
        requires(Robot.SUB_DRIVE);
    }

    protected void initialize() {
    	DriverStation.reportWarning("DRIVING BY POWER", false);
    	//Reset PID?
        long time = System.currentTimeMillis() + TIME_WAIT;
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