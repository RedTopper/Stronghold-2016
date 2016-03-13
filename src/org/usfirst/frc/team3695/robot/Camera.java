package org.usfirst.frc.team3695.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.RGBValue;
import com.ni.vision.NIVision.TextAlignment;
import com.ni.vision.NIVision.VerticalTextAlignment;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Camera extends Thread implements Runnable {
	private final USBCamera frontCam;
	private boolean frontCamOn = false;
	private final USBCamera rearCam;
	private boolean rearCamOn = false;
	
	private int cameraView = 0;
	private int newCameraView = 0;
	
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
				"", 32, 0, 0, 0, 0, TextAlignment.LEFT, VerticalTextAlignment.BASELINE, new RGBValue(0, 0, 0, 255), 0.0); //dear god why
		NIVision.imaqOverlayText(noFrame, new NIVision.Point(0,0), "No Camera Feed!", NIVision.RGB_RED, settings, "0");
		
		NIVision.imaqSetImageSize(waitFrame, 640, 480);
		NIVision.imaqOverlayText(waitFrame, new NIVision.Point(0,0), "Loading...", NIVision.RGB_RED, settings, "0");
		
		frontCam = startCam("front camera", CameraConstants.FRONT_CAM_NAME);
		rearCam = startCam("rear camera",CameraConstants.REAR_CAM_NAME);
		
		viewCam(FRONT_CAM);
	}

	public void run() {
		while(true) {
			try {
				long pastTime = System.currentTimeMillis();
				
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
				
				long currentTime = System.currentTimeMillis();
				SmartDashboard.putNumber("Camera Thread FPS", 1000.0 / (double)(currentTime - pastTime));
				if(newCameraView != cameraView) {
					viewCam(newCameraView);
					cameraView = newCameraView;
				}
			} catch (Exception e) {
				DriverStation.reportError("The main thread exited because of :" + e.toString(), true);
				break;
			}
		}
	}
	
	public synchronized void switchCam(int cam) {
		newCameraView = cam;
	}
	
	/**
	 * This method attempts to switch the camera to a new camera feed.
	 * @param cam Use the constants Camera.NO_CAM, Camera.FRONT_PROCCESSED
	 * Camera.FRONT_CAM or Camera.REAR_CAM to switch the camera to a
	 * different feed. 
	 */
	private void viewCam(int newCameraView) {
		CameraServer.getInstance().setImage(waitFrame);
		switch(newCameraView) {
		case FRONT_CAM:
			if(rearCam != null && rearCamOn) {
				rearCam.stopCapture();
				rearCam.closeCamera();
				rearCamOn = false;
			}
			if(frontCam != null) {
				frontCam.setWhiteBalanceManual(USBCamera.WhiteBalance.kFixedIndoor);
				frontCam.setExposureManual(CameraConstants.FRONT_EXPOSURE());
				frontCam.setBrightness(CameraConstants.FRONT_BRIGHTNESS());
				frontCam.setFPS(20);
				frontCam.setSize(640, 480);
				frontCam.updateSettings();
				frontCam.openCamera();
				frontCam.startCapture();
				frontCamOn = true;
				DriverStation.reportWarning("Front camera started!", false);
			}
			cameraView = FRONT_CAM;
			break;
		case REAR_CAM:
			if(frontCam != null && frontCamOn) {
				frontCam.stopCapture();
				frontCam.closeCamera();
				frontCamOn = false;
			}
			if(rearCam != null) {
				rearCam.setWhiteBalanceManual(USBCamera.WhiteBalance.kFixedIndoor);
				rearCam.setFPS(20);
				rearCam.setSize(640, 480);
				rearCam.updateSettings();
				rearCam.openCamera();
				rearCam.startCapture();
				rearCamOn = true;
				DriverStation.reportWarning("Rear camera started!", false);
			}
			cameraView = REAR_CAM;
			break;
		default:
		case NO_CAM:
			if(frontCam != null &&frontCamOn) {
				frontCam.stopCapture();
				frontCam.closeCamera();
				frontCamOn = false;
			}
			if(rearCam != null && rearCamOn) {
				rearCam.stopCapture();
				rearCam.closeCamera();
				rearCamOn = false;
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
			DriverStation.reportError("Could not start the " + humanName + " nammed \"" + camName + "\": " + e, true);
		}
		return cam;
	}
}
