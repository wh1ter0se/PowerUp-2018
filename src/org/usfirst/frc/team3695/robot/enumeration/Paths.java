package org.usfirst.frc.team3695.robot.enumeration;

public enum Paths {
    LEFT_NATIVE_SWITCH("",""),
    LEFT_FOREIGN_SWITCH("",""),
    RIGHT_NATIVE_SWITCH("",""),
    RIGHT_FOREIGN_SWITCH("",""),
    LEFT_NATIVE_SCALE("",""),
    LEFT_FOREIGN_SCALE("",""),
    RIGHT_NATIVE_SCALE("",""),
    RIGHT_FOREIGN_SCALE("","");

    private String left;
    private String right;

    /**
     * All possible trajectories in autonomous
     * @param left Left trajectory file path
     * @param right Right trajectory file path
     */
    Paths(String left, String right){
        this.left = left;
        this.right = right;
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
}
