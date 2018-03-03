package org.usfirst.frc.team3695.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
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


    private static TalonSRX leftMaster;
    private static TalonSRX leftSlave;
    private static TalonSRX rightMaster;
    private static TalonSRX rightSlave;

    public Drivetrain drivetrain;

    public static boolean reversing;
    public static boolean docking;
    private static double dockInhibitor;

    private Accelerometer accel;

    public PID pid; // instantiate innerclass

    /* Allowable tolerance to be considered in range when driving a distance, in rotations */
    public static final double DISTANCE_ALLOWABLE_ERROR = SubsystemDrive.in2rot(2.0);

    /* runs at robot boot */
    public void initDefaultCommand() {
        setDefaultCommand(new ManualCommandDrive());
    }

    /* converts left magnetic encoder's magic units to inches */
    public static double leftMag2In(double leftMag) {
        return leftMag / 212;
    }

    /* converts right magnetic encoder's magic units to inches */
    public static double rightMag2In(double rightMag) {
        return rightMag / 212;
    }

    /* converts left magnetic encoder's magic units to inches */
    public static double leftIn2Mag(double leftMag) {
        return leftMag * 212;
    }

    /* converts right magnetic encoder's magic units to inches */
    public static double rightIn2Mag(double rightMag) {
//        return rightMag * Constants.RIGHT_MAGIC_PER_INCHES;
        return rightMag * 212;
    }
    
    /* converts RPM to inches per second */
    public static double rpm2ips(double rpm) {
        return rpm / 60.0 * Constants.WHEEL_DIAMETER * Math.PI;
    }

    /* converts an inches per second number to RPM */
    public static double ips2rpm(double ips) {
        return ips * 60.0 / Constants.WHEEL_DIAMETER / Math.PI;
    }

    /* converts rotations to distance traveled in inches */
    public static double rot2in(double rot) {
        return rot * Constants.WHEEL_DIAMETER * Math.PI;
    }

    /* converts distance traveled in inches to rotations */
    public static double in2rot(double in) {
        return in / Constants.WHEEL_DIAMETER / Math.PI;
    }

    
    

    /* apply left motor invert */
    public static final double leftify(double left) {
        Boolean invert = Robot.bot == Bot.OOF ? Constants.OOF.LEFT_MOTOR_INVERT : Constants.TEUFELSKIND.LEFT_MOTOR_INVERT;
        return left * (invert ? -1.0 : 1.0) * (docking ? dockInhibitor : 1);
    }

    /* apply right motor invert */
    public static final double rightify(double right) {
        Boolean invert = Robot.bot == Bot.OOF ? Constants.OOF.RIGHT_MOTOR_INVERT : Constants.TEUFELSKIND.RIGHT_MOTOR_INVERT;
        return right * (invert ? -1.0 : 1.0) * (docking ? dockInhibitor : 1);
    }

    
    
    /**
     * gives birth to the talons and instantiates variables (including the Bot enum)
     */
    public SubsystemDrive() {

        accel = new BuiltInAccelerometer();

        drivetrain = Drivetrain.ROCKET_LEAGUE;

        reversing = false;
        docking = false;
        dockInhibitor = 0.5d;

        pid = new PID();

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
            case TEUFELSKIND:
            	PID.setPIDF(Constants.TEUFELSKIND.P, Constants.TEUFELSKIND.I, Constants.TEUFELSKIND.D, Constants.TEUFELSKIND.F);
                break;
            case OOF:
            	PID.setPIDF(Constants.OOF.P, Constants.OOF.I, Constants.OOF.D, Constants.OOF.F);
                break;
        }
    }

    public void setDrivetrain(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    public void toggleDocking(double dockInhibitor){
        docking = !docking;
        this.dockInhibitor = dockInhibitor;
    }

    public void toggleReversing(){
        reversing = !reversing;
    }

    public double getYAngle() {
        //http://www.hobbytronics.co.uk/accelerometer-info
        //Formula for getting the angle through the accelerometer
        //arctan returns in radians so we convert to degrees.
        return Math.atan(accel.getY() / Math.sqrt(Math.pow(accel.getX(), 2) + Math.pow(accel.getZ(), 2))) * 180 / Math.PI;
    }

    
    
    /**
     * simple rocket league drive code (not actually rocket league)
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
            leftMaster.set(ControlMode.PercentOutput, leftify(left));
            rightMaster.set(ControlMode.PercentOutput, rightify(right));
//        }
    }

    /**
     * drive code where rotation is dependent on acceleration
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

            if (!reversing ? Xbox.LEFT_X(joy) < 0 : Xbox.LEFT_X(joy) > 0) {
                right = acceleration;
                left = (acceleration * ((2 * (1 - Math.abs(Xbox.LEFT_X(joy)))) - 1)) / radius;
            } else if (!reversing ? Xbox.LEFT_X(joy) > 0 : Xbox.LEFT_X(joy) < 0) {
                left = acceleration;
                right = (acceleration * ((2 * (1 - Math.abs(Xbox.LEFT_X(joy)))) - 1)) / radius;
            } else {
                left = acceleration;
                right = acceleration;
            }
//        }][\
        left = (left > 1.0 ? 1.0 : (left < -1.0 ? -1.0 : left));
        right = (right > 1.0 ? 1.0 : (right < -1.0 ? -1.0 : right));
        leftMaster.set(ControlMode.PercentOutput, leftify(left) * inhibitor * (reversing ? -1.0 : 1.0));
        rightMaster.set(ControlMode.PercentOutput, rightify(right) * inhibitor * (reversing ? -1.0 : 1.0));

        SmartDashboard.putString("Left Master", "Left Master Voltage: " + leftMaster.getBusVoltage());
        SmartDashboard.putString("Right Master", "Right Master Voltage: " + rightMaster.getBusVoltage());
    }

    
    
    public void setRamps(double ramp) {
        if (leftMaster != null)
        	leftMaster.configOpenloopRamp(ramp, 10);
        if (leftSlave != null)
        	leftSlave.configOpenloopRamp(ramp, 10);
        if (rightMaster != null)
        	rightMaster.configOpenloopRamp(ramp, 10);
        if (rightSlave != null)
        	rightSlave.configOpenloopRamp(ramp, 10);
    }

    
    
    public boolean driveDistance(double leftIn, double rightIn) {
        double leftGoal = (leftIn2Mag(leftIn));
        double rightGoal = (rightIn2Mag(rightIn));
        leftMaster.set(ControlMode.Position, leftify(leftGoal));
    		leftSlave.follow(leftMaster);
        rightMaster.set(ControlMode.Position, rightify(rightGoal));
    		rightSlave.follow(rightMaster);

        boolean leftInRange =
                pid.getLeftInches() > leftify(leftGoal) - DISTANCE_ALLOWABLE_ERROR &&
                        pid.getLeftInches() < leftify(leftGoal) + DISTANCE_ALLOWABLE_ERROR;
        boolean rightInRange =
                pid.getRightInches() > rightify(rightGoal) - DISTANCE_ALLOWABLE_ERROR &&
                        pid.getRightInches() < rightify(rightGoal) + DISTANCE_ALLOWABLE_ERROR;
        return leftInRange && rightInRange;
    }

    public void driveDirect(double left, double right) {
        left = (left > 1.0 ? 1.0 : (left < -1.0 ? -1.0 : left));
        right = (right > 1.0 ? 1.0 : (right < -1.0 ? -1.0 : right));
        leftMaster.set(ControlMode.PercentOutput, leftify(left));
        rightMaster.set(ControlMode.PercentOutput, rightify(right));
    }

    public void setInverts() {
        rightMaster.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.RIGHT_MASTER_INVERT : Constants.TEUFELSKIND.RIGHT_MASTER_INVERT);
        rightSlave.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.RIGHT_SLAVE_INVERT : Constants.TEUFELSKIND.RIGHT_SLAVE_INVERT);
        leftMaster.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.LEFT_MASTER_INVERT : Constants.TEUFELSKIND.LEFT_MASTER_INVERT);
        leftSlave.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.LEFT_SLAVE_INVERT : Constants.TEUFELSKIND.LEFT_SLAVE_INVERT);
    }
    
    
    public static class PID {
    	Boolean enabled;
    	
    	public PID() {
    		enabled = true;
    	}
        public static void setPIDF(double p, double i, double d, double f) {
            //For future reference: Inverts must be applied individually
            setPIDF(Robot.SUB_DRIVE.leftMaster, false, p, i, d, f);
            setPIDF(Robot.SUB_DRIVE.leftSlave, false, p, i, d, f);
            setPIDF(Robot.SUB_DRIVE.rightMaster, true, p, i, d, f);
            setPIDF(Robot.SUB_DRIVE.rightSlave, true, p, i, d, f);
        }

        public static void setPIDF(TalonSRX _talon, Boolean invert, double p, double i, double d, double f) {
            /* first choose the sensor */
            _talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                    0, Constants.TIMEOUT_PID);
            _talon.setSensorPhase(true);
            _talon.setInverted(invert);
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
            _talon.selectProfileSlot(0, Constants.RIGHT_PID);
            _talon.config_kP(0, p, Constants.TIMEOUT_PID);
            _talon.config_kI(0, i, Constants.TIMEOUT_PID);
            _talon.config_kD(0, d, Constants.TIMEOUT_PID);
            _talon.config_kF(0, f, Constants.TIMEOUT_PID);
            /* set acceleration and vcruise velocity - see documentation */
            _talon.configMotionCruiseVelocity(15000, Constants.TIMEOUT_PID);
            _talon.configMotionAcceleration(6000, Constants.TIMEOUT_PID);
        }

        public void zeroEncoders() {
            Robot.SUB_DRIVE.leftMaster.setSelectedSensorPosition(0, 0, Constants.TIMEOUT_PID);
            Robot.SUB_DRIVE.rightMaster.setSelectedSensorPosition(0, 0, Constants.TIMEOUT_PID);
            Robot.SUB_DRIVE.leftMaster.setIntegralAccumulator(0,0, Constants.TIMEOUT_PID);
            Robot.SUB_DRIVE.rightMaster.setIntegralAccumulator(0,0, Constants.TIMEOUT_PID);
        }

        public double getError() {
            return (leftify(Robot.SUB_DRIVE.leftMaster.getErrorDerivative(Constants.LEFT_PID)) + rightify(Robot.SUB_DRIVE.rightMaster.getErrorDerivative(Constants.RIGHT_PID))) / 2.0;
        }

        public double getRightInches() {
            return rightMag2In(Robot.SUB_DRIVE.rightMaster.getSelectedSensorPosition(Constants.RIGHT_PID));
        }

        public double getLeftInches() {
            return leftMag2In(Robot.SUB_DRIVE.leftMaster.getSelectedSensorPosition(Constants.LEFT_PID));
        }

        public void reset() {
            Robot.SUB_DRIVE.leftMaster.setSelectedSensorPosition(0, Constants.LEFT_PID, Constants.TIMEOUT_PID);
            Robot.SUB_DRIVE.rightMaster.setSelectedSensorPosition(0, Constants.RIGHT_PID, Constants.TIMEOUT_PID);
        }
    }
}
