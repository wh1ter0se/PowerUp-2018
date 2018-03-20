package org.usfirst.frc.team3695.robot.commands.manual;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3695.robot.OI;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive;
import org.usfirst.frc.team3695.robot.util.Util;

/** manually command the robot with joysticks */
public class ManualCommandDrive extends Command {
	
    public ManualCommandDrive() {
        requires(Robot.SUB_DRIVE);
    }

    protected void initialize() {}

    protected void execute() {
    	
    	Robot.SUB_DRIVE.setInverts();
    	
        SmartDashboard.putBoolean("Forza Dampener (Docking mode)", SubsystemDrive.docking);
        SmartDashboard.putBoolean("Reversing mode", SubsystemDrive.reversing);
        SmartDashboard.putBoolean("Narrower (Turn inhibitor)", SubsystemDrive.narrowing);
        //Print PID values to smartdash?
        SmartDashboard.putNumber("Match Time", DriverStation.getInstance().getMatchTime());

    	switch (Robot.SUB_DRIVE.drivetrain) {
    		case ROCKET_LEAGUE:
    			Robot.SUB_DRIVE.driveRLTank(OI.DRIVER, Util.getAndSetDouble("Rocket Ramp", .75), Util.getAndSetDouble("Drive Inhibitor", 1));
    			break;
    		case FORZA: 
    			Robot.SUB_DRIVE.driveForza(OI.DRIVER, Util.getAndSetDouble("Forza Ramp", .75), Util.getAndSetDouble("Radius", 1), Util.getAndSetDouble("Drive Inhibitor", 1));
    			break;
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
}
