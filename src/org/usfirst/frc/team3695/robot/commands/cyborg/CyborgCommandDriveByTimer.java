package org.usfirst.frc.team3695.robot.commands.cyborg;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

public class CyborgCommandDriveByTimer extends Command {

    private double speed;
    private long start;
    private int timems;

    public CyborgCommandDriveByTimer(double speed, int timems) {
        this.speed = speed;
        this.timems = timems;
    }

    protected void initialize() {
        start = System.currentTimeMillis();
    }

    protected void execute() {
        Robot.SUB_DRIVE.autoDrive.setTalons(speed, speed);
    }

    protected boolean isFinished() {
        return start + timems < System.currentTimeMillis();
    }

    protected void end() {

    }

}
