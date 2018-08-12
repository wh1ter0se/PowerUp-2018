package org.usfirst.frc.team3695.robot.enumeration;

public enum Mast {
    SCREW_UP("Screw up", "Upper Screw"),
    SCREW_DOWN("Screw down", "Lower Screw"),
    PINION_UP("Pinion up", "Upper Pinion"),
    PINION_DOWN("Pinion down", "Lower Pinion");

    private final String name;
    private final String coolerName;
    
    Mast(String name, String coolerName) {
        this.name = name;
        this.coolerName = coolerName;
    }

    public String toString() {
        return name;
    }
    
    public String toBetterString() {
    	return coolerName;
    }
}

