
package org.usfirst.frc.team3695.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3695.robot.auto.CommandGroupAuto;
import org.usfirst.frc.team3695.robot.enumeration.Autonomous;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemClamp;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemManipulator;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemMast;

/** the magic place where everything happens (where the sequence of events is controlled, top of the hierarchy) */
public class Robot extends IterativeRobot {

	/// choosers
		SendableChooser<Autonomous> autoChooser;
		// add choosers as needed, these put drop down options in the smart dash
		
		
	/// subsystems
		public static SubsystemClamp SUB_CLAMP;
		public static SubsystemDrive SUB_DRIVE;
		public static SubsystemManipulator SUB_MANIPULATOR;
		public static SubsystemMast SUB_MAST;
		
		public static I2C i2c;
		public static OI oi;
		public static Vision vision;

		
	/// autonomous
		private CommandGroupAuto auto;
		
		
		
		
	/** runs when robot is turned on */
	public void robotInit() {
		
			
		/// instantiate subsystems
			SUB_CLAMP = new SubsystemClamp();
			SUB_DRIVE = new SubsystemDrive();
			SUB_MANIPULATOR = new SubsystemManipulator();
			SUB_MAST = new SubsystemMast();
			vision = new Vision();
			i2c = new I2C(I2C.Port.kOnboard, Constants.I2C_DEVICE_ADDRESS);
		/// instantiate operator interface
			OI.ye();
		
		/// instantiate autonomous chooser
			autoChooser = new SendableChooser<>();
			autoChooser.addDefault(Autonomous.NOTHING.toString(), Autonomous.NOTHING); // set default to nothing
			for(int i = 1; i < Autonomous.values().length; i++) { 
				autoChooser.addObject(Autonomous.values()[i].toString(), Autonomous.values()[i]); } // add each autonomous enum value to chooser
			SmartDashboard.putData("Auto Mode", autoChooser); //display the chooser on the dash

		/// instantiate cameras
			// vision.startCameraThread();
	}

	
	/** runs when robot gets disabled */
	public void disabledInit() { }

	
	/** runs at 50hz when bot is disabled */
	public void disabledPeriodic() {
		Scheduler.getInstance().run(); 
	}

	
	/** runs when autonomous start */
	public void autonomousInit() {
		if(autoChooser.getSelected() != null) {
			auto = new CommandGroupAuto(autoChooser.getSelected());
			auto.start(); 
		} 
	}

	
	/** runs at 50hz when in autonomous */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run(); 
	}

	
	/** runs when teleop starts*/
	public void teleopInit() {
		if (auto != null)
			auto.cancel(); 
	}

	
	/** runs at ~50hz when in teleop mode */
	public void teleopPeriodic() {
		Scheduler.getInstance().run(); 
	}

	
	/** runs at ~50hz when in test mode */
	public void testPeriodic() {
		LiveWindow.run(); 
	}
}

