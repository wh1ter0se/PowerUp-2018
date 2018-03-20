package org.usfirst.frc.team3695.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3695.robot.auto.CommandGroupAuto;
import org.usfirst.frc.team3695.robot.enumeration.Bot;
import org.usfirst.frc.team3695.robot.enumeration.Drivetrain;
import org.usfirst.frc.team3695.robot.enumeration.Goal;
import org.usfirst.frc.team3695.robot.enumeration.Position;
import org.usfirst.frc.team3695.robot.subsystems.*;

//    _____   _____   ____     ______           ______                   _                                        ____             _                   
//   |__  /  / ___/  / __ \   / ____/          / ____/  ____    _  __   (_)   ____ ___   __  __   _____          / __ \   _____   (_)   ____ ___   ___ 
//    /_ <  / __ \  / /_/ /  /___ \           / /_     / __ \  | |/_/  / /   / __ `__ \ / / / /  / ___/         / /_/ /  / ___/  / /   / __ `__ \ / _ \
//  ___/ / / /_/ /  \__, /  ____/ /          / __/    / /_/ / _>  <   / /   / / / / / // /_/ /  (__  )         / ____/  / /     / /   / / / / / //  __/
// /____/  \____/  /____/  /_____/          /_/       \____/ /_/|_|  /_/   /_/ /_/ /_/ \__,_/  /____/         /_/      /_/     /_/   /_/ /_/ /_/ \___/ 

/** the magic place where everything happens (where the sequence of events is controlled, top of the hierarchy) */
public class Robot extends IterativeRobot {
	// it... it's a bot
		public static Bot bot;

	// choosers
		private SendableChooser<Bot> botChooser;
		private SendableChooser<Goal> goalChooser;
		private SendableChooser<Goal> thirdPriorityChooser;
		private SendableChooser<Drivetrain> driveChooser;
		private SendableChooser<Position>  positionChooser;
		
	// subsystems
		public static SubsystemClamp SUB_CLAMP;
		public static SubsystemCompressor SUB_COMPRESSOR;
		public static SubsystemDrive SUB_DRIVE;
		public static SubsystemHook SUB_HOOK;
		public static SubsystemManipulator SUB_MANIPULATOR;
		public static SubsystemMast SUB_MAST;
		public static SubsystemVision SUB_VISION;

		public static OI oi;

	/// autonomous
		private CommandGroupAuto auto;

		
	/** runs when robot is turned on */
	public void robotInit() {
		/// instantiate bot chooser
		botChooser = new SendableChooser<>();
		botChooser.addDefault(Bot.TEUFELSKIND.toString(), Bot.TEUFELSKIND);
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
			SUB_HOOK = new SubsystemHook();
			SUB_MAST = new SubsystemMast();
			SUB_VISION = new SubsystemVision();

		/// instantiate operator interface
			oi = new OI();

			
			
		/// instantiate drivetrain chooser
				driveChooser = new SendableChooser<>();
				driveChooser.addDefault(Drivetrain.ROCKET_LEAGUE.toString(), Drivetrain.ROCKET_LEAGUE);
				for(int i = 1; i < Drivetrain.values().length; i++) { 
					driveChooser.addObject(Drivetrain.values()[i].toString(), Drivetrain.values()[i]); }
				SmartDashboard.putData("Drivetrain", driveChooser);
			
		/// instantiate position chooser
				positionChooser = new SendableChooser<>();
				positionChooser.addDefault(Position.CENTER.toString(), Position.CENTER);
					positionChooser.addObject(Position.LEFT.toString(), Position.LEFT);
					positionChooser.addObject(Position.RIGHT.toString(), Position.RIGHT);
				SmartDashboard.putData("Position", positionChooser);

		/// instantiate goal chooser
				goalChooser = new SendableChooser<>();
				goalChooser.addDefault(Goal.BEST_OPTION.toString(), Goal.BEST_OPTION);
				for(int i = 1; i < Goal.values().length; i++) { 
					goalChooser.addObject(Goal.values()[i - 1].toString(), Goal.values()[i - 1]); }
				SmartDashboard.putData("Goal", goalChooser);
			
		/// instantiate third priority chooser
				thirdPriorityChooser = new SendableChooser<>();
				thirdPriorityChooser.addDefault(Goal.SWITCH.toString(), Goal.SWITCH);
					thirdPriorityChooser.addObject(Goal.SCALE.toString(), Goal.SCALE);
					thirdPriorityChooser.addObject(Goal.ENEMY_SWITCH.toString(), Goal.ENEMY_SWITCH);
				SmartDashboard.putData("Third Priority", thirdPriorityChooser);
				
		/// instantiate bot chooser
				botChooser = new SendableChooser<>();
				botChooser.addDefault(Bot.TEUFELSKIND.toString(), Bot.TEUFELSKIND);
					botChooser.addObject(Bot.OOF.toString(), Bot.OOF); 
				SmartDashboard.putData("Bot", botChooser);
				
				
				
		/// instantiate cameras
			SUB_VISION.startScrewCameraThread();
			//Likely won't be used as it's unlikely to always work
//			SUB_VISION.startFrameCameraThread();

		SmartDashboard.putData("Sub_Clamp", SUB_CLAMP);
		SmartDashboard.putData("Sub_Compressor", SUB_COMPRESSOR);
		SmartDashboard.putData("Sub_Drive", SUB_DRIVE);
		SmartDashboard.putData("Sub_Hook", SUB_HOOK);
		SmartDashboard.putData("Sub_Manipulator", SUB_MANIPULATOR);
		SmartDashboard.putData("Sub_Mast", SUB_MAST);
		DriverStation.reportWarning("SUBSYSTEMS, CHOOSERS INSTANTIATED", false);
	}
	
	/** runs when robot gets disabled */
	public void disabledInit() { 
		DriverStation.reportWarning("TELEOP IS DISABLED", false);
		Scheduler.getInstance().removeAll();
	}

	/** runs at 50hz when bot is disabled */
	public void disabledPeriodic() {
		Scheduler.getInstance().run(); 
	}
	
	/** runs when autonomous start */
	public void autonomousInit() {
		DriverStation.reportWarning("AUTONOMOUS IS STARTING...", false);
		if(goalChooser.getSelected() != null) {
			auto = new CommandGroupAuto(positionChooser.getSelected(), goalChooser.getSelected(), thirdPriorityChooser.getSelected());
			auto.start(); 
		}
		
		setAllInverts();
	}

	/** runs at 50hz when in autonomous */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run(); 
		
		setAllInverts();
	}

	/** runs when teleop starts*/
	public void teleopInit() {
		DriverStation.reportWarning("TELEOP IS ENABLED", false);
		if (auto != null)
			auto.cancel(); 
		
		setAllInverts();
	}
	
	/** runs at ~50hz when in teleop mode */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		if (driveChooser.getSelected() != null) {
			SUB_DRIVE.setDrivetrain(driveChooser.getSelected());
		}
		
		setAllInverts();
	}

	/** runs at ~50hz when in test mode */
	public void testPeriodic() {}
	
	/** do you really need javadoc for this one */
	private void setAllInverts() {
		bot = (botChooser.getSelected() != null) ? botChooser.getSelected() : bot; // update motor inverts
			SUB_DRIVE.setInverts();
			SUB_MAST.setInverts();
			SUB_MANIPULATOR.setInverts();
	}
}
