package org.usfirst.frc.team3695.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

/** it's a compressor */
public class SubsystemCompressor extends Subsystem {
	
	
    Compressor comp = new Compressor(); // initialize compressor
    
    public void initDefaultCommand() {}
    
    /** return compressor state */
    public boolean isEnabled(){
		return comp.enabled();
    }
    
    /** enable/disable compressor */
    public void setState(boolean state){
		if (state) comp.start(); else comp.stop();
    }
    
   /** blindly change the state to !state */
    public void toggle(){
		setState(!isEnabled());
    }
}

