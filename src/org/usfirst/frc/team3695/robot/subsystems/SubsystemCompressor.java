package org.usfirst.frc.team3695.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SubsystemCompressor extends Subsystem {
	
	/**
	 * Initialize Default Compressor
	 */
    Compressor comp = new Compressor();
    
    public void initDefaultCommand() {}
    
    /**
     * @return whether or not compressor is currently running
     */
    public boolean isEnabled(){
		return comp.enabled();
    }
    
    /**
     * Enable/Disable the compressor
     * @param state - State to change to
     */
    public void setState(boolean state){
		if (state) comp.start(); else comp.stop();
    }
    
   /**
    * blindly change the state to !state
    */
    public void toggle(){
		setState(!isEnabled());
    }
}

