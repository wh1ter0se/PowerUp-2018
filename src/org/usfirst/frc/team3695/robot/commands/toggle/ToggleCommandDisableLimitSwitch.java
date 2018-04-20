package org.usfirst.frc.team3695.robot.commands.toggle;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Mast;

/**
 * Code that kills compressor until interruption
 */
public class ToggleCommandDisableLimitSwitch extends Command {

    private Mast limit;

    public ToggleCommandDisableLimitSwitch(Mast limit) {
        this.limit = limit;
        requires(Robot.SUB_MAST);
    }

    protected void initialize() {
        SmartDashboard.putBoolean(limit.toBetterString() + " enabled", Robot.SUB_MAST.toggleLimitSwitchEnabled(limit));
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
}
