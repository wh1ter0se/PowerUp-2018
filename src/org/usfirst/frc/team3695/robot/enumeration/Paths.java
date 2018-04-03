package org.usfirst.frc.team3695.robot.enumeration;

public enum Paths {
    LEFT_NATIVE_SWITCH("leftswitch_native.csv"),
    LEFT_FOREIGN_SWITCH("leftswitch_foreign.csv"),
    RIGHT_NATIVE_SWITCH("rightswitch_native.csv"),
    RIGHT_FOREIGN_SWITCH("rightswitch_foreign.csv"),
    CENTER_SWITCH_LEFT("centerswitch_left.csv"),
    CENTER_SWITCH_RIGHT("centerswitch_right.csv"),
    LEFT_NATIVE_SCALE("leftscale_native.csv"),
	LEFT_NATIVE_SCALE_ROT("leftscale_native_rot.csv"),
    LEFT_FOREIGN_SCALE("leftscale_foreign.csv"),
    RIGHT_NATIVE_SCALE("rightscale_native.csv"),
    RIGHT_NATIVE_SCALE_ROT("rightscale_native_rot.csv"),
    RIGHT_FOREIGN_SCALE("rightscale_foreign.csv"),
	LEFT_SETUP("leftsetup.csv"),
	RIGHT_SETUP("rightsetup.csv");

    private String trajFileName;
    /**
     * All possible trajectories in autonomous
     * @param left Left trajectory file path
     * @param right Right trajectory file path
     */
    Paths(String fileName){
        this.trajFileName = fileName;
    }
    
    public String getFileName() {
    	return trajFileName;
    }
}
