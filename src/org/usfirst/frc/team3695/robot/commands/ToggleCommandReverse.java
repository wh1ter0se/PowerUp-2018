package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

/** toggles the state of the clamp */
public class ToggleCommandReverse extends Command {

    boolean isFinished;
    public ToggleCommandReverse() {
        requires(Robot.SUB_DRIVE);
        isFinished = false;
    }

    protected void initialize() {}

    protected void execute() {
        Robot.SUB_DRIVE.toggleReversing();
        isFinished = true;
    }

    protected boolean isFinished() { return isFinished; }

    protected void end() {
        isFinished = false;
    }

    protected void interrupted() {
        end();
    }
}
