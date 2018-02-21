package org.usfirst.frc.team3695.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.commands.ManualCommandGrow;
import org.usfirst.frc.team3695.robot.enumeration.Bot;
import org.usfirst.frc.team3695.robot.enumeration.Position;
import org.usfirst.frc.team3695.robot.util.Xbox;

/** the big, metal pole */
public class SubsystemMast extends Subsystem {
	
	
	private TalonSRX leftPinion;
	private TalonSRX rightPinion;
	private TalonSRX screw;
	
	DigitalInput lowerPinionLimit;
    DigitalInput upperPinionLimit;
    DigitalInput lowerScrewLimit;
    DigitalInput midScrewLimit;
    DigitalInput upperScrewLimit;

    private Boolean override;

	
	/** runs at robot boot */
    public void initDefaultCommand() {
    	setDefaultCommand(new ManualCommandGrow()); }
	
	/** gives birth to the CANTalons */
    public SubsystemMast(){
    	lowerPinionLimit = new DigitalInput(1);
        upperPinionLimit = new DigitalInput(2);
        lowerScrewLimit  = new DigitalInput(3);
        upperScrewLimit  = new DigitalInput(4);
    	
    	leftPinion = new TalonSRX(Constants.LEFT_PINION_MOTOR);
    	rightPinion = new TalonSRX(Constants.RIGHT_PINION_MOTOR);
    	screw = new TalonSRX(Constants.SCREW_MOTOR);
    		voltage(leftPinion);
    		voltage(rightPinion);
    		voltage(screw);
    		
		override = false;
    }
    
    public void setOverride(Boolean override) {
    	this.override = override;
    }

   	/** apply pinion motor invert */
   	public static final double leftPinionate(double left) {
   		Boolean invert = Robot.bot == Bot.OOF ? Constants.OOF.LEFT_PINION_MOTOR_INVERT : Constants.SWISS.LEFT_PINION_MOTOR_INVERT;
   		return left * (invert ? -1.0 : 1.0);
   	}
   	
   	/** apply screw motor invert */
   	public static final double rightPinionate(double right) {
   		Boolean invert = Robot.bot == Bot.OOF ? Constants.OOF.RIGHT_PINION_MOTOR_INVERT : Constants.SWISS.RIGHT_PINION_MOTOR_INVERT;
   		return right * (invert ? -1.0 : 1.0);
   	}
   	
   	public static final double screwify(double screw) {
   		Boolean invert = Robot.bot == Bot.OOF ? Constants.OOF.SCREW_MOTOR_INVERT : Constants.SWISS.SCREW_MOTOR_INVERT;
   		return screw * (invert ? -1.0 : 1.0);
   	}
    
   	/** raise the mast at RT-LR trigger speed */
    public void moveBySpeed(Joystick joy, double inhibitor) {
    	double dualAction = Xbox.RT(joy) - Xbox.LT(joy);
    	double screwSpeed = Xbox.LEFT_Y(joy) + dualAction;
    	double pinionSpeed = Xbox.RIGHT_Y(joy) + dualAction;
    	
		if (!lowerPinionLimit.get() && pinionSpeed > 0)   { pinionSpeed = 0; }
		if (!upperPinionLimit.get() && pinionSpeed < 0)   { pinionSpeed = 0; }
		if (!lowerScrewLimit.get()  && screwSpeed  > 0)   { screwSpeed = 0;  }
		if (!upperScrewLimit.get()  && screwSpeed  < 0)   { screwSpeed = 0;  }
			
    	publishSwitches();
    	if (!override) {
    		leftPinion.set(ControlMode.PercentOutput, leftPinionate(pinionSpeed));
        	rightPinion.set(ControlMode.PercentOutput, rightPinionate(pinionSpeed));
        	screw.set(ControlMode.PercentOutput, inhibitor * screwify(screwSpeed));
    	}
    }
    
    public Boolean dropIt() {
    	if (lowerPinionLimit.get() && upperPinionLimit.get()) {
	    	leftPinion.set(ControlMode.PercentOutput, leftPinionate(-1));
	    	rightPinion.set(ControlMode.PercentOutput, rightPinionate(-1));
    	} else {
    		leftPinion.set(ControlMode.PercentOutput, 0);
	    	rightPinion.set(ControlMode.PercentOutput, 0);
    	}
    	
    	if (lowerScrewLimit.get() && upperScrewLimit.get()) {
    		screw.set(ControlMode.PercentOutput, screwify(1));
    	} else {
    		screw.set(ControlMode.PercentOutput, 0);
    	}
    	
    	return (!lowerPinionLimit.get() || !upperPinionLimit.get()) && (!lowerScrewLimit.get() || !upperScrewLimit.get());
    }

    public void adjustPinion(Position direction){

    	if (direction == Position.UP) {
			while (!upperPinionLimit.get()) {
				rightPinion.set(ControlMode.PercentOutput, rightPinionate(1));
				leftPinion.set(ControlMode.PercentOutput, leftPinionate(1));
			}
		} else if (direction == Position.DOWN) {
			while (!lowerPinionLimit.get()) {
				rightPinion.set(ControlMode.PercentOutput, rightPinionate(-1));
				leftPinion.set(ControlMode.PercentOutput, leftPinionate(-1));
			}
		}
		rightPinion.set(ControlMode.PercentOutput, rightPinionate(0));
    	leftPinion.set(ControlMode.PercentOutput, leftPinionate(0));
	}

	public void adjustScrew(Position direction){
    	if (direction == Position.UP){
    		while (!upperScrewLimit.get()){
    			screw.set(ControlMode.PercentOutput, screwify(1));
			}
		} else if (direction == Position.DOWN){
    		while (!lowerScrewLimit.get()){
    			screw.set(ControlMode.PercentOutput, screwify(-1));
			}
		}
		screw.set(ControlMode.PercentOutput, screwify(0));
	}
    	
    public void publishSwitches() {
    	SmartDashboard.putBoolean("Lower Screw", !lowerScrewLimit.get());
    	SmartDashboard.putBoolean("Upper Screw", !upperScrewLimit.get());
    	SmartDashboard.putBoolean("Lower Pinion", !lowerPinionLimit.get());
    	SmartDashboard.putBoolean("Upper Pinion", !upperPinionLimit.get());
    }
    
    /** configures the voltage of each CANTalon */
    private void voltage(TalonSRX talon) {
    	// talon.configNominalOutputVoltage(0f, 0f);
    	// talon.configPeakOutputVoltage(12.0f, -12.0f);
    	talon.enableCurrentLimit(true);
    	talon.configContinuousCurrentLimit(60, 300);
    	talon.configPeakCurrentDuration(500, 10);
    }
    
    

}

