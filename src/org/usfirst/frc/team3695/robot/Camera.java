package org.usfirst.frc.team3695.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Camera extends Thread implements Runnable {
	private final USBCamera frontCam;
	private final USBCamera rearCam;
	
	Image frontFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	Image rearFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	
	public Camera() {
		frontCam = startCam("front camera", CameraConstants.FRONT_CAM_NAME);
		rearCam = startCam("rear camera",CameraConstants.REAR_CAM_NAME);
		if(frontCam != null) {
			frontCam.setWhiteBalanceManual(USBCamera.WhiteBalance.kFixedIndoor);
			frontCam.setExposureManual(CameraConstants.FRONT_EXPOSURE);
			frontCam.setBrightness(CameraConstants.FRONT_BRIGHTNESS);
			frontCam.setFPS(20);
			frontCam.setSize(640, 480);
			frontCam.updateSettings();
			DriverStation.reportWarning("Front camera started!", false);
		}
		if(rearCam != null) {
			rearCam.setWhiteBalanceManual(USBCamera.WhiteBalance.kFixedIndoor);
			rearCam.setFPS(20);
			rearCam.setSize(640, 480);
			rearCam.updateSettings();
			DriverStation.reportWarning("Rear camera started!", false);
		}
	}

	public void run() {
		
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
