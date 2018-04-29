package org.usfirst.frc.team3695.robot.commands.manual;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

/**
 * move the mast
 */
public class ManualCommandIlliteracy extends Command {


    public ManualCommandIlliteracy() {
        requires(Robot.SUB_CALEB);
        requires(Robot.SUB_DRIVE);
    }

    protected void initialize() {
    }

    protected void execute() {
        Robot.SUB_CALEB.lizardBrain();
    }

    /**
     * It never ends
     *
     * @return false
     */
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
