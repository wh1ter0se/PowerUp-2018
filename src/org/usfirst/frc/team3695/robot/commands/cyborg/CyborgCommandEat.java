package org.usfirst.frc.team3695.robot.commands.cyborg;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

/**
 * Spin the manipulator wheels inwards. Used for picking a cube up in 2 cube auto
 * (if we ever do 2 cube auto...)
 */
public class CyborgCommandEat extends Command {

    private boolean start;
    private long timeout;
    private long starttime;

    public CyborgCommandEat(boolean start, long timeout) {
        requires(Robot.SUB_MANIPULATOR);
        requires(Robot.SUB_CLAMP);
        this.start = start;
        this.timeout = timeout;
        this.starttime = System.currentTimeMillis();
    }

    @Override
    protected void initialize() {
        if (start) {
            Robot.SUB_CLAMP.openArms();
        } else {
            Robot.SUB_CLAMP.closeArms();
        }
    }

    @Override
    protected void execute() {
//        if (start) {
//            Robot.SUB_CLAMP.openArms();
//            Robot.SUB_MANIPULATOR.eat();
//        } else {
//            Robot.SUB_CLAMP.closeArms();
//            Robot.SUB_MANIPULATOR.eat();
//        }
        Robot.SUB_MANIPULATOR.eat();

    }

    @Override
    protected boolean isFinished() {
        return starttime + timeout < System.currentTimeMillis();
    }
}
