package org.usfirst.frc.team3695.robot.commands.cyborg;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Mast;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemMast;

public class CyborgCommandGrow extends Command {
	//Where to go
    private Mast position;
    //Time it starts and how long to wait before forcefully ending regardless of results
    private long startTime;
	private long timeout;
	//If the command is finished
	private boolean isFinished;

    public CyborgCommandGrow(Mast position, long timeout) {
        requires(Robot.SUB_MAST);
        this.position = position;
        isFinished = false;
        this.timeout = timeout;
    }

    protected boolean isFinished() {
    	return isFinished || (startTime + timeout < System.currentTimeMillis());
    }

    protected void initialize() {
        startTime = System.currentTimeMillis();
    	DriverStation.reportWarning("RAISING INIT", false);
        isFinished = false;
    }

    protected void execute() {
    	DriverStation.reportWarning("RAISING " + position, false);
    	switch (position) {
	        case PINION_UP:
				if (Robot.SUB_MAST.upperPinionLimit.get()) {
					Robot.SUB_MAST.rightPinion.set(ControlMode.PercentOutput, SubsystemMast.rightPinionate(1));
					Robot.SUB_MAST.leftPinion.set(ControlMode.PercentOutput, SubsystemMast.leftPinionate(1));
				} else {
					isFinished = true;
				}
	            break;
	        case PINION_DOWN:
				if (Robot.SUB_MAST.lowerPinionLimit.get()) {
					Robot.SUB_MAST.rightPinion.set(ControlMode.PercentOutput, SubsystemMast.rightPinionate(-1));
					Robot.SUB_MAST.leftPinion.set(ControlMode.PercentOutput, SubsystemMast.leftPinionate(-1));
				} else {
					isFinished = true;
				}
	            break;
	        case SCREW_UP:
	    		if (Robot.SUB_MAST.upperScrewLimit.get()){
	    			Robot.SUB_MAST.screw.set(ControlMode.PercentOutput, SubsystemMast.screwify(-1));
				} else {
					isFinished = true;
				}
	            break;
	        case SCREW_DOWN:
	    		if (Robot.SUB_MAST.lowerScrewLimit.get()){
	    			Robot.SUB_MAST.screw.set(ControlMode.PercentOutput, SubsystemMast.screwify(1));
				} else {
					isFinished = true;
				}
	    		break;
    	}
    }

    protected void end() {
    	DriverStation.reportWarning("DONE RAISING", false);
    	Robot.SUB_MAST.leftPinion.set(ControlMode.PercentOutput, 0);
    	Robot.SUB_MAST.rightPinion.set(ControlMode.PercentOutput, 0);
    	Robot.SUB_MAST.screw.set(ControlMode.PercentOutput, 0);
        isFinished = false;
    }

    protected void interrupted() {
        end();
    }
}
