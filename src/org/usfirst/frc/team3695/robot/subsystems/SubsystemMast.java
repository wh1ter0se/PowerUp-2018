package org.usfirst.frc.team3695.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.commands.manual.ManualCommandGrow;
import org.usfirst.frc.team3695.robot.enumeration.Bot;
import org.usfirst.frc.team3695.robot.util.Xbox;

/** Control both the screw and pinion
 * 	Raises each of them up and down independently of each other
 */
public class SubsystemMast extends Subsystem {
	
	
	public TalonSRX leftPinion;
	public TalonSRX rightPinion;
	public TalonSRX screw;
	
	public DigitalInput lowerPinionLimit;
	public DigitalInput upperPinionLimit;
	public DigitalInput lowerScrewLimit;
	public DigitalInput upperScrewLimit;

	
	/** runs at robot boot */
    public void initDefaultCommand() {
    	setDefaultCommand(new ManualCommandGrow()); }

	/**
	 * Initialize the talons and limit switches
	 * Also limits the voltage of all talons
	 */
    public SubsystemMast(){
    	lowerPinionLimit = new DigitalInput(3);
        upperPinionLimit = new DigitalInput(7);
        lowerScrewLimit  = new DigitalInput(5);
        upperScrewLimit  = new DigitalInput(4);
    	
    	leftPinion = new TalonSRX(Constants.LEFT_PINION_MOTOR);
    	rightPinion = new TalonSRX(Constants.RIGHT_PINION_MOTOR);
    	screw = new TalonSRX(Constants.SCREW_MOTOR);

    	voltage(leftPinion);
   		voltage(rightPinion);
   		voltage(screw);
    }

	/**
	 * Set all inverts for the talons based on the bot being used
	 */
	public void setInverts() {
        leftPinion.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.LEFT_PINION_MOTOR_INVERT : Constants.TEUFELSKIND.LEFT_PINION_MOTOR_INVERT);
        rightPinion.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.RIGHT_PINION_MOTOR_INVERT : Constants.TEUFELSKIND.RIGHT_PINION_MOTOR_INVERT);
        screw.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.SCREW_MOTOR_INVERT : Constants.TEUFELSKIND.SCREW_MOTOR_INVERT);
    }

    @Deprecated
   	// apply pinion motor invert
   	public static double leftPinionate(double left) {
   		Boolean invert = Robot.bot == Bot.OOF ? Constants.OOF.LEFT_PINION_MOTOR_INVERT : Constants.TEUFELSKIND.LEFT_PINION_MOTOR_INVERT;
   		return left * (invert ? -1.0 : 1.0);
   	}
   	
   	@Deprecated
   	// apply screw motor invert
   	public static double rightPinionate(double right) {
   		Boolean invert = Robot.bot == Bot.OOF ? Constants.OOF.RIGHT_PINION_MOTOR_INVERT : Constants.TEUFELSKIND.RIGHT_PINION_MOTOR_INVERT;
   		return right * (invert ? -1.0 : 1.0);
   	}
   	
   	@Deprecated
   	public static double screwify(double screw) {
   		Boolean invert = Robot.bot == Bot.OOF ? Constants.OOF.SCREW_MOTOR_INVERT : Constants.TEUFELSKIND.SCREW_MOTOR_INVERT;
   		return screw * (invert ? -1.0 : 1.0);
   	}

	/**
	 * Manually controls the screw and pinion
	 * Screw controls: Left joystick up to raise and down to lower
	 * Pinion controls: Right joystick up to raise and down to lower
	 * Simultaneous: Right trigger can raise both simultaneously and left trigger can lower both simultaneously
	 * @param joy The Xbox controller that will be used to manually move the screw and pinion
	 * @param inhibitor	A limiter on how fast the screw can be moved
	 */
	public void moveBySpeed(Joystick joy, double inhibitor) {
    	double dualAction = Xbox.RT(joy) - Xbox.LT(joy);
    	double screwSpeed = Xbox.LEFT_Y(joy) + dualAction;
    	double pinionSpeed = Xbox.RIGHT_Y(joy) + dualAction;
    	
		if (!lowerPinionLimit.get() && pinionSpeed > 0)   { pinionSpeed = 0; }
		if (!upperPinionLimit.get() && pinionSpeed < 0)   { pinionSpeed = 0; }
		if (!lowerScrewLimit.get()  && screwSpeed  > 0)   { screwSpeed = 0;  }
		if (!upperScrewLimit.get()  && screwSpeed  < 0)   { screwSpeed = 0;  }
			
    	publishSwitches();
    	
		leftPinion.set(ControlMode.PercentOutput, pinionSpeed);
    	rightPinion.set(ControlMode.PercentOutput, pinionSpeed);
    	screw.set(ControlMode.PercentOutput, inhibitor * screwSpeed);
    }

	/**
	 * Lowers all parts of the mast until the lower limit switches are hit.
	 * Use with caution, especially if anything has gone wrong with a limit switch
	 * @param speed The percent of max speed to lower the mast by
	 */
	public void dropIt(double speed) {
    	if (lowerPinionLimit.get()) {
	    	leftPinion.set(ControlMode.PercentOutput, speed);
	    	rightPinion.set(ControlMode.PercentOutput, speed);
    	} else {
    		leftPinion.set(ControlMode.PercentOutput, 0);
	    	rightPinion.set(ControlMode.PercentOutput, 0);
    	}
    	
    	if (lowerScrewLimit.get()) {
    		screw.set(ControlMode.PercentOutput, speed);
    	} else {
    		screw.set(ControlMode.PercentOutput, 0);
    	}
    	
//    	return (!lowerPinionLimit.get()) && (!lowerScrewLimit.get());
    }

	/**
	 * Put the state of all limit switches on the SmartDash
	 * Should be used in an iterative command to ensure constant updates
	 */
	public void publishSwitches() {
    	SmartDashboard.putBoolean("Lower Screw", !lowerScrewLimit.get());
    	SmartDashboard.putBoolean("Upper Screw", !upperScrewLimit.get());
    	SmartDashboard.putBoolean("Lower Pinion", !lowerPinionLimit.get());
    	SmartDashboard.putBoolean("Upper Pinion", !upperPinionLimit.get());
    }

	/**
	 * Limits the voltage of individual talons passed to the method
	 * @param talon Talon to limit the voltage of
	 */
	private void voltage(TalonSRX talon) {
    	// talon.configNominalOutputVoltage(0f, 0f);
    	// talon.configPeakOutputVoltage(12.0f, -12.0f);
    	talon.enableCurrentLimit(true);
    	talon.configContinuousCurrentLimit(60, 300);
    	talon.configPeakCurrentDuration(500, 10);
    }
}

