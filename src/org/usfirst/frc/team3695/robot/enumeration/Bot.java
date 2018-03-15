package org.usfirst.frc.team3695.robot.enumeration;

public enum Bot {
	OOF("OOF (Practice)"), TEUFELSKIND("TEUFELSKIND (Competition)");
	private final String name;
	Bot(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}
