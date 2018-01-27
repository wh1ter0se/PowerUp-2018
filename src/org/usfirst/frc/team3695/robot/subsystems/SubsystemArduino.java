package org.usfirst.frc.team3695.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls for the Arduino
 */
public class SubsystemArduino extends Subsystem {

    I2C i2c;
    byte[] toSend;

    protected void initDefaultCommand() {}

    public SubsystemArduino() {
        i2c = new I2C(I2C.Port.kOnboard, 4);
        toSend = new byte[1];
    }
    
    //Send data through 
    private void sendData(int data){
    	toSend[0] = (byte)data;
    	i2c.transaction(toSend, 1, null, 0);
    }
    
    /** 
     * For testing purposes only. Do not send data to the arduino through this.
     * */
    public void sendArbitraryData(int data){
    	toSend[0] = (byte)data;
    	i2c.transaction(toSend, 1, null, 0);
    }

    public void sendAllianceColor(DriverStation.Alliance color){
    	sendData((color == DriverStation.Alliance.Blue ? 1 : 2));
    }
    
    public void sendStage(){
    	DriverStation game = DriverStation.getInstance();
    	if(game.isAutonomous()){
    		sendData(3);
    	} else if (game.isOperatorControl()){
    		sendData(4);
    	} else {
    		sendData(200);
    	}
    }
}