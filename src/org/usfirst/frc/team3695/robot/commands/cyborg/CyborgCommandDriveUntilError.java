package org.usfirst.frc.team3695.robot.commands.cyborg;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

@Deprecated //Use DriveByTimer
public class CyborgCommandDriveUntilError extends Command {

    private int range = 3000;
    private double posLeft;
    private double posRight;
    private int timeout = 500;
    private double speed = 0.1;
    private long start;

    {
        start = System.currentTimeMillis();
        posLeft = Robot.SUB_DRIVE.autoDrive.leftEncoderPos();
        posRight = Robot.SUB_DRIVE.autoDrive.rightEncoderPos();
    }

    public CyborgCommandDriveUntilError() {
    }

    public CyborgCommandDriveUntilError(int speed) {
        this.speed = speed;
    }

    public CyborgCommandDriveUntilError(int speed, int range){
        this.speed = speed;
        this.range = range;
    }

    protected void initialize() {}

    protected void execute() {
        Robot.SUB_DRIVE.autoDrive.setTalons(speed, speed);
    }

    protected boolean isFinished() {
        if (timeout + start < System.currentTimeMillis()){
            start = System.currentTimeMillis();
            return posLeft + range > Robot.SUB_DRIVE.autoDrive.leftEncoderPos() && posLeft - range < Robot.SUB_DRIVE.autoDrive.leftEncoderPos()
                    && posRight + range > Robot.SUB_DRIVE.autoDrive.rightEncoderPos() && posRight - range < Robot.SUB_DRIVE.autoDrive.rightEncoderPos();
        }

        return false;
    }

    protected void end() {

    }

}
