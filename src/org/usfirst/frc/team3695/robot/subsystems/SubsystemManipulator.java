package org.usfirst.frc.team3695.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Bot;
import org.usfirst.frc.team3695.robot.util.Xbox;

/** Control the flywheels of the manipulator to spin in or out */
public class SubsystemManipulator extends Subsystem {
	
	
	private TalonSRX leftFlywheel;
	private TalonSRX rightFlywheel;
	
	@Deprecated
	//applies left arm motor invert
	public static double leftArmify(double left) {
		Boolean invert = Robot.bot == Bot.OOF ? Constants.OOF.LEFT_FLYWHEEL_MOTOR_INVERT : Constants.TEUFELSKIND.LEFT_FLYWHEEL_MOTOR_INVERT;
		return left * (invert ? -1.0 : 1.0);
	}
	
	@Deprecated
	/** applies right arm motor invert */
    public static double rightArmify(double right) {
		Boolean invert = Robot.bot == Bot.OOF ? Constants.OOF.RIGHT_FLYWHEEL_MOTOR_INVERT : Constants.TEUFELSKIND.RIGHT_FLYWHEEL_MOTOR_INVERT;
		return right * (invert ? -1.0 : 1.0);
	}
	
	/** runs at robot boot */
    public void initDefaultCommand() {}
	
	/** gives birth to the talonSRX */
    public SubsystemManipulator(){
		leftFlywheel = new TalonSRX(Constants.LEFT_FLYWHEEL);
    	rightFlywheel = new TalonSRX(Constants.RIGHT_FLYWHEEL);
    	stopSpinning();
    }
    
    public void setInverts() {
        leftFlywheel.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.LEFT_FLYWHEEL_MOTOR_INVERT : Constants.TEUFELSKIND.LEFT_FLYWHEEL_MOTOR_INVERT);
        rightFlywheel.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.RIGHT_FLYWHEEL_MOTOR_INVERT : Constants.TEUFELSKIND.RIGHT_FLYWHEEL_MOTOR_INVERT);
    }
    
    /** eat the power cube */
    public void eat() {
    	leftFlywheel.set(ControlMode.PercentOutput, 1);
    	rightFlywheel.set(ControlMode.PercentOutput, 1);
    }
    
    /** spit out the power cube */
    public void spit() {
    	leftFlywheel.set(ControlMode.PercentOutput, -1);
    	rightFlywheel.set(ControlMode.PercentOutput, -1);
    }
    
    public void spinByJoystick(Joystick joy) {
    	int speed = 0;
    	speed += joy.getRawButton(Xbox.RB) ? 1d : 0d;
    	speed -= joy.getRawButton(Xbox.LB) ? 1d : 0d;
    	leftFlywheel.set(ControlMode.PercentOutput, speed);
    	rightFlywheel.set(ControlMode.PercentOutput, speed);
    }
    
    /** STOP SPINNING ME RIGHT ROUND, BABY RIGHT ROUND */
    public void stopSpinning() {
    	leftFlywheel.set(ControlMode.PercentOutput, 0);
    	rightFlywheel.set(ControlMode.PercentOutput, 0);
    }

}

