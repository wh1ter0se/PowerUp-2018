package org.usfirst.frc.team3695.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;
import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.commands.manual.ManualCommandDrive;
import org.usfirst.frc.team3695.robot.enumeration.Bot;
import org.usfirst.frc.team3695.robot.enumeration.Drivetrain;
import org.usfirst.frc.team3695.robot.util.Xbox;

/** Control for the drivetrain. Both for teleop and autonomous
 *  Autonomous code goes in the AutoDrive inner class */
public class SubsystemDrive extends Subsystem {


    public static TalonSRX leftMaster;
    private static TalonSRX leftSlave;
    public static TalonSRX rightMaster;
    private static TalonSRX rightSlave;

    public Drivetrain drivetrain;

    public static boolean reversing;
    
    public static boolean docking;
    private static double dockInhibitor;
    
    public static boolean narrowing;
    private static double narrower;


    public AnalogGyro gyro;

    public AutoDrive autoDrive;

    /* runs at robot boot */
    public void initDefaultCommand() {
        setDefaultCommand(new ManualCommandDrive());
    }
    
    /**
     * gives birth to the talons and instantiates variables
     */
    public SubsystemDrive() {

        drivetrain = Drivetrain.ROCKET_LEAGUE;

        reversing = false;
        docking = false;
        dockInhibitor = 0.5d;

        autoDrive = new AutoDrive();

        gyro = new AnalogGyro(1);

        // masters
        leftMaster = new TalonSRX(Constants.LEFT_MASTER);
        	leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.PIDF_LOOP_ID, Constants.PIDF_TIMEOUT);
        rightMaster = new TalonSRX(Constants.RIGHT_MASTER);
        	rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.PIDF_LOOP_ID, Constants.PIDF_TIMEOUT);

        // slaves
        leftSlave = new TalonSRX(Constants.LEFT_SLAVE);
        	leftSlave.follow(leftMaster);
        rightSlave = new TalonSRX(Constants.RIGHT_SLAVE);
        	rightSlave.follow(rightMaster);
    }
    
    public void setInverts() {
        rightMaster.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.RIGHT_MASTER_INVERT : Constants.TEUFELSKIND.RIGHT_MASTER_INVERT);
        rightSlave.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.RIGHT_SLAVE_INVERT : Constants.TEUFELSKIND.RIGHT_SLAVE_INVERT);
        leftMaster.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.LEFT_MASTER_INVERT : Constants.TEUFELSKIND.LEFT_MASTER_INVERT);
        leftSlave.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.LEFT_SLAVE_INVERT : Constants.TEUFELSKIND.LEFT_SLAVE_INVERT);
    }

    /* converts left magnetic encoder's magic units to inches
    * Use method within AutoDrive*/
    @Deprecated
    public static double leftMag2In(double leftMag) {
        return leftMag / 204; // 204
    }

    /* converts right magnetic encoder's magic unit to inches
    * Use method within AutoDrive*/
    @Deprecated
    public static double rightMag2In(double rightMag) {
        return rightMag / 212;
    }

    /* converts left magnetic encoder's magic units to inches
    * Use method within AutoDrive*/
    @Deprecated
    public static double leftIn2Mag(double leftMag) {
        return leftMag * 204; // 204
    }

    /* converts right magnetic encoder's magic units to inches
    * Use method within AutoDrive*/
    @Deprecated
    public static double rightIn2Mag(double rightMag) {
//        return rightMag * Constants.RIGHT_MAGIC_PER_INCHES;
        return rightMag * 212;
    }

    /* apply left motor invert */
    @Deprecated
    public static double leftify(double left) {
        return left * (docking ? dockInhibitor : 1);
    }

    /* apply right motor invert */
    @Deprecated
    public static double rightify(double right) {
        return right * (docking ? dockInhibitor : 1);
    }

    public void setDrivetrain(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    public void toggleDocking(double dockInhibitor){
        docking = !docking;
        SubsystemDrive.dockInhibitor = dockInhibitor;
    }
    
    public void toggleNarrowing(double narrower){
        narrowing = !narrowing;
        SubsystemDrive.narrower = narrower;
    }

    public void toggleReversing(){
        reversing = !reversing;
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
        
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
    }

    /**
     * drive code where rotation is dependent on acceleration
     * @param radius 0.00-1.00, 1 being zero radius and 0 being driving in a line
     */
    public void driveForza(Joystick joy, double ramp, double radius, double inhibitor) {
    	
    	if (narrowing) { radius *= narrower; }
    	
        double left, right;
        double acceleration = Xbox.RT(joy) - Xbox.LT(joy);

        setRamps(ramp);
        
            if (reversing ? Xbox.LEFT_X(joy) > 0 : Xbox.LEFT_X(joy) < 0) {
                right = acceleration;
                left = (acceleration * ((2 * (1 - Math.abs(Xbox.LEFT_X(joy)))) - 1)) / radius;
            } else if (reversing ? Xbox.LEFT_X(joy) < 0 : Xbox.LEFT_X(joy) > 0) {
                left = acceleration;
                right = (acceleration * ((2 * (1 - Math.abs(Xbox.LEFT_X(joy)))) - 1)) / radius;
            } else {
                left = acceleration;
                right = acceleration;
            }
            
        left = (left > 1.0 ? 1.0 : (left < -1.0 ? -1.0 : left));
        right = (right > 1.0 ? 1.0 : (right < -1.0 ? -1.0 : right));
        
        leftMaster.set(ControlMode.PercentOutput, left * inhibitor * (reversing ? -1.0 : 1.0));
        rightMaster.set(ControlMode.PercentOutput, right * inhibitor * (reversing ? -1.0 : 1.0));
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
  
    @Deprecated //Use AutoDrive and Pathfinder instead of this
    public void driveDistance(double leftIn, double rightIn) {
        double leftGoal = (leftIn2Mag(leftIn));
        double rightGoal = (rightIn2Mag(rightIn));
        leftMaster.set(ControlMode.MotionMagic, leftGoal);
    		leftSlave.follow(leftMaster);
        rightMaster.set(ControlMode.MotionMagic, rightGoal);
    		rightSlave.follow(rightMaster);
    }

    @Deprecated //If you really want to drive direct, AutoDrive has a method perfect for it
    public void driveDirect(double left, double right) {
        left = (left > 1.0 ? 1.0 : (left < -1.0 ? -1.0 : left));
        right = (right > 1.0 ? 1.0 : (right < -1.0 ? -1.0 : right));
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
    }
    
    
    public static class AutoDrive {
        //The distance between left and right sides of the wheelbase
        public static final double WHEELBASE_WIDTH = 0.5;
        //The diameter of the wheels, but in meters
        public static final double WHEEL_DIAMETER = 0.1524; //Check this number.

        //Various constants needed to generate a motion profile
        private final double TIME_STEP = .05;
        public final double MAX_VELOCITY = .07;
        private final double MAX_ACC = .25;
        private final double MAX_JERK = 60.0;
        //Allows the bot to achieve higher or lower speed quicker
        public final double ACC_GAIN = 0;

        //Configuration that stores all the values needed to configure the motion profile
        Trajectory.Config config;

        public AutoDrive() {
            Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, TIME_STEP, MAX_VELOCITY, MAX_ACC, MAX_JERK);
        }

        //Returns a trajectory path given a set of points
        public Trajectory generateTrajectory(Waypoint[] points){
          return Pathfinder.generate(points, config);
        }

        //Returns a tank modifier of a given trajectory to work with the bot
        public TankModifier generateTankMod(Trajectory trajectory){
            return new TankModifier(trajectory).modify(WHEELBASE_WIDTH);
        }

        //Directly send the talons a percent output
        public void setTalons(double left, double right){
            leftMaster.set(ControlMode.PercentOutput, left);
            rightMaster.set(ControlMode.PercentOutput, right);
        }

        //Clear any information currently stored on the encoders
        public void resetEncoders(){
            leftMaster.setSelectedSensorPosition(0,0,10);
            rightMaster.setSelectedSensorPosition(0,0,10);
        }

        //Return the current position of the right encoder in native units
        public double rightEncoderPos(){
            return rightMaster.getSelectedSensorPosition(0);
        }

        //Return the current position of the right encoder in inches
        public double rightEncoderInches() { return nativeToInches(rightEncoderPos()); }

        //Return the current position of the left encoder in native units
        public double leftEncoderPos(){
           return leftMaster.getSelectedSensorPosition(0);
        }

        //Return the current position of the left encoder in inches
        public double leftEncoderInches() { return nativeToInches(leftEncoderPos()); }

        //Convert from an encoder's native units to inches
        //May have to be separated into two different methods if the right and left sides are drastically different
        public double nativeToInches(double nativeUnits){
            return nativeUnits/212;
        }

        //Converts inches back to an encoder's native units
        public double inchesToNative(double inches){
            return inches*212;
        }
    }
}
