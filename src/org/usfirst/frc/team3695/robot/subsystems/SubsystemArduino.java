package org.usfirst.frc.team3695.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3695.robot.Constants;

//This is all uncertain and if it works will be commented properly later
public class SubsystemArduino extends Subsystem {

    I2C i2c;
    byte[] toSend;

    protected void initDefaultCommand() {}

    public SubsystemArduino() {
        i2c = new I2C(I2C.Port.kOnboard, Constants.I2C_DEVICE_ADDRESS);
        toSend = new byte[1];
//        test();
    }

    void test(){
        while (true){
            toSend[0] = 76;
            i2c.transaction(toSend, 1, null, 0);
            Timer.delay(1);
            toSend[0] = 72;
            i2c.transaction(toSend, 1, null, 0);
            Timer.delay(1);
        }
    }
    
    public void redline() {
    	
    }




}