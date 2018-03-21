package org.usfirst.frc.team3695.robot.commands.toggle;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive;

/** Reverse the direction of all movement
 *  It was implemented to make backing the hook into where it needs to be easier */
public class ToggleCommandReverse extends Command {

    private boolean isFinished;
    public ToggleCommandReverse() {
        requires(Robot.SUB_DRIVE);
        isFinished = false;
    }

    protected void initialize() {}

    protected void execute() {
        SubsystemDrive.reversing = !SubsystemDrive.reversing;
        isFinished = true;
    }

    protected boolean isFinished() { return isFinished; }

    protected void end() {
        isFinished = false;
    }

    protected void interrupted() {
        end();
    }
}
