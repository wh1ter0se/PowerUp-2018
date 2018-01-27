package org.usfirst.frc.team3695.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3695.robot.util.ArduinoConstants;

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

    public void sendData(int data){
    	toSend[0] = (byte)data;
    	i2c.transaction(toSend, 1, null, 0);
    }

    public byte[] recieveData(int request, int size){
        byte[] recieved = new byte[size];
        toSend[0] = (byte)request;
        i2c.transaction(toSend, 1, recieved, size);
        return recieved;
    }

    public void sendAllianceColor(DriverStation.Alliance color){
    	sendData(color == DriverStation.Alliance.Blue ? ArduinoConstants.BLUE : ArduinoConstants.RED);
    }
    
//    public void sendStage(){
//    	DriverStation game = DriverStation.getInstance();
//    	if(game.isAutonomous()){
//    		sendData(ArduinoConstants.AUTONOMOUS);
//    	} else if (game.isOperatorControl()){
//    		sendData(ArduinoConstants.OPERATOR_CONTROL);
//    	} else {
//    		sendData(ArduinoConstants.DISABLED);
//    	}
//    }
}