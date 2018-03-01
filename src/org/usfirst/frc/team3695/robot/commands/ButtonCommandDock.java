package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

/** toggles the state of the clamp */
public class ButtonCommandDock extends Command {

    public ButtonCommandDock() {
        requires(Robot.SUB_DRIVE);
    }

    protected void initialize() {
        Robot.SUB_DRIVE.docking = true;
        Robot.SUB_DRIVE.dockInhibitor = 0.5d;
    }

    protected void execute() {}

    protected boolean isFinished() { return false; }

    protected void end() {
        Robot.SUB_DRIVE.docking = false;
        Robot.SUB_DRIVE.dockInhibitor = 1;
    }

    protected void interrupted() {
        end();
    }
}
