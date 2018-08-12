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
import org.usfirst.frc.team3695.robot.enumeration.Mast;
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
		/// Reversing mode
			Button toggleReverse = new JoystickButton(DRIVER, Xbox.Y);
				toggleReverse.toggleWhenPressed(new ToggleCommandReverse());
		/// Low ramp mode
			Button toggleLowRamp = new JoystickButton(DRIVER, Xbox.X);
				toggleLowRamp.toggleWhenPressed(new ToggleCommandLowRamp());
		/// Docking mode
			Button toggleDock = new JoystickButton(DRIVER, Xbox.A);
				toggleDock.whenPressed(new ToggleCommandDock());
		/// Narrow mode
			Button toggleNarrow = new JoystickButton(DRIVER, Xbox.B);
				toggleNarrow.whileHeld(new ToggleCommandNarrow());
				
			SmartDashboard.putData("Disable Lower Screw", new ToggleCommandDisableLimitSwitch(Mast.SCREW_DOWN));
			SmartDashboard.putData("Disable Upper Screw", new ToggleCommandDisableLimitSwitch(Mast.SCREW_UP));
			SmartDashboard.putData("Disable Lower Pinion", new ToggleCommandDisableLimitSwitch(Mast.PINION_DOWN));
			SmartDashboard.putData("Disable Upper Pinion", new ToggleCommandDisableLimitSwitch(Mast.PINION_UP));
			
			for (Mast limit:Mast.values()) {
				SmartDashboard.putBoolean(limit.toBetterString() + " enabled", true);
			}
			
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
}