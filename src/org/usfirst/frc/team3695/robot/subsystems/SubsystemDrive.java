package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.commands.ManualCommandDrive;
import org.usfirst.frc.team3695.robot.util.Xbox;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/** VROOM VROOM */
public class SubsystemDrive extends Subsystem {
	
	
	
	private TalonSRX left1;
	private TalonSRX left2;
    private TalonSRX right1;
    private TalonSRX right2;

	
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
    	//Master Talons
	    	left1 = new TalonSRX(Constants.LEFT_MASTER);
	    	right1 = new TalonSRX(Constants.RIGHT_MASTER);
    	
    	//Slave Talons
	    	left2 = new TalonSRX(Constants.LEFT_SLAVE);
	    	right2 = new TalonSRX(Constants.RIGHT_SLAVE);
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
    private void voltage(TalonSRX talon) {
    	// talon.configNominalOutputVoltage(0f, 0f);
    	// talon.configPeakOutputVoltage(12.0f, -12.0f);
    	talon.enableCurrentLimit(true);
    	//talon.setCurrentLimit(30);
    }
    
    

}

