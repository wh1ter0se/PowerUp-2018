package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.commands.ButtonCommandSpit;
import org.usfirst.frc.team3695.robot.commands.ManualCommandDrive;
import org.usfirst.frc.team3695.robot.commands.ManualCommandGrow;
import org.usfirst.frc.team3695.robot.enumeration.Direction;
import org.usfirst.frc.team3695.robot.util.Util;
import org.usfirst.frc.team3695.robot.util.Xbox;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

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

	
	/** runs at robot boot */
    public void initDefaultCommand() {
    	setDefaultCommand(new ManualCommandGrow()); }
	
	/** gives birth to the CANTalons */
    public SubsystemMast(){
    	lowerPinionLimit = new DigitalInput(1);
        upperPinionLimit = new DigitalInput(2);
        lowerScrewLimit  = new DigitalInput(3);
        midScrewLimit    = new DigitalInput(4);
        upperScrewLimit  = new DigitalInput(5);
    	
    	leftPinion = new TalonSRX(Constants.LEFT_PINION_MOTOR);
    	rightPinion = new TalonSRX(Constants.RIGHT_PINION_MOTOR);
    	screw = new TalonSRX(Constants.SCREW_MOTOR);
    		voltage(leftPinion);
    		voltage(rightPinion);
    		voltage(screw);
    }

   	
   	/** apply pinion motor invert */
   	public static final double leftPinionate(double left) {
   		return left * (Constants.LEFT_PINION_MOTOR_INVERT ? -1.0 : 1.0);
   	}
   	
   	/** apply screw motor invert */
   	public static final double rightPinionate(double right) {
   		return right * (Constants.RIGHT_PINION_MOTOR_INVERT ? -1.0 : 1.0);
   	}
   	
   	public static final double screwify(double screw) {
   		return screw * (Constants.SCREW_MOTOR_INVERT ? -1.0 : 1.0);
   	}
    
   	/** raise the mast at RT-LR trigger speed */
    public void moveBySpeed(Joystick joy) {
    	double screwSpeed = Xbox.RT(joy) - Xbox.LT(joy);
    	double pinionSpeed = Xbox.RT(joy) - Xbox.LT(joy);
    	
    	if (lowerScrewLimit.get()  && screwSpeed  < 0)   { screwSpeed = 0;  }
    	if (upperScrewLimit.get()  && screwSpeed  > 1)   { screwSpeed = 0;  }
    	if (lowerPinionLimit.get() && pinionSpeed < 0)   { pinionSpeed = 0; }
    	if (upperPinionLimit.get() && pinionSpeed > 1)   { pinionSpeed = 0; }
    	
    	leftPinion.set(ControlMode.PercentOutput, leftPinionate(screwSpeed));
    	rightPinion.set(ControlMode.PercentOutput, rightPinionate(screwSpeed));
    	screw.set(ControlMode.PercentOutput, screwify(pinionSpeed));
    }

    /** configures the voltage of each CANTalon */
    private void voltage(TalonSRX talon) {
    	// talon.configNominalOutputVoltage(0f, 0f);
    	// talon.configPeakOutputVoltage(12.0f, -12.0f);
    	// talon.enableCurrentLimit(true);
    	talon.configContinuousCurrentLimit(35, 300);
    }
    
    

}

