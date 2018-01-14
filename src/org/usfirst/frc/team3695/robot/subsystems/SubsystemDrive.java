package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.commands.ManualCommandDrive;
import org.usfirst.frc.team3695.robot.util.Xbox;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/** VROOM VROOM */
public class SubsystemDrive extends Subsystem {
	
	/// drift variables
	public static int     OFFSET,        // the degrees the bot is tilted inward
	                      METADIRECTION; // on button down, this resets to 0; the angle relative to that initial angle (changes on physical rotation)
	                     			 	 // pretty much the cumulative angle since button down, minus offset
	public static double  ROT_SPEED,     // the speed of rotation (min 0, max 10.00)
						  ROT_RADIUS;    // turn radius in inches
	public static boolean IN_DRIFT,		 // if the bot is currently in a drift
						  DRIFT_IS_CW;   // if the drift is turning right
	
	// instantiate the CANTalons here
		// EX: private CANTalon left1;

	
	/** runs at robot boot */
    public void initDefaultCommand() {
    	setDefaultCommand(new ManualCommandDrive()); }
    
    
    /** converts RPM to inches per second */
    public static final double rpm2ips(double rpm) {
    	return rpm / 60.0 * Constants.WHEEL_DIAMETER * Math.PI; }
    
    
    /** converts an inches per second number to RPM */
    public static final double ips2rpm(double ips) {
    	return ips * 60.0 / Constants.WHEEL_DIAMETER / Math.PI; }
    
    
    /** converts rotations to distance traveled in inches */
    public static final double rot2in(double rot) {
    	return rot * Constants.WHEEL_DIAMETER * Math.PI; }
    
    
    /** converts distance traveled in inches to rotations */
    public static final double in2rot(double in) {
    	return in / Constants.WHEEL_DIAMETER / Math.PI; }
	
	/** gives birth to the CANTalons */
    public SubsystemDrive(){
    	// assign initial values to CANTalons
    		// EX: left1 = new CANTalon(Constants.LEFT_MOTOR);
    	// call voltage for each instantiated CANTalon
    		// EX: voltage(left1);
    	// train each CANTalon
    		// master EX: left1.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
    		//			  left1.setEncPosition(0);
    		//			  left1.reverseSensor(false);
    		// slave EX:  left2.changeControlMode(CANTalon.TalonControlMode.Follower);
    		//			  left2.set(left1.getDeviceID());
    }
    
    public void driveSmoothTank(Joystick joy) {
    	
    }
    
    /** configures the voltage of each CANTalon */
    private void voltage(CANTalon talon) {
    	// talon.configNominalOutputVoltage(0f, 0f);
    	// talon.configPeakOutputVoltage(12.0f, -12.0f);
    	talon.EnableCurrentLimit(true);
    	talon.setCurrentLimit(30);
    }
    
    

}

