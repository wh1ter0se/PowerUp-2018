package org.usfirst.frc.team3695.robot.enumeration;

public enum Drivetrain {
	ROCKET_LEAGUE("Rocket League"),
	FORZA("Forza (Colton)"),
	BROGAN("Brogan Drive"),
	REV("VROOOOOM");

	private final String name;
	Drivetrain(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}
