package org.usfirst.frc.team3695.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.commands.ManualCommandDrive;
import org.usfirst.frc.team3695.robot.enumeration.Drivetrain;
import org.usfirst.frc.team3695.robot.util.Xbox;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;

/**
 * VROOM VROOM
 */
public class SubsystemDrive extends Subsystem {


    private TalonSRX leftMaster;
    private TalonSRX leftSlave;
    private TalonSRX rightMaster;
    private TalonSRX rightSlave;

    public Drivetrain drivetrain;

    /**
     * Allowable tolerance to be considered in range when driving a distance, in rotations
     */
    public static final double DISTANCE_ALLOWABLE_ERROR = SubsystemDrive.in2rot(2.0);

    /**
     * runs at robot boot
     */
    public void initDefaultCommand() {
        setDefaultCommand(new ManualCommandDrive());
    }


    /**
     * converts RPM to inches per second
     */
    public static final double rpm2ips(double rpm) {
        return rpm / 60.0 * Constants.WHEEL_DIAMETER * Math.PI;
    }


    /**
     * converts an inches per second number to RPM
     */
    public static final double ips2rpm(double ips) {
        return ips * 60.0 / Constants.WHEEL_DIAMETER / Math.PI;
    }


    /**
     * converts rotations to distance traveled in inches
     */
    public static final double rot2in(double rot) {
        return rot * Constants.WHEEL_DIAMETER * Math.PI;
    }


    /**
     * converts distance traveled in inches to rotations
     */
    public static final double in2rot(double in) {
        return in / Constants.WHEEL_DIAMETER / Math.PI;
    }

    /**
     * apply left motor invert
     */
    public static final double leftify(double left) {
		return left * (Constants.LEFT_MOTOR_INVERT ? -1.0 : 1.0);
	}

    /**
     * apply right motor invert
     */
    public static final double rightify(double right) {
        return right * (Constants.RIGHT_MOTOR_INVERT ? -1.0 : 1.0);
    }

    /**
     * gives birth to the CANTalons
     */
    public SubsystemDrive() {

        drivetrain = Drivetrain.ROCKET_LEAGUE;

        // masters
        leftMaster = new TalonSRX(Constants.LEFT_MASTER);
        //leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.LEFT_PID, Constants.TIMEOUT_PID);
        rightMaster = new TalonSRX(Constants.RIGHT_MASTER);
        //rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.RIGHT_PID, Constants.TIMEOUT_PID);

        // slaves
        leftSlave = new TalonSRX(Constants.LEFT_SLAVE);
        leftSlave.follow(leftMaster);
        rightSlave = new TalonSRX(Constants.RIGHT_SLAVE);
        rightSlave.follow(rightMaster);

        //voltage(leftMaster);
        //voltage(leftSlave);
        //voltage(rightMaster);
        //voltage(rightSlave);
    }

