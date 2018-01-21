package org.usfirst.frc.team3695.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.ArrayList;

/**
 * Contains methods used for anything vision
 */
public class Vision extends IterativeRobot {

    /// Two cameras for double FOV
    private UsbCamera cameraLeft;
    private UsbCamera cameraRight;

    void startCameraThread(){
        //Places the vision in a separate thread from everything else as recommended by FIRST.
        new Thread(this::concatCameraStream).start();

    }

    /**
     * Start both the left and right camera streams and combine them into a single one which is then pushed
     * to an output stream titled Concat.
     * This method should only be used for starting the camera stream.
     */
        private void concatCameraStream() {
        cameraLeft = CameraServer.getInstance().startAutomaticCapture("Left", 0);
        cameraRight = CameraServer.getInstance().startAutomaticCapture("Right", 1);

        /// Dummy sinks to keep camera connections open.
        CvSink cvsinkLeft = new CvSink("leftSink");
        cvsinkLeft.setSource(cameraLeft);
        cvsinkLeft.setEnabled(true);
        CvSink cvsinkRight = new CvSink("rightSink");
        cvsinkRight.setSource(cameraRight);
        cvsinkRight.setEnabled(true);

        /// Matrices to store each image from the cameras.
        Mat leftSource = new Mat();
        Mat rightSource = new Mat();

        /// The ArrayList of left and right sources is needed for the hconcat method used to combine the streams
        ArrayList<Mat> sources = new ArrayList<>();
        sources.add(leftSource);
        sources.add(rightSource);

        /// Concatenation of both matrices
        Mat concat = new Mat();

        /// Puts the combined video on the SmartDashboard (I think)
        /// The width is multiplied by 2 as the dimensions of the stream will have a width two times that of a single webcam
        CvSource outputStream = CameraServer.getInstance().putVideo("Concat", 2*Constants.CAM_WIDTH, Constants.CAM_HEIGHT);

        while (!Thread.interrupted()) {
            /// Provide each mat with the current frame
            cvsinkLeft.grabFrame(leftSource);
            cvsinkRight.grabFrame(rightSource);
            /// Combine the frames into a single mat in the Output and stream the image.
            Core.hconcat(sources, concat);
            outputStream.putFrame(concat);
        }
    }
}
