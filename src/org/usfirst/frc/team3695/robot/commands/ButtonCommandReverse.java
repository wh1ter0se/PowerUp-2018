package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

/** toggles the state of the clamp */
public class ButtonCommandReverse extends Command {

    boolean isFinished;
    public ButtonCommandReverse() {
        requires(Robot.SUB_DRIVE);
        isFinished = false;
    }

    protected void initialize() {}

    protected void execute() {
        Robot.SUB_DRIVE.toggleReverse();
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
