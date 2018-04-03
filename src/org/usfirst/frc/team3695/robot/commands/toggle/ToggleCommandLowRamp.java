package org.usfirst.frc.team3695.robot.commands.toggle;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3695.robot.OI;
import org.usfirst.frc.team3695.robot.Robot;

/**
 * Toggle the bot in and out of docking mode
 *  Docking mode limits the maximum speed of the robot for more sensitive movements
 */
public class ToggleCommandLowRamp extends Command {

    private static final String smartDashKey = "Low Ramp";
    public ToggleCommandLowRamp() {
        requires(Robot.SUB_DRIVE);
    }

    protected void initialize() { }

    protected void execute() {
        Robot.SUB_DRIVE.driveRLTank(OI.DRIVER, 0.1, 1);
        SmartDashboard.putBoolean(smartDashKey, true);
    }

    protected boolean isFinished() { return false; }

    protected void end() {
        SmartDashboard.putBoolean(smartDashKey, false);
    }

    protected void interrupted() {
        end();
    }
}
