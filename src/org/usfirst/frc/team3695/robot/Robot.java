
package org.usfirst.frc.team3695.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3695.robot.auto.CommandGroupAuto;
import org.usfirst.frc.team3695.robot.enumeration.Bot;
import org.usfirst.frc.team3695.robot.enumeration.Drivetrain;
import org.usfirst.frc.team3695.robot.enumeration.Goal;
import org.usfirst.frc.team3695.robot.enumeration.Position;
import org.usfirst.frc.team3695.robot.subsystems.*;
import org.usfirst.frc.team3695.robot.util.Util;

//    _____   _____   ____     ______           ______                   _                                        ____             _                   
//   |__  /  / ___/  / __ \   / ____/          / ____/  ____    _  __   (_)   ____ ___   __  __   _____          / __ \   _____   (_)   ____ ___   ___ 
//    /_ <  / __ \  / /_/ /  /___ \           / /_     / __ \  | |/_/  / /   / __ `__ \ / / / /  / ___/         / /_/ /  / ___/  / /   / __ `__ \ / _ \
//  ___/ / / /_/ /  \__, /  ____/ /          / __/    / /_/ / _>  <   / /   / / / / / // /_/ /  (__  )         / ____/  / /     / /   / / / / / //  __/
// /____/  \____/  /____/  /_____/          /_/       \____/ /_/|_|  /_/   /_/ /_/ /_/ \__,_/  /____/         /_/      /_/     /_/   /_/ /_/ /_/ \___/ 

/** the magic place where everything happens (where the sequence of events is controlled, top of the hierarchy) */
public class Robot extends IterativeRobot {

		public static Bot bot;
	
	/// choosers
		SendableChooser<Bot> botChooser;
		SendableChooser<Goal> goalChooser;
		SendableChooser<Drivetrain> driveChooser;
		SendableChooser<Position>  positionChooser;
		// add choosers as needed, these put drop down options in the smart dash
		
		
	/// subsystems
//		public static SubsystemArduino SUB_ARDUINO;
		public static SubsystemClamp SUB_CLAMP;
		public static SubsystemCompressor SUB_COMPRESSOR;
		public static SubsystemDrive SUB_DRIVE;
		public static SubsystemHook SUB_HOOK;
		public static SubsystemManipulator SUB_MANIPULATOR;
		public static SubsystemMast SUB_MAST;

		public static OI oi;
		public static Vision vision;

		
	/// autonomous
		private CommandGroupAuto auto;

	/** runs when robot is turned on */
	public void robotInit() {
		/// instantiate bot chooser
		botChooser = new SendableChooser<>();
		botChooser.addDefault(Bot.SWISS.toString(), Bot.SWISS);
		botChooser.addObject(Bot.OOF.toString(), Bot.OOF);
		SmartDashboard.putData("Bot", botChooser);

		if (botChooser.getSelected() != null){
			bot = botChooser.getSelected();
		} else {
			bot = Bot.OOF;
		}

			DriverStation.reportWarning("ROBOT STARTED; GOOD LUCK", false);
		/// instantiate subsystems
//			SUB_ARDUINO = new SubsystemArduino();
			SUB_MANIPULATOR = new SubsystemManipulator();
			SUB_CLAMP = new SubsystemClamp();
			SUB_COMPRESSOR = new SubsystemCompressor();
			SUB_DRIVE = new SubsystemDrive();
			
			SUB_DRIVE.setPIDF(.5, 0, 0, 0);
			
			SUB_HOOK = new SubsystemHook();
			SUB_MAST = new SubsystemMast();
			vision = new Vision();

		/// instantiate operator interface
			oi = new OI();

		/// instantiate drivetrain chooser
			driveChooser = new SendableChooser<>();
			driveChooser.addDefault(Drivetrain.ROCKET_LEAGUE.toString(), Drivetrain.ROCKET_LEAGUE); // set default to RL drive
			for(int i = 1; i < Drivetrain.values().length; i++) { 
				driveChooser.addObject(Drivetrain.values()[i].toString(), Drivetrain.values()[i]); } // add each drivetrain enum value to chooser
			SmartDashboard.putData("Drivetrain", driveChooser); //display the chooser on the dash
			
		/// instantiate position chooser
			positionChooser = new SendableChooser<>();
			positionChooser.addDefault(Position.CENTER.toString(), Position.CENTER); // set default to nothing
			for(int i = 1; i < Position.values().length; i++) { 
				positionChooser.addObject(Position.values()[i].toString(), Position.values()[i]); } // add each autonomous enum value to chooser
			SmartDashboard.putData("Position", positionChooser); //display the chooser on the dash

		/// instantiate goal chooser
			goalChooser = new SendableChooser<>();
			goalChooser.addDefault(Goal.NOTHING.toString(), Goal.NOTHING); // set default to nothing
			for(int i = 1; i < Goal.values().length; i++) { 
				goalChooser.addObject(Goal.values()[i].toString(), Goal.values()[i]); } // add each autonomous enum value to chooser
			SmartDashboard.putData("Goal", goalChooser); //display the chooser on the dash
			
		/// instantiate bot chooser
			botChooser = new SendableChooser<>();
			botChooser.addDefault(Bot.SWISS.toString(), Bot.SWISS);
				botChooser.addObject(Bot.OOF.toString(), Bot.OOF); 
			SmartDashboard.putData("Bot", botChooser);
			
		/// instantiate cameras
			vision.startScrewCameraThread();
			vision.startFrameCameraThread();

		SmartDashboard.putData("Sub_Drive", SUB_DRIVE);
		DriverStation.reportWarning("SUBSYSTEMS, CHOOSERS INSTANTIATED", false);
	}

	
	/** runs when robot gets disabled */
	public void disabledInit() { 
		DriverStation.reportWarning("TELEOP IS DISABLED", false);
	}

	
	/** runs at 50hz when bot is disabled */
	public void disabledPeriodic() {
		Scheduler.getInstance().run(); 
	}

	
	/** runs when autonomous start */
	public void autonomousInit() {
		DriverStation.reportWarning("AUTONOMOUS IS STARTING...", false);
		if(goalChooser.getSelected() != null) {
			auto = new CommandGroupAuto(positionChooser.getSelected(), goalChooser.getSelected());
			auto.start(); 
		} 
	}

	/** runs at 50hz when in autonomous */
	public void autonomousPeriodic() {
		Robot.SUB_DRIVE.setPIDF(Util.getAndSetDouble("P", .5),
								Util.getAndSetDouble("I", 0),
								Util.getAndSetDouble("D", 0),
								Util.getAndSetDouble("F", 0));
		
		Scheduler.getInstance().run(); 
		bot = botChooser.getSelected(); // update motor inverts
	}

	
	/** runs when teleop starts*/
	public void teleopInit() {
		DriverStation.reportWarning("TELEOP IS ENABLED", false);
		if (auto != null)
			auto.cancel(); 
	}

	
	/** runs at ~50hz when in teleop mode */
	public void teleopPeriodic() {
		Robot.SUB_DRIVE.setPIDF(Util.getAndSetDouble("P", .5),
								Util.getAndSetDouble("I", 0),
								Util.getAndSetDouble("D", 0),
								Util.getAndSetDouble("F", 0));
		
		Scheduler.getInstance().run();
		if (driveChooser.getSelected() != null) {
			SUB_DRIVE.setDrivetrain(driveChooser.getSelected());
		}
		if (botChooser.getSelected() != null){
			bot = botChooser.getSelected();
		}
	}

	
	/** runs at ~50hz when in test mode */
	public void testPeriodic() {
		LiveWindow.run(); 
	}
}

