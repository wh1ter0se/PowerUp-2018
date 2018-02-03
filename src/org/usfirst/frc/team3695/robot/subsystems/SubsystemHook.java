package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.commands.ManualCommandHook;
import org.usfirst.frc.team3695.robot.util.Xbox;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/** VROOM VROOM */
public class SubsystemHook extends Subsystem {
	
	
	// private Solenoid raiseHook;
	// private Solenoid lowerHook;
	// private boolean open; // current State of arms; true = open, false = closed
	
	/** runs at robot boot */
    public void initDefaultCommand() {
    	setDefaultCommand(new ManualCommandHook());
    }
	
	/** gives birth to the CANTalons */
    public SubsystemHook(){
    	// raiseHook = new Solenoid(Constants.RAISE_HOOK);
		// lowerHook = new Solenoid(Constants.LOWER_HOOK);
		// open = false;
    }
    
    /**    
	    public void raiseHook(){
	    	raiseHook.set(true);
	    	lowerHook.set(false);
	    	open = true;
	    }
	    
	    public void lowerHook(){
	    	raiseHook.set(false);
	    	lowerHook.set(true);
	    	open = false;
	    }
	    
	    public void toggleHook(){
	    	if (open) lowerHook();	else raiseHook();
	    }
    */
           
    

}

