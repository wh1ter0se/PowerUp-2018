package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

/** toggles the state of the clamp */
public class ButtonCommandDock extends Command {

    public ButtonCommandDock() {
        requires(Robot.SUB_DRIVE);
    }

    protected void initialize() {
        Robot.SUB_DRIVE.isDocking(true, 0.5d);
    }

    protected void execute() {}

    protected boolean isFinished() { return false; }

    protected void end() {
        Robot.SUB_DRIVE.isDocking(false, 0.5d);
    }

    protected void interrupted() {
        end();
    }
}
