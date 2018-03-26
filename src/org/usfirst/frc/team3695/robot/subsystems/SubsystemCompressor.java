package org.usfirst.frc.team3695.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

/** Controller for the compressor */
public class SubsystemCompressor extends Subsystem {
	
	
    private Compressor comp = new Compressor(); // initialize compressor

    /**
     * Compressor is initialized by default so the constructor has nothing to do
     */
    public SubsystemCompressor() {}

    public void initDefaultCommand() {}

    /**
     * The state of the compressor
     * @return Whether the compressor is on or off
     */
    public boolean isEnabled(){
		return comp.enabled();
    }

    /**
     * Turns the compressor on and off
     * @param state The state to set the projector
     */
    public void setState(boolean state){
		if (state) comp.start(); else comp.stop();
    }

    /**
     * Toggle the compressor's state
     */
    public void toggle(){ //Method is never used. Look into deleting
		setState(!isEnabled());
    }
}

