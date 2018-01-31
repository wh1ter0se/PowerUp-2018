package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

/**
 * Pls get digital input
 */
public class TestCommandDigitalInput extends Command {

    public TestCommandDigitalInput() {
        requires(Robot.SUB_MAST);
    }

    protected void initialize() {}

    protected void execute() {
        SmartDashboard.putBoolean("Digital Input", Robot.SUB_MAST.getDigitalInput());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {
    	end();
    }
}
