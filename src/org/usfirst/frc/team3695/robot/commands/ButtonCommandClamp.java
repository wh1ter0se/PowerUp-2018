package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Direction;

import edu.wpi.first.wpilibj.command.Command;

/**
 * toggles the state of the clamp
 */
public class ButtonCommandClamp extends Command {
	
	Direction direction;
	
    public ButtonCommandClamp() {
        requires(Robot.SUB_CLAMP);
    }

    protected void initialize() {
    	//Robot.SUB_CLAMP.openArms();
    }

    protected void execute() {}

    protected boolean isFinished() { return false; }

    protected void end() {
    	///Robot.SUB_CLAMP.closeArms();
    }

    protected void interrupted() {
    	end();
    }
}
