package org.usfirst.frc.team3695.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.commands.ManualCommandDrive;
import org.usfirst.frc.team3695.robot.enumeration.Bot;
import org.usfirst.frc.team3695.robot.enumeration.Drivetrain;
import org.usfirst.frc.team3695.robot.util.Xbox;

/** VROOM VROOM */
public class SubsystemDrive extends Subsystem {


    private TalonSRX leftMaster;
    private TalonSRX leftSlave;
    private TalonSRX rightMaster;
    private TalonSRX rightSlave;

    public Drivetrain drivetrain;

    public static boolean reversing;

    public static boolean docking;
    private static double dockInhibitor;

    public static boolean override;

    private Accelerometer accel;

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
     * converts left magnetic encoder's magic units to inches
     */
    public static final double leftMag2in(double leftMag) {
        return leftMag / Constants.LEFT_MAGIC_PER_INCHES;
    }

    /**
     * converts right magnetic encoder's magic units to inches
     */
    public static final double rightMag2in(double rightMag) {
        return rightMag / Constants.RIGHT_MAGIC_PER_INCHES;
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
        Boolean invert = Robot.bot == Bot.OOF ? Constants.OOF.LEFT_MOTOR_INVERT : Constants.SWISS.LEFT_MOTOR_INVERT;
        return left * (invert ? -1.0 : 1.0) * (docking ? dockInhibitor : 1);
    }

    /**
     * apply right motor invert
     */
    public static final double rightify(double right) {
        Boolean invert = Robot.bot == Bot.OOF ? Constants.OOF.RIGHT_MOTOR_INVERT : Constants.SWISS.RIGHT_MOTOR_INVERT;
        return right * (invert ? -1.0 : 1.0) * (docking ? dockInhibitor : 1);
    }