    public void setDrivetrain(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    /**
     * simple rocket league drive code; independent rotation and acceleration
     */
    public void driveRLTank(Joystick joy) {
        double adder = Xbox.RT(joy) - Xbox.LT(joy);
        double left = adder + (Xbox.LEFT_X(joy) / 1.333333);
        double right = adder - (Xbox.LEFT_X(joy) / 1.333333);

        //Quick Truncate
        left = (left > 1.0 ? 1.0 : (left < -1.0 ? -1.0 : left));
        right = (right > 1.0 ? 1.0 : (right < -1.0 ? -1.0 : right));

        leftMaster.set(ControlMode.PercentOutput, leftify(left));
//    		leftSlave.set(ControlMode.Follower, leftify(left));
        rightMaster.set(ControlMode.PercentOutput, rightify(right));
//    		rightSlave.set(ControlMode.Follower, rightify(right));

    }

    /**
     * drive code where rotation is dependent on acceleration
     *
     * @param radius 0.00-1.00, 1 being zero radius and 0 being driving in a line
     */
    public void driveForza(Joystick joy, double ramp, double radius) {
        double left = 0,
                right = 0;
        double acceleration = Xbox.RT(joy) - Xbox.LT(joy);

        if (Xbox.LEFT_X(joy) < 0) {
            right = acceleration;
            left = (acceleration * ((2 * (1 - Math.abs(Xbox.LEFT_X(joy)))) - 1)) / radius;
        } else if (Xbox.LEFT_X(joy) > 0) {
            left = acceleration;
            right = (acceleration * ((2 * (1 - Math.abs(Xbox.LEFT_X(joy)))) - 1)) / radius;
        } else {
            left = acceleration;
            right = acceleration;
        }

        /// ramps
        leftMaster.configOpenloopRamp(ramp, 0);
        leftSlave.configOpenloopRamp(ramp, 0);
        rightMaster.configOpenloopRamp(ramp, 0);
        rightSlave.configOpenloopRamp(ramp, 0);

        leftMaster.set(ControlMode.PercentOutput, leftify(left));
//			leftSlave.set(ControlMode.PercentOutput, leftify(left));
        rightMaster.set(ControlMode.PercentOutput, rightify(right));
//			rightSlave.set(ControlMode.PercentOutput, rightify(right));
    }

    /**
     * configures the voltage of each CANTalon
     */
    private void voltage(TalonSRX talon) {
        // talon.configNominalOutputVoltage(0f, 0f);
        // talon.configPeakOutputVoltage(12.0f, -12.0f);
        // talon.enableCurrentLimit(true);
        // talon.configContinuousCurrentLimit(35, 300);
    }

    public double getError() {
        return  (leftify(leftMaster.getErrorDerivative(Constants.LEFT_PID)) + rightify(rightMaster.getErrorDerivative(Constants.RIGHT_PID))) / 2.0;
    }

    double getRightPos() {
        return rightMaster.getSelectedSensorPosition(Constants.RIGHT_PID);
    }

    double getLeftPos() {
        return leftMaster.getSelectedSensorPosition(Constants.LEFT_PID);
    }

    public boolean driveDistance(double leftIn, double rightIn) {
        double leftGoal = in2rot(leftIn);
        double rightGoal = in2rot(rightIn);

        leftMaster.set(ControlMode.MotionMagic, leftGoal);
        rightMaster.set(ControlMode.MotionMagic, rightGoal);

        boolean leftInRange =
                getLeftPos() > leftify(leftGoal) - DISTANCE_ALLOWABLE_ERROR &&
                        getLeftPos() < leftify(leftGoal) + DISTANCE_ALLOWABLE_ERROR;
        boolean rightInRange =
                getRightPos() > rightify(rightGoal) - DISTANCE_ALLOWABLE_ERROR &&
                        getRightPos() < rightify(rightGoal) + DISTANCE_ALLOWABLE_ERROR;

        return leftInRange && rightInRange;
    }

    public void driveDirect(double left, double right) {
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
    }

    public void setPIDF(double p, double i, double d, double f) {
        rightMaster.config_kF(Constants.RIGHT_PID, f, Constants.TIMEOUT_PID);
        rightMaster.config_kP(Constants.RIGHT_PID, p, Constants.TIMEOUT_PID);
        rightMaster.config_kI(Constants.RIGHT_PID, i, Constants.TIMEOUT_PID);
        rightMaster.config_kD(Constants.RIGHT_PID, d, Constants.TIMEOUT_PID);

        leftMaster.config_kF(Constants.RIGHT_PID, f, Constants.TIMEOUT_PID);
        leftMaster.config_kP(Constants.RIGHT_PID, p, Constants.TIMEOUT_PID);
        leftMaster.config_kI(Constants.RIGHT_PID, i, Constants.TIMEOUT_PID);
        leftMaster.config_kD(Constants.RIGHT_PID, d, Constants.TIMEOUT_PID);
    }

    public void reset() {
        leftMaster.setSelectedSensorPosition(0, Constants.LEFT_PID, Constants.TIMEOUT_PID);
        rightMaster.setSelectedSensorPosition(0, Constants.RIGHT_PID, Constants.TIMEOUT_PID);
    }
}
