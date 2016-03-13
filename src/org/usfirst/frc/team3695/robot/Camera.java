package org.usfirst.frc.team3695.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.RGBValue;
import com.ni.vision.NIVision.TextAlignment;
import com.ni.vision.NIVision.VerticalTextAlignment;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Camera extends Thread implements Runnable {
	private final USBCamera frontCam;
	private boolean frontCamOn = false;
	private final USBCamera rearCam;
	private boolean rearCamOn = false;
	
	private int cameraView = 0;
	
	/**
	 * Selectable cameras.
	 */
	public static final int NO_CAM = 0,
							FRONT_PROCCESSED = 1,
							FRONT_CAM = 2,
							REAR_CAM = 3;
	
	private Image frontFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	private Image rearFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	
	private Image waitFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	private Image noFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

	
	/**
	 * Creates a new set of cameras. A set of cameras consists of a front and a rear
	 * camera. If no cameras exist, or some have a problem, this constructor will handle
	 * that situation without throwing an exception. In theory, it'll print a "No Camera Feed!"
	 * message to the camera viewer if there is a problem.
	 */
	public Camera() {
		NIVision.imaqSetImageSize(noFrame, 640, 480);
		NIVision.OverlayTextOptions settings = new NIVision.OverlayTextOptions(
				"Times New Roman", 48, 0, 0, 0, 0, TextAlignment.LEFT, VerticalTextAlignment.BASELINE, new RGBValue(0, 0, 0, 255), 0.0); //dear god why
		NIVision.imaqOverlayText(noFrame, new NIVision.Point(0,0), "No Camera Feed!", NIVision.RGB_RED, settings, "0");
		
		NIVision.imaqSetImageSize(waitFrame, 640, 480);
		NIVision.imaqOverlayText(waitFrame, new NIVision.Point(0,0), "Loading...", NIVision.RGB_RED, settings, "0");
		
		frontCam = startCam("front camera", CameraConstants.FRONT_CAM_NAME);
		rearCam = startCam("rear camera",CameraConstants.REAR_CAM_NAME);
		
		init();
	}
	
	private void init() {
		viewCam(NO_CAM); //Disable all cameras.
		if(frontCam != null) {
			frontCam.setWhiteBalanceManual(USBCamera.WhiteBalance.kFixedIndoor);
			frontCam.setExposureManual(CameraConstants.FRONT_EXPOSURE());
			frontCam.setBrightness(CameraConstants.FRONT_BRIGHTNESS());
			frontCam.setFPS(20);
			frontCam.setSize(640, 480);
			frontCam.updateSettings();
			frontCam.openCamera();
			frontCam.startCapture();
			DriverStation.reportWarning("Front camera started!", false);
		}
		if(rearCam != null) {
			rearCam.setWhiteBalanceManual(USBCamera.WhiteBalance.kFixedIndoor);
			rearCam.setFPS(20);
			rearCam.setSize(640, 480);
			rearCam.updateSettings();
			rearCam.openCamera();
			rearCam.startCapture();
			DriverStation.reportWarning("Rear camera started!", false);
		}
		CameraServer.getInstance().setQuality(CameraConstants.SERVER_QUALITY());
		viewCam(FRONT_CAM); //Restart cameras
	}

	public void run() {
		while(true) {
			try {
				out: switch(cameraView) {
				case FRONT_CAM:
					frontCam.getImage(frontFrame);
					CameraServer.getInstance().setImage(frontFrame);
					break out;
				case REAR_CAM:
					rearCam.getImage(rearFrame);
					CameraServer.getInstance().setImage(rearFrame);
					break out;
				default:
				case NO_CAM:
					CameraServer.getInstance().setImage(noFrame);
					break out;
				}
				try {
					Thread.sleep((long)(1000.0/25.0)); //Sleep for slightly less than 20fps (loop runs at 25fps)?
				} catch (InterruptedException e) {
					//consume
				}
			} catch (Exception e) {
				DriverStation.reportError("The main thread exited because of :" + e.getStackTrace(), true);
				break;
			}
		}
	}
	
	/**
	 * This method attempts to switch the camera to a new camera feed.
	 * @param cam Use the constants Camera.NO_CAM, Camera.FRONT_PROCCESSED
	 * Camera.FRONT_CAM or Camera.REAR_CAM to switch the camera to a
	 * different feed. 
	 */
	public synchronized void viewCam(int cam) {
		if(cameraView == cam) {
			return;
		}
		CameraServer.getInstance().setImage(waitFrame);
		switch(cam) {
		case FRONT_CAM:
			if(rearCamOn) {
				rearCam.stopCapture();
				rearCam.closeCamera();
			}
			frontCam.openCamera();
			frontCam.startCapture();
			cameraView = FRONT_CAM;
			break;
		case REAR_CAM:
			if(frontCamOn) {
				frontCam.stopCapture();
				frontCam.closeCamera();
			}
			rearCam.openCamera();
			rearCam.startCapture();
			cameraView = REAR_CAM;
			break;
		default:
		case NO_CAM:
			if(frontCamOn) {
				frontCam.stopCapture();
				frontCam.closeCamera();
			}
			if(rearCamOn) {
				rearCam.stopCapture();
				rearCam.closeCamera();
			}
			cameraView = NO_CAM;
		}
	}
	
	/**
	 * Starts a camera.
	 * @param humanName A human readable name for the camera
	 * @param camName The camera string as retrieved by the RoboRIO dash board
	 * @return A USB camera if no exception is thrown. Null otherwise.
	 */
	private USBCamera startCam(String humanName, String camName) {
		USBCamera cam = null;
		try{
			cam = new USBCamera(camName);
		} catch (Exception e) {
			DriverStation.reportError("Could not start the " + humanName + " nammed \"" + camName + "\": " + e.getStackTrace(), true);
		}
		return cam;
	}
}
