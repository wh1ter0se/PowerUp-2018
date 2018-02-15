package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Position;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive;

import org.usfirst.frc.team3695.robot.util.Util;

import edu.wpi.first.wpilibj.command.Command;

public class CyborgCommandDriveUntilError extends Command {
    public static final long ERROR_TIME = 500;
    public static final int TARGET_ERROR = 500;

    private final Position position;
    private long time = 0;

    public CyborgCommandDriveUntilError(Position position) {
        requires(Robot.SUB_DRIVE);
        this.position = position;
    }

    protected void initialize() {
        time = System.currentTimeMillis() + ERROR_TIME;
    }

    protected void execute() {
        double speed = SubsystemDrive.ips2rpm(Util.getAndSetDouble("SPEED ERROR: Forward", 20.0));
        if(position == Position.BACKWARD) speed *= -1;
        Robot.SUB_DRIVE.driveDirect(speed, speed);
    }

    protected boolean isFinished() {
        if(Math.abs(Robot.SUB_DRIVE.getError()) < TARGET_ERROR) {
            time = System.currentTimeMillis() + ERROR_TIME;
        }
        return time < System.currentTimeMillis();
    }

    protected void end() {
        Robot.SUB_DRIVE.driveDirect(0, 0);
    }

    protected void interrupted() {
        end();
    }
}