package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Mast;
import org.usfirst.frc.team3695.robot.enumeration.Position;

public class CyborgCommandRaiseToPosition extends Command {

    private Mast position;

    public CyborgCommandRaiseToPosition(Mast position) {
        requires(Robot.SUB_MAST);
        this.position = position;
    }

    protected boolean isFinished() { return false; }

    protected void initialize() {}

    protected void execute() {
        switch (position){
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
    }

    protected void end() {}

    protected void interrupted() {
        end();
    }
}
