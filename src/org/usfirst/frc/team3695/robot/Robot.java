
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
			DriverStation.reportWarning("ROBOT STARTED; GOOD LUCK", false);
		/// instantiate subsystems
//			SUB_ARDUINO = new SubsystemArduino();
			SUB_MANIPULATOR = new SubsystemManipulator();
			SUB_CLAMP = new SubsystemClamp();
			SUB_COMPRESSOR = new SubsystemCompressor();
			SUB_DRIVE = new SubsystemDrive();
			SUB_HOOK = new SubsystemHook();
			SUB_MAST = new SubsystemMast();
			vision = new Vision();

		/// instantiate operator interface
			oi = new OI();

		//Set PID
		//plz no kill me colton
		//Robot.SUB_DRIVE.setPIDF(0.2,0,0, 0.2);


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
		Scheduler.getInstance().run();
		if (driveChooser.getSelected() != null) {
			SUB_DRIVE.setDrivetrain(driveChooser.getSelected());
		}
		SUB_MANIPULATOR.spinByJoystick(OI.OPERATOR); // THIS IS A PATCH. DO AS I SAY, NOT AS I DO
	}

	
	/** runs at ~50hz when in test mode */
	public void testPeriodic() {
		LiveWindow.run(); 
	}
}

