package org.usfirst.frc.team3695.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.ArrayList;

//As I don't know where I should be putting this,
//I made a different class to store this.
//Move it wherever it should be.
public class Vision extends IterativeRobot {

    //Two cameras for double FOV
    private UsbCamera cameraLeft;
    private UsbCamera cameraRight;


    public void robotInit() {
        //Places the vision in a separate thread from everything else as recommended by FIRST
        //It should never be accessed by other code, so protection isn't necessary.
        new Thread(() -> {
            //Initialize cameras as the left and right respectively.
            cameraLeft = CameraServer.getInstance().startAutomaticCapture("Left", 0);
            cameraRight = CameraServer.getInstance().startAutomaticCapture("Right", 1);

            //Dummy sinks to keep camera connections open.
            CvSink cvsinkLeft = new CvSink("leftSink");
            cvsinkLeft.setSource(cameraLeft);
            cvsinkLeft.setEnabled(true);
            CvSink cvsinkRight = new CvSink("rightSink");
            cvsinkRight.setSource(cameraRight);
            cvsinkRight.setEnabled(true);

            //Matrices to store each image from the cameras.
            //Labeled left and right respectively.
            Mat leftSource = new Mat();
            Mat rightSource = new Mat();

            //The arraylist of left and right sources is needed for concatenating
            ArrayList<Mat> sources = new ArrayList<>();
            sources.add(leftSource);
            sources.add(rightSource);

            //Concatenation of both matrices
            Mat concat = new Mat();

            //Puts the combined video on the SmartDashboard (I think)
            CvSource outputStream = CameraServer.getInstance().putVideo("Concat", 3840, 1080);

            while (!Thread.interrupted()) {
                //Provide each mat with the current frame
                cvsinkLeft.grabFrame(leftSource);
                cvsinkRight.grabFrame(rightSource);
                //Combine the frames into a single mat in the Output
                Core.hconcat(sources, concat);
                outputStream.putFrame(concat);
            }
        }).start();

    }
}
