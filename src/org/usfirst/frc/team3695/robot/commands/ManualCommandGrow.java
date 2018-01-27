package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import org.usfirst.frc.team3695.robot.OI;
import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/** move the mast */
public class ManualCommandGrow extends Command {

    DigitalInput lowerPinionLimit;
    DigitalInput upperPinionLimit;
    DigitalInput lowerScrewLimit;
    DigitalInput upperScrewLimit;

    public ManualCommandGrow() {
        requires(Robot.SUB_MAST);
        lowerPinionLimit = new DigitalInput(1);
        upperPinionLimit = new DigitalInput(2);
        lowerScrewLimit = new DigitalInput(3);
        upperScrewLimit = new DigitalInput(4);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.SUB_MAST.moveBySpeed(OI.OPERATOR);
    }

    protected boolean isFinished() {
        return lowerPinionLimit.get() ||
                upperPinionLimit.get() ||
                lowerScrewLimit.get() ||
                upperScrewLimit.get();
    }

    protected void end() {}

    protected void interrupted() {}
}
