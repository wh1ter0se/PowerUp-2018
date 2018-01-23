package org.usfirst.frc.team3695.robot;

import org.usfirst.frc.team3695.robot.commands.ButtonCommandClamp;
import org.usfirst.frc.team3695.robot.commands.ButtonCommandEat;
import org.usfirst.frc.team3695.robot.commands.ButtonCommandSpit;
import org.usfirst.frc.team3695.robot.enumeration.Direction;
import org.usfirst.frc.team3695.robot.util.Xbox;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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
	public static void ye() { // see footer for name explanation
		/// manipulator wheels
			Button spinIn = new JoystickButton(DRIVER, Xbox.LB);
				spinIn.whileHeld(new ButtonCommandEat());
			Button spinOut = new JoystickButton(DRIVER, Xbox.RB);
				spinOut.whileHeld(new ButtonCommandSpit());
		/// manipulator clamp
			Button toggleClamp = new JoystickButton(OPERATOR, Xbox.RB);
				toggleClamp.toggleWhenActive(new ButtonCommandClamp());
	}
}



/************************
 * [Colton and AJ discussing why OI was instantiated but never used in 2017's Robot.java]
 * 		(it's because all of the setup was in the constructor, not a method)
 * 
 * AJ    : Yeah, so in that case just turn the constructor into a static method.
 * Colton: what if I just moved the OI constructor code to a new static method in OI, then call that method
 * AJ    : And then call that
 * Colton: okay we are on the same page
 * AJ    : ye
 * Colton: I'll call it ye 
 * AJ    : -_-
 * Colton: I'm doing it
 * 
 * TL;DR: AJ doesn't type fast enough and accidentally said it shall be named ye... AND SO IT SHALL
 ***********************/





/// WPILIB comments
		////CREATING BUTTONS
		// One type of button is a joystick button which is any button on a
		//// joystick.
		// You create one by telling it which joystick it's on and which button
		// number it is.
		// Joystick stick = new Joystick(port);
		// Button button = new JoystickButton(stick, buttonNumber);
		
		// There are a few additional built in buttons you can use. Additionally,
		// by subclassing Button you can create custom triggers and bind those to
		// commands the same as any other Button.
		
		//// TRIGGERING COMMANDS WITH BUTTONS
		// Once you have a button, it's trivial to bind it to a button in one of
		// three ways:
		
		// Start the command when the button is pressed and let it run the command
		// until it is finished as determined by it's isFinished method.
		// button.whenPressed(new ExampleCommand());
		
		// Run the command while the button is being held down and interrupt it once
		// the button is released.
		// button.whileHeld(new ExampleCommand());
		
		// Start the command when the button is released and let it run the command
		// until it is finished as determined by it's isFinished method.
		// button.whenReleased(new ExampleCommand());