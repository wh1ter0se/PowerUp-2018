package org.usfirst.frc.team3695.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3695.robot.commands.button.ButtonCommandEat;
import org.usfirst.frc.team3695.robot.commands.button.ButtonCommandHitTheDeck;
import org.usfirst.frc.team3695.robot.commands.button.ButtonCommandSpit;
import org.usfirst.frc.team3695.robot.commands.toggle.*;
import org.usfirst.frc.team3695.robot.util.Xbox;

/** the Operator Interface setup */
public class OI {
	
	public static final Joystick DRIVER = new Joystick(0);
	public static final Joystick OPERATOR = new Joystick(1);

	/**
	 * Assigns some buttons on the Xbox conctrollers and places values on the SmartDashboard
	 * Check the manual commands for controlss that are not assigned here
	 */
	public OI() {
		instantiateDriver();
		instantiateOperator();

		/// To Compress, or Not To Compress. It is now an option.
			SmartDashboard.putData("Disable Compressor", new ToggleCommandKillCompressor());
			
			//From Brogan: I don't know when you added this, but I love it.
			SmartDashboard.putBoolean("Caleb is Illiterate", true);

		/// limit switch displays
			SmartDashboard.putBoolean("Lower Screw", true);
	    	SmartDashboard.putBoolean("Upper Screw", false);
	    	SmartDashboard.putBoolean("Lower Pinion", true);
	    	SmartDashboard.putBoolean("Upper Pinion", false);
	    	
	    	DriverStation.reportWarning("OI IS INSTANTIATED", false);
	}

	/**
	 * Control Scheme:
	 * A: None
	 * B: Toggle Narrowing mode (Forza drive)
	 * X: Toggle Low Ramp (Rocket league)
	 * Y: Toggle Reversing mode (Forzza drive)
	 * RT: Accelerate forwards
	 * LT: Accelerate backwards
	 * RB: None
	 * LB: None
	 * Left-Y: None
	 * Left-X: Turning
	 * Right-Y: None
	 * Right-X: None
	 */
	private static void instantiateDriver(){
		/// Reversing mode
		Button toggleReverse = new JoystickButton(DRIVER, Xbox.Y);
			toggleReverse.toggleWhenPressed(new ToggleCommandReverse());
		/// Docking mode
		Button toggleDock = new JoystickButton(DRIVER, Xbox.X);
			toggleDock.whenPressed(new ToggleCommandLowRamp());
		/// Narrow mode
		Button toggleNarrow = new JoystickButton(DRIVER, Xbox.B);
			toggleNarrow.whileHeld(new ToggleCommandNarrow());
	}

	/**
	 * Control Scheme:
	 * A: Toggle manipulator arms
	 * B: Raise/lower hook
	 * X: Lower the mast to lowest point (Needs fixing)
	 * Y: None
	 * RT: Raise screw and mast simultaneously
	 * LT: Lower screw and mast simultaneously
	 * RB: None
	 * LB: None
	 * Left-Y: Raise/lower screw
	 * Left-X: None
	 * Right-Y: Raise/lower pinion
	 * Right-X: None
	 */
	private static void instantiateOperator(){
		/// manipulator wheels
		Button spinIn = new JoystickButton(OPERATOR, Xbox.RB);
			spinIn.whileHeld(new ButtonCommandEat());
		Button spinOut = new JoystickButton(OPERATOR, Xbox.LB);
			spinOut.whileHeld(new ButtonCommandSpit());
		/// manipulator clamp
		Button toggleClamp = new JoystickButton(OPERATOR, Xbox.A);
			toggleClamp.toggleWhenActive(new ToggleCommandClamp());
		/// candy cane
		Button toggleHook = new JoystickButton(OPERATOR, Xbox.B);
			toggleHook.toggleWhenActive(new ToggleCommandHook());
		/// drop the mast
		Button dropIt = new JoystickButton(OPERATOR, Xbox.X);
			dropIt.toggleWhenPressed(new ButtonCommandHitTheDeck());
	}
}