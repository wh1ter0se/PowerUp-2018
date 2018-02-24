package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

public class CyborgCommandSpit extends Command {

    long runTime;
    long startTime;
    public CyborgCommandSpit(long runTime) {
        requires(Robot.SUB_MANIPULATOR);
        this.runTime = runTime;
    }

    protected void initialize() {}

    protected void execute() {
        startTime = System.currentTimeMillis();
        while (startTime + runTime >= System.currentTimeMillis()){
            Robot.SUB_MANIPULATOR.spit();
        }
    }

    protected boolean isFinished() { return false; }

    protected void end() {}

    protected void interrupted() {
        end();
    }
}
