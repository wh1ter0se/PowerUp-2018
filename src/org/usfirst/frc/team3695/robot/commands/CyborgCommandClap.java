package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;

public class CyborgCommandClap extends Command {

    long runTime;
    long startTime;
    long claptime;
    public CyborgCommandClap(long runtime, long claptime){
        requires(Robot.SUB_CLAMP);
        this.runTime = runtime;
        this.claptime = claptime;
    }

    protected void execute(){
        if (System.currentTimeMillis() % claptime == 0){
            Robot.SUB_CLAMP.openArms();
        } else if (System.currentTimeMillis() % (claptime/2) == 0){
            Robot.SUB_CLAMP.closeArms();
        }
    }

    protected void initialize() {
        startTime = System.currentTimeMillis();
    }

    protected boolean isFinished() {
        return runTime + startTime < System.currentTimeMillis();
    }
}
