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
	private final USBCamera rearCam;
	
	private int cameraView = 0;
	
	private static final int NO_CAM = 0,
							 FRONT_CAM = 1,
							 REAR_CAM = 2;
	
	Image frontFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	Image rearFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	Image noFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	
	public Camera() {
		NIVision.imaqSetImageSize(noFrame, 640, 480);
		NIVision.OverlayTextOptions settings = new NIVision.OverlayTextOptions(
				"Times New Roman", 48, 0, 0, 0, 0, TextAlignment.LEFT, VerticalTextAlignment.BASELINE, new RGBValue(0, 0, 0, 255), 0.0); //dear god
		NIVision.imaqOverlayText(noFrame, new NIVision.Point(0,0), "No Camera Feed!", NIVision.RGB_RED, settings, "0");
		
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
		viewFrontCam();
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
	
	public synchronized void viewFrontCam() {
		if(cameraView == FRONT_CAM) {
			return;
		}
		if(frontCam == null) {
			cameraView = NO_CAM;
			return;
		}
	}
	
	public synchronized void viewRearCam() {
		if(cameraView == REAR_CAM) {
			return;
		}
		if(rearCam == null) {
			cameraView = NO_CAM;
			return;
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
