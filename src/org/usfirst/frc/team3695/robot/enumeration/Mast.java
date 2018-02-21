package org.usfirst.frc.team3695.robot.enumeration;

public enum Mast {
    SCREW_UP("Screw up"),
    SCREW_DOWN("Screw down"),
    PINION_UP("Pinion up"),
    PINION_DOWN("Pinion down");

    private final String name;
    Mast(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}

