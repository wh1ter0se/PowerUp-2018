package org.usfirst.frc.team3695.robot;

import org.usfirst.frc.team3695.robot.commands.ButtonCommandClamp;
import org.usfirst.frc.team3695.robot.commands.ButtonCommandEat;
import org.usfirst.frc.team3695.robot.commands.ButtonCommandSpit;
import org.usfirst.frc.team3695.robot.enumeration.Direction;
import org.usfirst.frc.team3695.robot.util.Xbox;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** the output/input setup */
public class OI {
	
	public static final Joystick DRIVER = new Joystick(0);
	public static final Joystick OPERATOR = new Joystick(1);
	
	/** 
	 * assigns what every SmartDash and controller button does
	 * 
	 * ye() gets called at teleop enable, assigning button values to controller input
	 * still in ye(), below controller value assigns, place each SmartDash button
	 * */
	public OI() {
		/// manipulator wheels
			Button spinIn = new JoystickButton(OPERATOR, Xbox.LB);
				spinIn.whileHeld(new ButtonCommandEat());
			Button spinOut = new JoystickButton(OPERATOR, Xbox.RB);
				spinOut.whileHeld(new ButtonCommandSpit());
		/// manipulator clamp
			Button toggleClamp = new JoystickButton(OPERATOR, Xbox.RB);
				toggleClamp.toggleWhenActive(new ButtonCommandClamp());
	}
	
}