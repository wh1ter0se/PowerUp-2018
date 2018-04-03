package org.usfirst.frc.team3695.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3695.robot.commands.manual.ManualCommandIlliteracy;

/**
 * This is the final form of the Robot.
 */
public class SubsystemCaleb extends Subsystem {

    /**
     * Properly construct caleb
     */
    protected void initDefaultCommand() {
        setDefaultCommand(new ManualCommandIlliteracy());
    }

    /**
     * This does nothing. Trust me on this.
     * > no
     */
    public SubsystemCaleb(){}

    
}