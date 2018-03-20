package org.usfirst.frc.team3695.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3695.robot.commands.button.ButtonCommandSpit;
import org.usfirst.frc.team3695.robot.commands.cyborg.CyborgCommandRotateDegrees;
import org.usfirst.frc.team3695.robot.commands.toggle.ToggleCommandReverse;
import org.usfirst.frc.team3695.robot.util.Util;
import org.usfirst.frc.team3695.robot.util.Xbox;

/** the Operator Interface setup */
public class OI {
	
	public static final Joystick DRIVER = new Joystick(0);
	public static final Joystick OPERATOR = new Joystick(1);
	
	/** assigns what every SmartDash and controller button does */
	public OI() {
		/// manipulator wheels
			Button spinIn = new JoystickButton(OPERATOR, Xbox.RB);
					spinIn.whileHeld(new org.usfirst.frc.team3695.robot.commands.button.ButtonCommandEat());
			Button spinOut = new JoystickButton(OPERATOR, Xbox.LB);
					spinOut.whileHeld(new ButtonCommandSpit());
		/// manipulator clamp
			Button toggleClamp = new JoystickButton(OPERATOR, Xbox.A);
				toggleClamp.toggleWhenActive(new org.usfirst.frc.team3695.robot.commands.toggle.ToggleCommandClamp());
		/// candy cane
			Button toggleHook = new JoystickButton(OPERATOR, Xbox.B);
				toggleHook.toggleWhenActive(new org.usfirst.frc.team3695.robot.commands.toggle.ToggleCommandHook());
		/// drop the mast
			Button dropIt = new JoystickButton(OPERATOR, Xbox.X);
				dropIt.toggleWhenPressed(new org.usfirst.frc.team3695.robot.commands.button.ButtonCommandHitTheDeck());
		/// Reversing mode
			Button toggleReverse = new JoystickButton(DRIVER, Xbox.Y);
				toggleReverse.toggleWhenPressed(new ToggleCommandReverse());
		/// Docking mode
			Button toggleDock = new JoystickButton(DRIVER, Xbox.X);
				toggleDock.toggleWhenPressed(new org.usfirst.frc.team3695.robot.commands.toggle.ToggleCommandDock());
		/// Narrow mode
			Button toggleNarrow = new JoystickButton(DRIVER, Xbox.B);
				toggleNarrow.whileHeld(new org.usfirst.frc.team3695.robot.commands.toggle.ToggleCommandNarrow());
		/// To Compress, or Not To Compress. It is now an option.
			SmartDashboard.putData("Disable Compressor", new org.usfirst.frc.team3695.robot.commands.toggle.ToggleCommandKillCompressor());
			

		/// PID
			SmartDashboard.putData("Kill PID", new org.usfirst.frc.team3695.robot.commands.toggle.ToggleCommandKillPID());
			SmartDashboard.putNumber("Right Encoder Position", 0);
			SmartDashboard.putNumber("Left Encoder Position", 0);
			
		/// limit switch displays
			SmartDashboard.putBoolean("Lower Screw", true);
	    	SmartDashboard.putBoolean("Upper Screw", false);
	    	SmartDashboard.putBoolean("Lower Pinion", true);
	    	SmartDashboard.putBoolean("Upper Pinion", false);
	    	
	    	DriverStation.reportWarning("OI IS INSTANTIATED", false);

	    /// Cyborg command testers
			SmartDashboard.putData("Drive Direct", new org.usfirst.frc.team3695.robot.commands.cyborg.CyborgCommandDriveDirect(Util.getAndSetDouble("Drive Direct Power", 0)));
			SmartDashboard.putData("Drive Distance", new org.usfirst.frc.team3695.robot.commands.cyborg.CyborgCommandDriveDistance(Util.getAndSetDouble("Drive Distance Inches", 0), (int) Util.getAndSetDouble("Drive Distance Timeout", 5000)));
			SmartDashboard.putData("Drive Until Error", new org.usfirst.frc.team3695.robot.commands.cyborg.CyborgCommandDriveUntilError(500,2,.25, 3));
			SmartDashboard.putData("Rotate Degree", new CyborgCommandRotateDegrees(Util.getAndSetDouble("Rotate Degrees", 0), (int) Util.getAndSetDouble("Rotate Timeout", 5000)));
	}
	
}