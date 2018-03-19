package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Commands the gear flaps
 */
public class ButtonCommandSpit extends Command {

    public ButtonCommandSpit() {
        requires(Robot.SUB_MANIPULATOR);
    }

    protected void initialize() {
        Robot.SUB_MANIPULATOR.spit();
    }

    protected void execute() {}

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.SUB_MANIPULATOR.stopSpinning();
    }

    protected void interrupted() {
        end();
    }
}
