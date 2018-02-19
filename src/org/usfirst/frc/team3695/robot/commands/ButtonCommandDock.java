package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Position;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.util.Util;

/** toggles the state of the clamp */
public class ButtonCommandDock extends Command {

    public ButtonCommandDock() {
        requires(Robot.SUB_DRIVE);
    }

    protected void initialize() {
        Robot.SUB_DRIVE.docker(true, Util.getAndSetDouble("Dock", Constants.DOCK_SPEED));
    }

    protected void execute() {}

    protected boolean isFinished() { return false; }

    protected void end() {
        Robot.SUB_DRIVE.docker(false, Util.getAndSetDouble("Dock", Constants.DOCK_SPEED));
    }

    protected void interrupted() {
        end();
    }
}