    /**
     * gives birth to the CANTalons
     */
    public SubsystemDrive() {

        accel = new BuiltInAccelerometer();

        drivetrain = Drivetrain.ROCKET_LEAGUE;

        reversing = false;

        docking = false;
        dockInhibitor = 0.5d;

        override = false;

        // masters
        leftMaster = new TalonSRX(Constants.LEFT_MASTER);
        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.LEFT_PID, Constants.TIMEOUT_PID);
        rightMaster = new TalonSRX(Constants.RIGHT_MASTER);
        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.RIGHT_PID, Constants.TIMEOUT_PID);

        // slaves
        leftSlave = new TalonSRX(Constants.LEFT_SLAVE);
        leftSlave.follow(leftMaster);
        rightSlave = new TalonSRX(Constants.RIGHT_SLAVE);
        rightSlave.follow(rightMaster);

        switch (Robot.bot){
            case SWISS:
                setPIDF(leftMaster, Constants.SWISS.P, Constants.SWISS.I, Constants.SWISS.D, Constants.SWISS.F);
                setPIDF(rightMaster, Constants.SWISS.P, Constants.SWISS.I, Constants.SWISS.D, Constants.SWISS.F);
                break;
            case OOF:
                setPIDF(leftMaster, Constants.OOF.P, Constants.OOF.I, Constants.OOF.D, Constants.OOF.F);
                setPIDF(rightMaster, Constants.OOF.P, Constants.OOF.I, Constants.OOF.D, Constants.OOF.F);
                break;
        }
    }

    public void setDrivetrain(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    public double getYAngle() {
        //http://www.hobbytronics.co.uk/accelerometer-info
        //Formula for getting the angle through the accelerometer
        //arctan returns in radians so we convert to degrees.
        return Math.atan(accel.getY() / Math.sqrt(Math.pow(accel.getX(), 2) + Math.pow(accel.getZ(), 2))) * 180 / Math.PI;
    }

    public void isDocking(boolean docking, double dockInhibitor) {
        this.docking = docking;
        this.dockInhibitor = dockInhibitor;
    }

    public void isReversing(boolean reversing) {
        this.reversing = reversing;
    }

    /**
     * simple rocket league drive code
     * independent rotation and acceleration
     */

    public void driveRLTank(Joystick joy, double ramp, double inhibitor) {
        double adder = Xbox.RT(joy) - Xbox.LT(joy);
        double left = adder + (Xbox.LEFT_X(joy) / 1.333333);
        double right = adder - (Xbox.LEFT_X(joy) / 1.333333);
        left = (left > 1.0 ? 1.0 : (left < -1.0 ? -1.0 : left));
        right = (right > 1.0 ? 1.0 : (right < -1.0 ? -1.0 : right));
        setRamps(ramp);

//        if (getYAngle() > Constants.TILT_ANGLE ) {
//            leftMaster.set(ControlMode.PercentOutput, -1*Constants.RECOVERY_SPEED);
//            rightMaster.set(ControlMode.PercentOutput, -1*Constants.RECOVERY_SPEED);
//        } else if (getYAngle() < -1*Constants.TILT_ANGLE){
//            leftMaster.set(ControlMode.PercentOutput, Constants.RECOVERY_SPEED);
//            rightMaster.set(ControlMode.PercentOutput, Constants.RECOVERY_SPEED);
//        } else {
            leftMaster.set(ControlMode.PercentOutput, leftify(left)* (reversing ? -1.0 : 1.0));
            rightMaster.set(ControlMode.PercentOutput, rightify(right)* (reversing ? -1.0 : 1.0));
//        }

    }

    /**
     * drive code where rotation is dependent on acceleration
     *
     * @param radius 0.00-1.00, 1 being zero radius and 0 being driving in a line
     */
    public void driveForza(Joystick joy, double ramp, double radius, double inhibitor) {
        double left = 0,
                right = 0;
        double acceleration = Xbox.RT(joy) - Xbox.LT(joy);

        setRamps(ramp);
//        if (getYAngle() > Constants.TILT_ANGLE ) {
//            leftMaster.set(ControlMode.PercentOutput, -1 * Constants.RECOVERY_SPEED);
//            rightMaster.set(ControlMode.PercentOutput, -1 * Constants.RECOVERY_SPEED);
//        } else if (getYAngle() < -1 * Constants.TILT_ANGLE){
//            leftMaster.set(ControlMode.PercentOutput, Constants.RECOVERY_SPEED);
//            rightMaster.set(ControlMode.PercentOutput, Constants.RECOVERY_SPEED);
//        } else {

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
//        }
        left = (left > 1.0 ? 1.0 : (left < -1.0 ? -1.0 : left));
        right = (right > 1.0 ? 1.0 : (right < -1.0 ? -1.0 : right));
        leftMaster.set(ControlMode.PercentOutput, leftify(left) * inhibitor * (reversing ? -1.0 : 1.0));
        rightMaster.set(ControlMode.PercentOutput, rightify(right) * inhibitor * (reversing ? -1.0 : 1.0));

        SmartDashboard.putString("Left Master", "Left Master Voltage: " + leftMaster.getBusVoltage());
        SmartDashboard.putString("Right Master", "Right Master Voltage: " + rightMaster.getBusVoltage());

    }

    public void setRamps(double ramp) {
        leftMaster.configOpenloopRamp(ramp, 10);
        leftSlave.configOpenloopRamp(ramp, 10);
        rightMaster.configOpenloopRamp(ramp, 10);
        rightSlave.configOpenloopRamp(ramp, 10);
    }

    public double getError() {
        return (leftify(leftMaster.getErrorDerivative(Constants.LEFT_PID)) + rightify(rightMaster.getErrorDerivative(Constants.RIGHT_PID))) / 2.0;
    }

    public double getRightPos() {
        return rightMag2in(rightMaster.getSelectedSensorPosition(Constants.RIGHT_PID));
    }

    public double getLeftPos() {
        return leftMag2in(leftMaster.getSelectedSensorPosition(Constants.LEFT_PID));
    }

    public boolean driveDistance(double leftIn, double rightIn) {
        double leftGoal = in2rot(leftIn);
        double rightGoal = in2rot(rightIn);

        leftMaster.set(ControlMode.Position, leftGoal);
        rightMaster.set(ControlMode.Position, rightGoal);

        boolean leftInRange =
                getLeftPos() > leftify(leftGoal) - DISTANCE_ALLOWABLE_ERROR &&
                        getLeftPos() < leftify(leftGoal) + DISTANCE_ALLOWABLE_ERROR;
        boolean rightInRange =
                getRightPos() > rightify(rightGoal) - DISTANCE_ALLOWABLE_ERROR &&
                        getRightPos() < rightify(rightGoal) + DISTANCE_ALLOWABLE_ERROR;

        return leftInRange && rightInRange;
    }

    public void driveDirect(double left, double right) {
        left = (left > 1.0 ? 1.0 : (left < -1.0 ? -1.0 : left));
        right = (right > 1.0 ? 1.0 : (right < -1.0 ? -1.0 : right));
        leftMaster.set(ControlMode.PercentOutput, leftify(left));
        rightMaster.set(ControlMode.PercentOutput, rightify(right));
    }

    public void setPIDF(TalonSRX _talon, double p, double i, double d, double f) {
        /* first choose the sensor */
        _talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                Constants.RIGHT_PID, Constants.TIMEOUT_PID);
        _talon.setSensorPhase(true);
        _talon.setInverted(false);
        /* Set relevant frame periods to be at least as fast as periodic rate*/
        _talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10,
                Constants.TIMEOUT_PID);
        _talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10,
                Constants.TIMEOUT_PID);
        /* set the peak and nominal outputs */
        _talon.configNominalOutputForward(0, Constants.TIMEOUT_PID);
        _talon.configNominalOutputReverse(0, Constants.TIMEOUT_PID);
        _talon.configPeakOutputForward(1, Constants.TIMEOUT_PID);
        _talon.configPeakOutputReverse(-1, Constants.TIMEOUT_PID);
        /* set closed loop gains in slot0 - see documentation */
        _talon.selectProfileSlot(Constants.RIGHT_PID, Constants.TIMEOUT_PID);
        _talon.config_kF(0, 0.2, Constants.TIMEOUT_PID);
        _talon.config_kP(0, 0.2, Constants.TIMEOUT_PID);
        _talon.config_kI(0, 0, Constants.TIMEOUT_PID);
        _talon.config_kD(0, 0, Constants.TIMEOUT_PID);
        /* set acceleration and vcruise velocity - see documentation */
        _talon.configMotionCruiseVelocity(15000, Constants.TIMEOUT_PID);
        _talon.configMotionAcceleration(6000, Constants.TIMEOUT_PID);
        /* zero the sensor */
        _talon.setSelectedSensorPosition(0, Constants.RIGHT_PID, Constants.TIMEOUT_PID);
    }

    public void reset() {
        leftMaster.setSelectedSensorPosition(0, Constants.LEFT_PID, Constants.TIMEOUT_PID);
        rightMaster.setSelectedSensorPosition(0, Constants.RIGHT_PID, Constants.TIMEOUT_PID);
    }
}
