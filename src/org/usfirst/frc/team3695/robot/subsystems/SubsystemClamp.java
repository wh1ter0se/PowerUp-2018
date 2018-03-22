package org.usfirst.frc.team3695.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3695.robot.Constants;

/**
 * Controls the clamp on the arms of the manipulator
 */
public class SubsystemClamp extends Subsystem {
	private Solenoid openArms;
	private Solenoid closeArms;
	private boolean open; // current State of arms; true = open, false = closed

	/**
	 * Instantiates the solenoids and sets the default state to closed
	 */
	public SubsystemClamp(){
		openArms = new Solenoid(Constants.OPEN_ARMS);
		closeArms = new Solenoid(Constants.CLOSE_ARMS);
		closeArms();
	}

    public void initDefaultCommand() {}

	/**
	 * Opens the manipulator arms
	 */
	public void openArms(){
    	openArms.set(true);
    	closeArms.set(false);
    	open = true;
    }

	/**
	 * Closes the manipulator arms
	 */
	public void closeArms(){
    	openArms.set(false);
    	closeArms.set(true);
    	open = false;
    }

	/**
	 * Toggle the state of the manipulator
	 * If opened, then it will close
	 * If closed, then it will open
	 */
	public void toggleArms(){ //Method is never used. Look into deleting
    	if (open) closeArms();	else openArms();
    }
}

