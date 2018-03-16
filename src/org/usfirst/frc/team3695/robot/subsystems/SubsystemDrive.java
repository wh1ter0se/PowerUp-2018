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
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive.PIDF;
import org.usfirst.frc.team3695.robot.util.Util;
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
    
    public static boolean narrowing;
    private static double narrower;

    private Accelerometer accel;

    public PIDF pidf; // instantiate innerclass

    /* Allowable tolerance to be considered in range when driving a distance, in rotations */
    public static final double DISTANCE_ALLOWABLE_ERROR = 8.0;

    /* runs at robot boot */
    public void initDefaultCommand() {
        setDefaultCommand(new ManualCommandDrive());
    }

    /* converts left magnetic encoder's magic units to inches */
    public static double leftMag2In(double leftMag) {
        return leftMag / 204; // 204
    }

    /* converts right magnetic encoder's magic unit to inches */
    public static double rightMag2In(double rightMag) {
        return rightMag / 212;
    }

    /* converts left magnetic encoder's magic units to inches */
    public static double leftIn2Mag(double leftMag) {
        return leftMag * 204; // 204
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
        return left * (docking ? dockInhibitor : 1);
    }

    /* apply right motor invert */
    public static final double rightify(double right) {
        return right * (docking ? dockInhibitor : 1);
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

        pidf = new PIDF();

        // masters
        leftMaster = new TalonSRX(Constants.LEFT_MASTER);
        	leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.LEFT_PID, Constants.PIDF_TIMEOUT);
        rightMaster = new TalonSRX(Constants.RIGHT_MASTER);
        	rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.PIDF_LOOP_ID, Constants.PIDF_TIMEOUT);

        // slaves
        leftSlave = new TalonSRX(Constants.LEFT_SLAVE);
        	leftSlave.follow(leftMaster);
        rightSlave = new TalonSRX(Constants.RIGHT_SLAVE);
        	rightSlave.follow(rightMaster);

        PIDF.setPIDF(0);
    }

    public void setDrivetrain(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    public void toggleDocking(double dockInhibitor){
        docking = !docking;
        this.dockInhibitor = dockInhibitor;
    }
    
    public void toggleNarrowing(double narrower){
        narrowing = !narrowing;
        this.narrower = narrower;
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
        
        leftMaster.set(ControlMode.PercentOutput, leftify(left));
        rightMaster.set(ControlMode.PercentOutput, rightify(right));
    }

    /**
     * drive code where rotation is dependent on acceleration
     * @param radius 0.00-1.00, 1 being zero radius and 0 being driving in a line
     */
    public void driveForza(Joystick joy, double ramp, double radius, double inhibitor) {
    	
    	if (narrowing) { radius *= narrower; }
    	
        double left = 0,
                right = 0;
        double acceleration = Xbox.RT(joy) - Xbox.LT(joy);

        setRamps(ramp);
        
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
            
        left = (left > 1.0 ? 1.0 : (left < -1.0 ? -1.0 : left));
        right = (right > 1.0 ? 1.0 : (right < -1.0 ? -1.0 : right));
        
        leftMaster.set(ControlMode.PercentOutput, leftify(left) * inhibitor * (reversing ? -1.0 : 1.0));
        rightMaster.set(ControlMode.PercentOutput, rightify(right) * inhibitor * (reversing ? -1.0 : 1.0));
    }

    public void driveBrogan(Joystick joy, double ramp, double inhibitor) {
        double power = Xbox.LEFT_Y(joy);
        double left  = power + (Xbox.LT(joy) / (4/3)) - (Xbox.RT(joy) / (4/3));
        double right = power + (Xbox.RT(joy) / (4/3)) - (Xbox.LT(joy) / (4/3));

        //Truncate. We can't run greater than 100% because Caleb won't let me
        left = (left > 1.0 ? 1.0 : (left < -1.0 ? -1.0 : left));
        right = (right > 1.0 ? 1.0 : (right < -1.0 ? -1.0 : right));
        setRamps(ramp);

        leftMaster.set(ControlMode.PercentOutput, leftify(left));
        rightMaster.set(ControlMode.PercentOutput, rightify(right));
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
    
    public void driveDistance(double leftIn, double rightIn) {
        double leftGoal = (leftIn2Mag(leftIn));
        double rightGoal = (rightIn2Mag(rightIn));
        leftMaster.set(ControlMode.MotionMagic, leftify(leftGoal));
    		leftSlave.follow(leftMaster);
        rightMaster.set(ControlMode.MotionMagic, rightify(rightGoal));
    		rightSlave.follow(rightMaster);
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
    
    
    public static class PIDF {
    	Boolean enabled;
    	
    	public PIDF() {
    		enabled = true;
    	}
    	
    	public static void setPIDF(int slot) {
    		double[] leftDistPIDF = {
        			Util.getAndSetDouble("LeftDistance-P", 0.16),
    				Util.getAndSetDouble("LeftDistance-I", 0),
    				Util.getAndSetDouble("LeftDistance-D", 3),
    				Util.getAndSetDouble("LeftDistance-F", 0.30509)};
        	double[] rightDistPIDF = {
        			Util.getAndSetDouble("RightDistance-P", 0.1655),
    				Util.getAndSetDouble("RightDistance-I", 0),
    				Util.getAndSetDouble("RightDistance-D", 1.655),
    				Util.getAndSetDouble("RightDistance-F", 0.33355)};
        	double[] leftCWPIDF = {
        			Util.getAndSetDouble("LeftCW-P", 1.16013),
    				Util.getAndSetDouble("LeftCW-I", 0),
    				Util.getAndSetDouble("LeftCW-D", 1.60125),
    				Util.getAndSetDouble("LeftCW-F", .30509)};
        	double[] rightCWPIDF = {
        			Util.getAndSetDouble("RightCW-P", 0.17514),
    				Util.getAndSetDouble("RightCW-I", 0),
    				Util.getAndSetDouble("RightCW-D", 1.7514),
    				Util.getAndSetDouble("RightCW-F", 0.33355)};
        	double[] leftCCWPIDF = {
        			Util.getAndSetDouble("LeftCCW-P", 0.176125),
    				Util.getAndSetDouble("LeftCCW-I", 0),
    				Util.getAndSetDouble("LeftCCW-D", 1.60125),
    				Util.getAndSetDouble("LeftCCW-F", .30509)};
        	double[] rightCCWPIDF = {
        			Util.getAndSetDouble("RightCCW-P", 1.19864),
    				Util.getAndSetDouble("RightCCW-I", 0),
    				Util.getAndSetDouble("RightCCW-D", 1.7514),
    				Util.getAndSetDouble("RightCCW-F", 0.33355)};
        	
        	if (slot == 0){
        		PIDF.setPIDF(0, leftDistPIDF, rightDistPIDF);
        	} else if (slot == 1) {
        		PIDF.setPIDF(1, leftCWPIDF, rightCWPIDF);
        	} else {
        		PIDF.setPIDF(1, leftCCWPIDF, rightCCWPIDF);
        	}
        	
    	}
    	
        public static void setPIDF(int slot, double[] leftPIDF, double[] rightPIDF) {
            //For future reference: Inverts must be applied individually
            setPIDF(Robot.SUB_DRIVE.leftMaster, leftPIDF, slot, (int) Util.getAndSetDouble("Left Velocity", 0), (int) Util.getAndSetDouble("Left Acceleration", 0));
            setPIDF(Robot.SUB_DRIVE.leftSlave, leftPIDF, slot, (int) Util.getAndSetDouble("Left Velocity", 0), (int) Util.getAndSetDouble("Left Acceleration", 0));
            setPIDF(Robot.SUB_DRIVE.rightMaster, rightPIDF, slot, (int) Util.getAndSetDouble("Right Velocity", 0), (int) Util.getAndSetDouble("Right Acceleration", 0));
            setPIDF(Robot.SUB_DRIVE.rightSlave, rightPIDF, slot, (int) Util.getAndSetDouble("Right Velocity", 0), (int) Util.getAndSetDouble("Right Acceleration", 0));
            //Do not remove. This delay may be necessary to ensure all PID loops have updated before it continues driving
            for (int i = 0; i < 4; i++) {
            	DriverStation.reportWarning(Double.toString(leftPIDF[i]), false);
            	DriverStation.reportWarning(Double.toString(rightPIDF[i]), false);
            }
        }

        //
        public static void setPIDF(TalonSRX _talon, double[] PIDF, int slot, int velocity, int acceleration) {
            /* first choose the sensor */
            _talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                    0, Constants.PIDF_TIMEOUT);
            _talon.setSensorPhase(true);
            /* Set relevant frame periods to be at least as fast as periodic rate*/
            _talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10,
                    Constants.PIDF_TIMEOUT);
            _talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10,
                    Constants.PIDF_TIMEOUT);
            /* set the peak and nominal outputs */
            _talon.configNominalOutputForward(0, Constants.PIDF_TIMEOUT);
            _talon.configNominalOutputReverse(0, Constants.PIDF_TIMEOUT);
            _talon.configPeakOutputForward(1, Constants.PIDF_TIMEOUT);
            _talon.configPeakOutputReverse(-1, Constants.PIDF_TIMEOUT);
            /* set closed loop gains in slot */
            _talon.selectProfileSlot(slot, Constants.PIDF_LOOP_ID);
            _talon.config_kP(slot, PIDF[0], Constants.PIDF_TIMEOUT);
            _talon.config_kI(slot, PIDF[1], Constants.PIDF_TIMEOUT);
            _talon.config_kD(slot, PIDF[2], Constants.PIDF_TIMEOUT);
            _talon.config_kF(slot, PIDF[3], Constants.PIDF_TIMEOUT);
            /* set acceleration and vcruise velocity - see documentation */
            _talon.configMotionCruiseVelocity(velocity, Constants.PIDF_TIMEOUT);
            _talon.configMotionAcceleration(acceleration, Constants.PIDF_TIMEOUT);
        }


        public void getError() {
            SmartDashboard.putNumber("Right Error", Robot.SUB_DRIVE.rightMaster.getErrorDerivative(0));
            SmartDashboard.putNumber("Left Error", Robot.SUB_DRIVE.leftMaster.getErrorDerivative(0));
        }
        
        public double getRightInches() {
            return rightMag2In(Robot.SUB_DRIVE.rightMaster.getSelectedSensorPosition(Constants.PIDF_LOOP_ID));
        }

        public double getLeftInches() {
            return leftMag2In(Robot.SUB_DRIVE.leftMaster.getSelectedSensorPosition(Constants.LEFT_PID));
        }

        public void reset() {
            Robot.SUB_DRIVE.leftMaster.setSelectedSensorPosition(0, Constants.LEFT_PID, Constants.PIDF_TIMEOUT);
            Robot.SUB_DRIVE.rightMaster.setSelectedSensorPosition(0, Constants.PIDF_LOOP_ID, Constants.PIDF_TIMEOUT);
            Robot.SUB_DRIVE.leftMaster.setIntegralAccumulator(0,0, Constants.PIDF_TIMEOUT);
            Robot.SUB_DRIVE.rightMaster.setIntegralAccumulator(0,0, Constants.PIDF_TIMEOUT);
        }
    }
}
