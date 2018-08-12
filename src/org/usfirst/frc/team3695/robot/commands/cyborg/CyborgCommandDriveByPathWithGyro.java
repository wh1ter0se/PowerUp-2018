package org.usfirst.frc.team3695.robot.commands.cyborg;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Util;

import static org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive.AutoDrive;

/**
 * Uses Jaci's Pathfinder class to autonomously get us from one point to another by following a set of waypoints
 */
public class CyborgCommandDriveByPathWithGyro extends Command {

    private TankModifier tankmod;

    private EncoderFollower leftEncoder;
    private EncoderFollower rightEncoder;

    private double initAngle;
    private double initGyro;

    //Good old PID values. Do not add I. Just don't
    private final static double P_LEFT = 3.000;
    private final static double I_LEFT = 0.000;
    private final static double D_LEFT = 0.000;

    private final static double P_RIGHT = 3.000;
    private final static double I_RIGHT = 0.000;
    private final static double D_RIGHT = 0.000;

    public CyborgCommandDriveByPathWithGyro(Trajectory traj, double initAngle) {
        requires(Robot.SUB_DRIVE);
        this.tankmod = Robot.SUB_DRIVE.autoDrive.generateTankMod(traj);
        this.initAngle = initAngle;
    }

    public void initialize() {
        leftEncoder = new EncoderFollower(tankmod.getLeftTrajectory());
        rightEncoder = new EncoderFollower(tankmod.getRightTrajectory());

//        Robot.SUB_DRIVE.gyro.reset();
        Robot.SUB_DRIVE.autoDrive.resetEncoders();
        initGyro = Robot.SUB_DRIVE.gyro.getAngle();

        leftEncoder.configureEncoder((int) Robot.SUB_DRIVE.autoDrive.leftEncoderPos(), 4071, AutoDrive.WHEEL_DIAMETER / 12);
        rightEncoder.configureEncoder((int) Robot.SUB_DRIVE.autoDrive.rightEncoderPos(), 3996, AutoDrive.WHEEL_DIAMETER / 12);

        leftEncoder.configurePIDVA(
                Util.getAndSetDouble("Path-Left-P", P_LEFT),
                Util.getAndSetDouble("Path-Left-I", I_LEFT),
                Util.getAndSetDouble("Path-Left-D", D_LEFT),
//                Util.getAndSetDouble("Max Velocity", 1/Robot.SUB_DRIVE.autoDrive.MAX_VELOCITY),
                1d / Robot.SUB_DRIVE.autoDrive.MAX_VELOCITY,
                Util.getAndSetDouble("Accel Gain", Robot.SUB_DRIVE.autoDrive.ACC_GAIN));
        rightEncoder.configurePIDVA(
                Util.getAndSetDouble("Path-Right-P", P_RIGHT),
                Util.getAndSetDouble("Path-Right-I", I_RIGHT),
                Util.getAndSetDouble("Path-Right-D", D_RIGHT),
//                Util.getAndSetDouble("Max Velocity", 1/Robot.SUB_DRIVE.autoDrive.MAX_VELOCITY),
                1d / Robot.SUB_DRIVE.autoDrive.MAX_VELOCITY,
                Util.getAndSetDouble("Accel Gain", Robot.SUB_DRIVE.autoDrive.ACC_GAIN));

        DriverStation.reportWarning("Pathfinder configuration complete", false);
    }

    protected void execute() {
        Robot.SUB_DRIVE.setRamps(0);

        //Now that we have setup for this drive, it's time to roll out!
        double leftOutput = leftEncoder.calculate((int) Robot.SUB_DRIVE.autoDrive.leftEncoderPos() * -1);
        double rightOutput = rightEncoder.calculate((int) Robot.SUB_DRIVE.autoDrive.rightEncoderPos());

        //All of this will account for any turning the robot makes when it drives
        //Way better than what we had with
        //Make sure gyro is in degrees!!!


//        double gyroHeading = Robot.SUB_DRIVE.gyro.getAngle();
//        	gyroHeading %= 360;
//        	gyroHeading *= -1;
        double gyroHeading = convertedGyro();
        gyroHeading %= 360;
        if (gyroHeading < 0) {
            gyroHeading = 360 - Math.abs(gyroHeading);
        }
        SmartDashboard.putNumber("Gyro Heading", gyroHeading);

        //The sides of the robot are in parallel and therefore are always the same
        //So lets just use left
        double desiredHeading = Pathfinder.r2d(leftEncoder.getHeading());
        SmartDashboard.putNumber("Desired Heading", desiredHeading);
        //The difference in angle we want to reach
        double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        double turn = .8 * (-1.0 / 80.0) * angleDifference; //Blame Jaci. Not quite sure why this is what it is.

//    	Robot.SUB_DRIVE.autoDrive.setTalons(leftOutput + turn, rightOutput - turn);
        Robot.SUB_DRIVE.autoDrive.setTalons(leftOutput, rightOutput);

        SmartDashboard.putNumber("leftOutput", leftOutput);
        SmartDashboard.putNumber("rightOutput", rightOutput);
        SmartDashboard.putNumber("Left Inches", ((int) Robot.SUB_DRIVE.autoDrive.leftEncoderPos() * -1) / 4071 * 6 * Math.PI);
        SmartDashboard.putNumber("Right Inches", (int) Robot.SUB_DRIVE.autoDrive.rightEncoderPos() / 3996 * 6 * Math.PI);
    }

    protected boolean isFinished() {
        return leftEncoder.isFinished() && rightEncoder.isFinished();
    }

    protected void end() {
        Robot.SUB_DRIVE.autoDrive.setTalons(0, 0);
    }

    private double convertedGyro() {
        return Robot.SUB_DRIVE.gyro.getAngle() - initGyro + initAngle;
//    	return Robot.SUB_DRIVE.gyro.getAngle();
    }
}
