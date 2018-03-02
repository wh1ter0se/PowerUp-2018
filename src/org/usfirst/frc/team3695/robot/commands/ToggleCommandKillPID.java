package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;

/** Toggle PID */
public class ToggleCommandKillPID extends Command {

    public ToggleCommandKillPID() {
        requires(Robot.SUB_DRIVE);
    }

    protected void initialize() {
        Robot.SUB_DRIVE.pid.setPIDF(0,0,0,0);
    }

    protected void execute() { }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.SUB_DRIVE.pid.setPIDF(Util.getAndSetDouble("P", .5),
                Util.getAndSetDouble("I", 0),
                Util.getAndSetDouble("D", 0),
                Util.getAndSetDouble("F", 0));
    }

    protected void interrupted() {}
}
