package org.usfirst.frc.team3695.robot.commands.toggle;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;

/**
 * Toggle the bot in and out of docking mode
 *  Docking mode limits the maximum speed of the robot for more sensitive movements
 * */
public class ToggleCommandDock extends Command {

    private boolean isFinished;

    public ToggleCommandDock() {
        requires(Robot.SUB_DRIVE);
        isFinished = false;
    }

    protected void initialize() {
        Robot.SUB_DRIVE.toggleDocking(Util.getAndSetDouble("Docking Inhibitor", 0.5d));
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
