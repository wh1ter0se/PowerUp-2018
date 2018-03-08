package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;

/** toggles the state of the clamp */
public class ToggleCommandNarrow extends Command {

    boolean isFinished;

    public ToggleCommandNarrow() {
        requires(Robot.SUB_DRIVE);
        isFinished = false;
    }

    protected void initialize() {
        Robot.SUB_DRIVE.toggleNarrowing(Util.getAndSetDouble("Turning Inhibitor", 0.5d));
        isFinished = true;
    }

    protected void execute() {}

    protected boolean isFinished() { return isFinished; }

    protected void end() {
        isFinished = false;
    }

    protected void interrupted() {
        end();
    }
}
