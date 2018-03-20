package org.usfirst.frc.team3695.robot.commands.button;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

/**
 * Bring power cubes into the manipulator
 */
public class ButtonCommandEat extends Command {

    public ButtonCommandEat() {
        requires(Robot.SUB_MANIPULATOR);
    }

    protected void initialize() {
        Robot.SUB_MANIPULATOR.eat();
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
