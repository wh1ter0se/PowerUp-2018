package org.usfirst.frc.team3695.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3695.robot.Constants;

/**
 * Controls the hook, specifically if it's raised or lowered
 */
public class SubsystemHook extends Subsystem {


    private Solenoid raiseHook;
    private Solenoid lowerHook;
    private boolean raised; // current State of arms; true = open, false = closed

    /**
     * runs at robot boot
     */
    public void initDefaultCommand() {
    }

    /**
     * Instantiates the solenoids and sets the default state to lowered
     */
    public SubsystemHook() {
        raiseHook = new Solenoid(Constants.RAISE_HOOK);
        lowerHook = new Solenoid(Constants.LOWER_HOOK);
        raised = false;
    }

    /**
     * Raises the hook
     */
    public void raiseHook() {
        lowerHook.set(false);
        raiseHook.set(true);
        raised = true;
    }

    /**
     * Lowers the hook
     */
    public void lowerHook() {
        raiseHook.set(false);
        lowerHook.set(true);
        raised = false;
    }

    /**
     * Toggles the state of the hook
     * If it's raised, then it will lower
     * If it's lowered, then it will raise
     */
    public void toggleHook() {
        if (raised) lowerHook();
        else raiseHook();
    }


}

