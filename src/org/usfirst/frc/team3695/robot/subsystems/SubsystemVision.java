package org.usfirst.frc.team3695.robot.subsystems;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Constants.VisionConstants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Bot;

import java.util.Arrays;
import java.util.Optional;

/**
 * Controls the vision of the bot (cameras)
 */
public class SubsystemVision extends Subsystem {

    private static Mat failImage;

    protected void initDefaultCommand() {
    }

    /**
     * Creates the fail image to put up when exceptions occur
     */
    public SubsystemVision() {
        failImage = new Mat(VisionConstants.CAM_WIDTH, VisionConstants.CAM_HEIGHT, CvType.CV_8UC3, new Scalar(0, 0, 0));
    }

    //Camera streams are placed in separate threads as recommended by FIRST

    /**
     * Start the concat camera in a separate thread
     */
    public void startConcatCameraThread() { //Never used, but should be kept for future teams to have as a reference
        new Thread(this::concatCameraStream).start();
    }

    /**
     * Start the screw camera stream in a separate thread
     */
    public void startScrewCameraThread() {
        new Thread(this::screwCameraStream).start();
    }

    /**
     * Start the frame camera stream in a seperate thread
     */
    public void startFrameCameraThread() { //Never used, can likely be deleted if we're never going to use the frame camera
        new Thread(this::frameCameraStream).start();
    }

    /**
     * Create the screw camera stream
     */
    private void screwCameraStream() {
        UsbCamera cameraScrew = CameraServer.getInstance().startAutomaticCapture("Screw", VisionConstants.SCREW_ID);

        //The sinks do nothing except ensure the camera is never deconstructed with the garbage collector
        CvSink cvsinkScrew = new CvSink("screwSink");
        cvsinkScrew.setSource(cameraScrew);
        cvsinkScrew.setEnabled(true);

        //Matrix to store
        Mat streamImages = new Mat();

        CvSource outputScrew = CameraServer.getInstance().putVideo("Screw", VisionConstants.CAM_WIDTH, VisionConstants.CAM_HEIGHT);
        while (!Thread.interrupted()) {
            try {
                cvsinkScrew.grabFrame(streamImages);
                Core.rotate(streamImages, streamImages, Core.ROTATE_180);
                outputScrew.putFrame(streamImages);
            } catch (Exception cameraFail) {
                DriverStation.reportWarning("Screw Camera: " + cameraFail.toString(), false);
                outputScrew.putFrame(failImage);
            }
        }
    }

    /**
     * Create the frame camera stream
     */
    private void frameCameraStream() {
        UsbCamera cameraFrame = CameraServer.getInstance().startAutomaticCapture("Frame", VisionConstants.HOOK_ID);

        CvSink cvsinkFrame = new CvSink("frameSink");
        cvsinkFrame.setSource(cameraFrame);
        cvsinkFrame.setEnabled(true);

        Mat streamImages = new Mat();

        CvSource outputFrame = CameraServer.getInstance().putVideo("Frame", VisionConstants.CAM_WIDTH, VisionConstants.CAM_HEIGHT);
        while (!Thread.interrupted()) {
            try {
                cvsinkFrame.grabFrame(streamImages);
                if ((Robot.bot == Bot.TEUFELSKIND && Constants.TEUFELSKIND.FRAME_CAM_FLIP)
                        || (Robot.bot == Bot.OOF && Constants.OOF.FRAME_CAM_FLIP)) {
                    Core.rotate(streamImages, streamImages, Core.ROTATE_180);
                }
                outputFrame.putFrame(streamImages);
            } catch (Exception cameraFail) {
                DriverStation.reportWarning("Frame Camera: " + cameraFail.toString(), false);
                outputFrame.putFrame(failImage);
            }
        }
    }

    /**
     * Start both the left and right camera streams and combine them into a single one which is then pushed
     * to an output stream titled Concat.
     * This method should only be used for starting the camera stream.
     */
    private void concatCameraStream() {
        UsbCamera cameraLeft = CameraServer.getInstance().startAutomaticCapture("Left", VisionConstants.LEFT_ID);
        UsbCamera cameraRight = CameraServer.getInstance().startAutomaticCapture("Right", VisionConstants.RIGHT_ID);

        //Dummy sinks to keep camera connections open.
        CvSink cvsinkLeft = new CvSink("leftSink");
        cvsinkLeft.setSource(cameraLeft);
        cvsinkLeft.setEnabled(true);
        CvSink cvsinkRight = new CvSink("rightSink");
        cvsinkRight.setSource(cameraRight);
        cvsinkRight.setEnabled(true);

        //Matrices to store each image from the cameras.
        Optional<Mat> leftSource = Optional.of(new Mat());
        Optional<Mat> rightSource = Optional.of(new Mat());

        //Puts the combined video on the SmartDashboard
        //The width is multiplied by 2 as the dimensions of the stream will have a width two times that of a single webcam
        CvSource outputStream = CameraServer.getInstance().putVideo("Concat", 2 * VisionConstants.CAM_WIDTH, VisionConstants.CAM_HEIGHT);
        //Storage for concatenation of both matrices
        Mat concat = new Mat();
        while (!Thread.interrupted()) {
            //Provide each mat with the current frame
            cvsinkLeft.grabFrame(leftSource.get());
            cvsinkRight.grabFrame(rightSource.get());

            //Combine the frames into one image. If either failed to grab, replace it with the fail image and concat with that.
            Core.hconcat(Arrays.asList(leftSource.orElse(failImage), rightSource.orElse(failImage)), concat);
            outputStream.putFrame(concat);
        }
    }
}
