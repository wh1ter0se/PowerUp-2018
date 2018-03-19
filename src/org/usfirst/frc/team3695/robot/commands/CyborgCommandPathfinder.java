package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Waypoint;

public class CyborgCommandPathfinder extends Command {

    Waypoint[] waypoints;
    public CyborgCommandPathfinder(Waypoint[] points) {
        waypoints = points;
    }

    public void initialize(){

    }

    protected void execute(){

    }

    protected boolean isFinished() {
        return false;
    }
}
