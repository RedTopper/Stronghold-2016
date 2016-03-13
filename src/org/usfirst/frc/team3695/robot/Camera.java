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
	
	public static final int NO_CAM = 0,
							FRONT_PROCCESSED = 1,
							FRONT_CAM = 2,
							REAR_CAM = 3;
	
	private Image frontFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	private Image rearFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	
	private Image waitFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	private Image noFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

	
	public Camera() {
		NIVision.imaqSetImageSize(noFrame, 640, 480);
		NIVision.OverlayTextOptions settings = new NIVision.OverlayTextOptions(
				"Times New Roman", 48, 0, 0, 0, 0, TextAlignment.LEFT, VerticalTextAlignment.BASELINE, new RGBValue(0, 0, 0, 255), 0.0); //dear god
		NIVision.imaqOverlayText(noFrame, new NIVision.Point(0,0), "No Camera Feed!", NIVision.RGB_RED, settings, "0");
		
		NIVision.imaqSetImageSize(waitFrame, 640, 480);
		NIVision.imaqOverlayText(waitFrame, new NIVision.Point(0,0), "Loading...", NIVision.RGB_RED, settings, "0");
		
		frontCam = startCam("front camera", CameraConstants.FRONT_CAM_NAME);
		rearCam = startCam("rear camera",CameraConstants.REAR_CAM_NAME);
		if(frontCam != null) {
			frontCam.setWhiteBalanceManual(USBCamera.WhiteBalance.kFixedIndoor);
			frontCam.setExposureManual(CameraConstants.FRONT_EXPOSURE);
			frontCam.setBrightness(CameraConstants.FRONT_BRIGHTNESS);
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
		CameraServer.getInstance().setQuality(CameraConstants.SERVER_QUALITY);
		viewCam(FRONT_CAM);
	}

	public void run() {
		switch(cameraView) {
		case FRONT_CAM:
			frontCam.getImage(frontFrame);
			CameraServer.getInstance().setImage(frontFrame);
			break;
		case REAR_CAM:
			rearCam.getImage(rearFrame);
			CameraServer.getInstance().setImage(rearFrame);
			break;
		default:
		case NO_CAM:
			CameraServer.getInstance().setImage(noFrame);
			break;
		}
	}
	
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
