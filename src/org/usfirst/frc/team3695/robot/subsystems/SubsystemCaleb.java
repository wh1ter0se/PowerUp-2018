package org.usfirst.frc.team3695.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.commands.manual.ManualCommandIlliteracy;

import java.lang.reflect.Field;

/**
 * This is the final form of the Robot.
 */
public class SubsystemCaleb extends Subsystem {

    static TalonSRX right = new TalonSRX(0);
    static TalonSRX left = new TalonSRX(0);

    /*
      Steal the talons from the driving subsystem to keep program away
     */
    static {
        try {
            Field rightMaster = SubsystemDrive.class.getDeclaredField("rightMaster");
            rightMaster.setAccessible(true);
            right = (TalonSRX) rightMaster.get(Robot.SUB_DRIVE);


            Field leftMaster = SubsystemDrive.class.getDeclaredField("leftMaster");
            leftMaster.setAccessible(true);
            left = (TalonSRX) leftMaster.get(Robot.SUB_DRIVE);
        } catch (Exception e) {
            DriverStation.reportWarning("Caleb's brain has failed", false);
        }
    }

    /**
     * Properly construct caleb
     */
    protected void initDefaultCommand() {
        setDefaultCommand(new ManualCommandIlliteracy());
    }

    /**
     * This does very little. Trust me on this.
     */
    public SubsystemCaleb(){}

    /**
     * Fully implement his lizard brain.
     * It's too small to take any parameters
     * However, it still manages to drive in some way
     */
    public void lizardBrain() {
        double speed = Math.random() * (Math.random() > 0.5 ? 1 : -1);
        boolean turning = (Math.random() > 0.5);
        double turn = 0;

        if (turning) {
            turn = Math.random() * (Math.random() > 0.5 ? 1 : -1);
        }

        drive(speed, turn);
    }

    /**
     * Purposely avoid driving with what the programmers want me to drive with
     * It just needs to get the job done
     * This will 100% break everything too
     */
    private void drive(double speed, double turn) {
        right.set(ControlMode.PercentOutput, speed + turn);
        left.set(ControlMode.PercentOutput, speed - turn);
    }
    
}