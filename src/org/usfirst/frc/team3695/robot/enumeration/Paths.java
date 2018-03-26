package org.usfirst.frc.team3695.robot.enumeration;

public enum Paths {
    LEFT_NATIVE_SWITCH("leftSwitch_left.csv","leftSwitch_right.csv", "leftSwitch_tank.csv"),
    LEFT_FOREIGN_SWITCH("","", ""),
    RIGHT_NATIVE_SWITCH("","", ""),
    RIGHT_FOREIGN_SWITCH("","",""),
    LEFT_NATIVE_SCALE("","",""),
    LEFT_FOREIGN_SCALE("","",""),
    RIGHT_NATIVE_SCALE("","",""),
    RIGHT_FOREIGN_SCALE("","","");

    private String left;
    private String right;
    private String tankMod;
    /**
     * All possible trajectories in autonomous
     * @param left Left trajectory file path
     * @param right Right trajectory file path
     */
    Paths(String left, String right, String tankMod){
        this.left = left;
        this.right = right;
        this.tankMod = tankMod;
    }

    /**
     * @return Left trajectory file path
     */
    public String getLeft() {
        return left;
    }

    /**
     * @return Right trajectory file path
     */
    public String getRight() {
        return right;
    }
    
    public String getTank() {
    	return tankMod;
    }
}
