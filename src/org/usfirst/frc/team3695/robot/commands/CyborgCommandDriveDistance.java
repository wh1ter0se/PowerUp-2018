
package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive.PID;
import org.usfirst.frc.team3695.robot.util.Util;

public class CyborgCommandDriveDistance extends Command {

    public static final long TIME_WAIT = 1000;
    public double inches;
    private long time;
    private boolean inRange;

    public CyborgCommandDriveDistance(double inches) {
        this.inches = inches;
        requires(Robot.SUB_DRIVE);
        
    }

    protected void initialize() {
//    	Robot.SUB_DRIVE.pid.zeroEncoders(); Reset does this
    	inRange = false;
    	Robot.SUB_DRIVE.pid.reset();
    	time = System.currentTimeMillis() + TIME_WAIT;
    	inches = Util.getAndSetDouble("Drive Distance Inches", 10); // take out in final version
    	PID.setPIDF(Util.getAndSetDouble("P", .5),
				Util.getAndSetDouble("I", 0),
				Util.getAndSetDouble("D", 0),
				Util.getAndSetDouble("F", 0));
    	inRange = Robot.SUB_DRIVE.driveDistance(inches, inches);

    }

    protected void execute() {
    	DriverStation.reportWarning("DRIVING " + inches + " INCHES", false);
    	SmartDashboard.putNumber("Left Encoder Inches", Robot.SUB_DRIVE.pid.getLeftInches());
    	SmartDashboard.putNumber("Right Encoder Inches", Robot.SUB_DRIVE.pid.getRightInches());

        SmartDashboard.putNumber("Error", Robot.SUB_DRIVE.pid.getError());
    }

    protected boolean isFinished() {
        if(!inRange) {
            time = System.currentTimeMillis() + TIME_WAIT;
        }
        return time < System.currentTimeMillis();
    }

    protected void end() {
        DriverStation.reportWarning("CyborgCommandDriveDistance finished", false);
        Robot.SUB_DRIVE.driveDirect(0, 0);
    }

    protected void interrupted() {}
}