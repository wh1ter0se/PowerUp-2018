package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Mast;
import org.usfirst.frc.team3695.robot.enumeration.Position;

public class CyborgCommandGrow extends Command {

    private Mast position;

    boolean isFinished;
    public CyborgCommandGrow(Mast position) {
        requires(Robot.SUB_MAST);
        this.position = position;
        isFinished = false;
    }

    protected boolean isFinished() {
        return isFinished;
    }

    protected void initialize() {
        switch (position) {
            case PINION_UP:
                Robot.SUB_MAST.adjustPinion(Position.UP);
                break;
            case PINION_DOWN:
                Robot.SUB_MAST.adjustPinion(Position.DOWN);
                break;
            case SCREW_UP:
                Robot.SUB_MAST.adjustScrew(Position.UP);
                break;
            case SCREW_DOWN:
                Robot.SUB_MAST.adjustScrew(Position.DOWN);
                break;
        }
        isFinished = true;
    }

    protected void execute() {}

    protected void end() {
        isFinished = false;
    }

    protected void interrupted() {
        end();
    }
}
