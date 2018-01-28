package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/** VROOM VROOM */
public class SubsystemManipulator extends Subsystem {
	
	
	private TalonSRX armLeft;
	private TalonSRX armRight;
	
	public static int redlineIteration;
	
	/** applies left arm motor invert */
	public static final double leftArmify(double left) {
		return left * (Constants.LEFT_ARM_MOTOR_INVERT ? -1.0 : 1.0);
	}
	
	/** applies right arm motor invert */
	public static final double rightArmify(double right) {
		return right * (Constants.RIGHT_ARM_MOTOR_INVERT ? -1.0 : 1.0);
	}
	
	/** runs at robot boot */
    public void initDefaultCommand() {}
	
	/** gives birth to the CANTalons */
    public SubsystemManipulator(){
    	armLeft = new TalonSRX(Constants.LEFT_ARM);
    	armRight = new TalonSRX(Constants.RIGHT_ARM);
    }
    
    /** eat the power cube */
    public void eat(double speed) {
    	speed *= -1;
    	armLeft.set(ControlMode.PercentOutput, leftArmify(speed));
    	armRight.set(ControlMode.PercentOutput, rightArmify(speed));
    }
    
    /** spit out the power cube */
    public void spit(double speed) {
    	armLeft.set(ControlMode.PercentOutput, leftArmify(speed));
    	armRight.set(ControlMode.PercentOutput, rightArmify(speed));
    }
    
    /** STOP SPINNING ME RIGHT ROUND, BABY RIGHT ROUND */
    public void stopSpinning() {
    	armLeft.set(ControlMode.PercentOutput, 0);
    	armRight.set(ControlMode.PercentOutput, 0);
    }
    
    /** imitates a combustion engine redlining
     * @param   frequency   iterations between start and end (each is about 1/50th of a sec)
     * */
    public void redlineUntilStop(int frequency) {
    	if (redlineIteration >= frequency) { redlineIteration = 0 ; }
    	
    	double speed = (double) redlineIteration / (double) frequency;
    	speed = generateRedlineCurve(speed);
    	
    	armLeft.set(ControlMode.PercentOutput, leftArmify(speed));
    	armRight.set(ControlMode.PercentOutput, rightArmify(speed));
    	
    	redlineIteration++;
    }
    
    /** generates a quadratic curve based on the three points in constants */
    public double generateRedlineCurve(double x) {
    	// TODO simplify this; I just plugged our variables into the equation for this
    	double y;
    	y  = Constants.REDLINE_START * (((x - .5) * (x - 1))/(.5));
    	y += Constants.REDLINE_MID * ((x * (x - 1))/(-.25));
    	y += Constants.REDLINE_END * ((x * (x-.5))/(.5));
    	return y;
    }

    /** configures the voltage of each CANTalon */
    private void voltage(TalonSRX talon) {
    	// talon.configNominalOutputVoltage(0f, 0f);
    	// talon.configPeakOutputVoltage(12.0f, -12.0f);
    	// talon.enableCurrentLimit(true);
    	// talon.configContinuousCurrentLimit(30, 3000);
    }
    
    

}

