package org.usfirst.frc.team3695.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;
import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.commands.manual.ManualCommandDrive;
import org.usfirst.frc.team3695.robot.enumeration.Bot;
import org.usfirst.frc.team3695.robot.enumeration.Drivetrain;
import org.usfirst.frc.team3695.robot.enumeration.Paths;
import org.usfirst.frc.team3695.robot.util.Xbox;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

/** Control for the drivetrain. Both for teleop and autonomous
 *  Autonomous code goes in the AutoDrive inner class
 */
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

    public ADXRS450_Gyro gyro;

    public AutoDrive autoDrive;

    /* runs at robot boot */
    public void initDefaultCommand() {
        setDefaultCommand(new ManualCommandDrive());
    }
    
    /**
     * Instantiate everything needed for drive to work
     */
    public SubsystemDrive() {
        drivetrain = Drivetrain.ROCKET_LEAGUE;

        reversing = false;
        docking = false;
        dockInhibitor = 0.5d;

        autoDrive = new AutoDrive();

        gyro = new ADXRS450_Gyro();

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

    /**
     * Set the inverts for each talon based on the bot being used
     */
    public void setInverts() {
        rightMaster.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.RIGHT_MASTER_INVERT : Constants.TEUFELSKIND.RIGHT_MASTER_INVERT);
        rightSlave.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.RIGHT_SLAVE_INVERT : Constants.TEUFELSKIND.RIGHT_SLAVE_INVERT);
        leftMaster.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.LEFT_MASTER_INVERT : Constants.TEUFELSKIND.LEFT_MASTER_INVERT);
        leftSlave.setInverted(Robot.bot == Bot.OOF ? Constants.OOF.LEFT_SLAVE_INVERT : Constants.TEUFELSKIND.LEFT_SLAVE_INVERT);
    }
    
    public void setBraking(Boolean braking) {
    	rightMaster.setNeutralMode(braking ? NeutralMode.Brake : NeutralMode.Coast);
    	rightSlave.setNeutralMode(braking ? NeutralMode.Brake : NeutralMode.Coast);
    	leftMaster.setNeutralMode(braking ? NeutralMode.Brake : NeutralMode.Coast);
    	leftSlave.setNeutralMode(braking ? NeutralMode.Brake : NeutralMode.Coast);
    }

    public void publishDrivetrain() {
    	SmartDashboard.putNumber("Left Motor", leftMaster.getMotorOutputPercent());
    	SmartDashboard.putNumber("Right Motor", rightMaster.getMotorOutputPercent());
    }
    /**
     * Set the drivetrain to the one that will be used
     * @param drivetrain The drivetrain to use
     */
    public void setDrivetrain(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    /**
     * Toggles the docking mode and sets the inhibitor
     * @param dockInhibitor The percent maximum that the bot can drive at when in docking mode
     */
    public void toggleDocking(double dockInhibitor){
        docking = !docking;
        SubsystemDrive.dockInhibitor = dockInhibitor;
    }

    /**
     * Toggles narrowing mode and sets the new radius for Forza Drive
     * @param narrower The radius to be set when in narrowing mode
     */
    public void toggleNarrowing(double narrower){
        narrowing = !narrowing;
        SubsystemDrive.narrower = narrower;
    }
    
    /**
     * simple rocket league drive code (not actually rocket league)
     * independent rotation and acceleration
     * Controls:
     *  Forward: Right Trigger
     *  Backwards: Left Trigger
     *  Turning: Right joystick on the x-axis
     *  @param joy The Xbox controller to use for driving
     *  @param ramp How long it will take for the robot to go from rest to max speed
     *  @param inhibitor The percent of max speed the bot can go at
     */
    public void driveRLTank(Joystick joy, double ramp, double inhibitor) { //Inhibitor is never used. Look into deleting
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
    
    /**
     * Directly sets drive motors to PercentOutputs
     * @param left Left motor output
     * @param right Right motor output
     */
    public void driveDirect(double left, double right) {
        left = (left > 1.0 ? 1.0 : (left < -1.0 ? -1.0 : left));
        right = (right > 1.0 ? 1.0 : (right < -1.0 ? -1.0 : right));
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
    }

    /**
     * Set the ramp for all talons
     * @param ramp The time to go from rest to max speed
     */
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
    
    public double getRightInches() {
        return Robot.SUB_DRIVE.rightMaster.getSelectedSensorPosition(0) * (6 * Math.PI) / 3996;
    }

    public double getLeftInches() {
        return Robot.SUB_DRIVE.leftMaster.getSelectedSensorPosition(0) * (6 * Math.PI) / 4071;
    }

    /**
     * Methods for all autonomous code
     */
    public static class AutoDrive {

        private static final String path = "/home/lvuser/";
        private static HashMap<String, Trajectory> trajectoryFiles;

        //The distance between left and right sides of the wheelbase
        static final double WHEELBASE_WIDTH = 2.1666;
        //The diameter of the wheels, but in meters
        public static final double WHEEL_DIAMETER = 6; //Check this number.

        //Various constants needed to generate a motion profile
        private final double TIME_STEP = .05;
        public final double MAX_VELOCITY = 2.5; // was 5.0
        private final double MAX_ACC = 1.5; // was 2.25
        private final double MAX_JERK = 60.0;
        //Allows the bot to achieve higher or lower speed quicker
        public final double ACC_GAIN = 0;

        //Configuration that stores all the values needed to configure the motion profile
        Trajectory.Config config;
        
        /**
         * Instantiate the config needed to generate trajectories
         */
        AutoDrive() {
            config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, TIME_STEP, MAX_VELOCITY, MAX_ACC, MAX_JERK);
        }
    
        public Trajectory getTrajectory(String name){
        	for(Entry<String, Trajectory> sets : trajectoryFiles.entrySet()){
        		if (sets.getKey().equals(name)) return sets.getValue();
        	}
        	return null;
        }

        /**
         * Generates a trajectory and saves it under the given filename
         * @param points Waypoints to turn into a trajectory
         * @param fileName Name of the trajectory
         */
        public void generateAndSaveTrajectory(Waypoint[] points, String fileName) {
            File save = new File(path + fileName);
            Trajectory toSave;
//            try {
//                if (!save.createNewFile()){
//                    return Pathfinder.readFromCSV(save);
//                }
//            } catch (IOException e){
//                DriverStation.reportError("Error generating trajectory", false);
//            }
            toSave = Pathfinder.generate(points, config);
            Pathfinder.writeToCSV(save, toSave);
            DriverStation.reportWarning("Trajectory Saved:" + fileName + ".csv", false);
        }
        
        public Trajectory getSavedTrajectory(Paths filePath){
        	return Pathfinder.readFromCSV(new File(path + filePath.getFileName()));
        }

        /**
         * Generates a tank modifer to make a trajectory work with tank drive
         * @param trajectory The trajectory to modify
         * @return The tankmodifer to make the trajectory work with tank drive
         */
        public TankModifier generateTankMod(Trajectory trajectory){
            return new TankModifier(trajectory).modify(WHEELBASE_WIDTH);
        }

        /**
         * Sets the talons to drive with a percent output
         * @param left Percent left side should drive at
         * @param right Percent right side should drive at
         */
        public void setTalons(double left, double right){
            leftMaster.set(ControlMode.PercentOutput, left);
            rightMaster.set(ControlMode.PercentOutput, right);
        }

        /**
         * Clears the position of the encoders
         */
        public void resetEncoders(){
            leftMaster.setSelectedSensorPosition(0,0,10);
            rightMaster.setSelectedSensorPosition(0,0,10);
        }

        /**
         * @return Position of the right encoder in native units
         */
        public double rightEncoderPos(){
            return rightMaster.getSelectedSensorPosition(0);
        }

        /**
         * @return Position of the right encoder in inches
         */
        public double rightEncoderInches() { 
//        	return nativeToInches(leftEncoderPos()); 
        	return rightEncoderPos();
        }

        /**
         * @return Position of the left encoder in native units
         */
        public double leftEncoderPos(){
           return leftMaster.getSelectedSensorPosition(0);
        }

        /**
         * @return Position of the left encoder in inches
         */
        public double leftEncoderInches() {
//        	return nativeToInches(leftEncoderPos()); 
        	return leftEncoderPos();
        }

        /**
         * @param nativeUnits Native units of the encoders to convert
         * @return The native units converted to inches
         */
        public double nativeToInches(double nativeUnits){
            return nativeUnits/204;
        }

        /**
         * @param inches Inches to convert to native units of the encoders
         * @return The inches in native units
         */
        public double inchesToNative(double inches){
            return inches*204;
        }
       
    }
}
