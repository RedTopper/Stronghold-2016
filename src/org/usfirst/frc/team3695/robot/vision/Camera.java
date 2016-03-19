package org.usfirst.frc.team3695.robot.vision;

import org.usfirst.frc.team3695.robot.Logger;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.ColorMode;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.MeasureParticlesCalibrationMode;
import com.ni.vision.NIVision.MeasureParticlesReport;
import com.ni.vision.NIVision.Point;
import com.ni.vision.NIVision.Rect;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Camera extends Thread implements Runnable {
	private final USBCamera frontCam;
	private boolean frontCamOn = false;
	private final USBCamera rearCam;
	private boolean rearCamOn = false;
	
	private int cameraView = FRONT_CAM;
	private int newCameraView = FRONT_CAM;
	
	/**
	 * Selectable cameras.
	 */
	public static final int NO_CAM = 0,
							FRONT_PROCCESSED = 1,
							FRONT_CAM = 2,
							REAR_CAM = 3;
	
	private Image frontFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	private Image rearFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	
	double startTime = 0.0;
	private Image waitFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	private Image noFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

	
	/**
	 * Creates a new set of cameras. A set of cameras consists of a front and a rear
	 * camera. If no cameras exist, or some have a problem, this constructor will handle
	 * that situation without throwing an exception. In theory, it'll print a "No Camera Feed!"
	 * message to the camera viewer if there is a problem.
	 */
	public Camera() throws Exception {
		NIVision.imaqSetImageSize(noFrame, 640, 480);
		NIVision.imaqDrawShapeOnImage(noFrame, noFrame, new Rect(0,(640/2) - (480/2), 480, 480), DrawMode.PAINT_VALUE, ShapeMode.SHAPE_OVAL, getColor(0xFF,0x0,0x0));
		NIVision.imaqDrawShapeOnImage(noFrame, noFrame, new Rect(10,(640/2) - (480/2) + 10, 460, 460), DrawMode.PAINT_VALUE, ShapeMode.SHAPE_OVAL, getColor(0,0,0));
		Point topLeft = new Point((int)((320 - 230 * (Math.sqrt(2)/2))+ 0.5),
								  (int)((240 - 230 * (Math.sqrt(2)/2)) + 0.5));
		Point bottomRight = new Point((int)((320 + 230 * (Math.sqrt(2)/2)) + 0.5),
				  					  (int)((240 + 230 * (Math.sqrt(2)/2)) + 0.5));
		NIVision.imaqDrawLineOnImage(noFrame, noFrame, DrawMode.DRAW_VALUE, topLeft, bottomRight, getColor(0xFF,0x00,0x00));
		for(int i = 1; i <= 3; i++) { //This for loop causes the lines to draw out to a thickness of 7, however, because the diagnal of a pixel is root 2, it will be 10 wide.
			NIVision.imaqDrawLineOnImage(noFrame, noFrame, DrawMode.DRAW_VALUE, new Point(topLeft.x, topLeft.y + i), new Point(bottomRight.x + i, bottomRight.y), getColor(0xFF,0x00,0x00));
			NIVision.imaqDrawLineOnImage(noFrame, noFrame, DrawMode.DRAW_VALUE, new Point(topLeft.x - i, topLeft.y), new Point(bottomRight.x, bottomRight.y - i), getColor(0xFF,0x00,0x00));
		}
				
		NIVision.imaqSetImageSize(waitFrame, 640, 480);
		
		frontCam = startCam("front camera", CameraConstants.FRONT_CAM_NAME);
		rearCam = startCam("rear camera",CameraConstants.REAR_CAM_NAME);
	}

	public void run() {
		boolean launchThread = true;
		try {
			viewCam(FRONT_CAM);
		} catch (Exception e) {
			Logger.err("The main thread exited! ", e); 
			launchThread = false;
		}
		while(launchThread) {
			try {
				long pastTime = System.currentTimeMillis();

				out: switch(cameraView) {
				case FRONT_PROCCESSED:
					frontCam.getImage(frontFrame);
					NIVision.imaqColorThreshold(frontFrame, frontFrame, 0xFF/*???*/, ColorMode.HSV, CameraConstants.HUE(), CameraConstants.SATURATION(),  CameraConstants.VALUE());
					NIVision.imaqFillHoles(frontFrame, frontFrame, 4);
					NIVision.MeasurementType x = NIVision.MeasurementType.MT_CENTER_OF_MASS_X;
					NIVision.MeasurementType y = NIVision.MeasurementType.MT_CENTER_OF_MASS_Y;
					MeasureParticlesReport report = NIVision.imaqMeasureParticles(frontFrame, MeasureParticlesCalibrationMode.CALIBRATION_MODE_PIXEL, new NIVision.MeasurementType[]{x, y});
					for(int i = 0; i < report.numParticles; i++) { 
						SmartDashboard.putNumber("Test", x.getValue());
					}
					report.free();
					CameraServer.getInstance().setImage(frontFrame);
					break out;
				case FRONT_CAM:
					frontCam.getImage(frontFrame);
					NIVision.imaqDrawShapeOnImage(frontFrame, frontFrame, new Rect((480/2) - 220,(640/2) - 50, 100, 100), DrawMode.PAINT_VALUE, ShapeMode.SHAPE_RECT, getColor(0,0,0));
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
				}
			} catch (Exception e) {
				Logger.err("Possibly recoverable error occcured! ", e);
				try {
					CameraServer.getInstance().setImage(noFrame);
					Thread.sleep(2000);	//Wait for error to go away.
					viewCam(FRONT_CAM); //Attempt to restart the front camera. Might fail.
				} catch (Exception e2) {
					CameraServer.getInstance().setImage(noFrame);
					Logger.err("Nope, it was an irrecoverable error :( ", e2); 
					launchThread = false;
					break;
				}
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
	private void viewCam(int newCameraView) throws Exception {
		CameraServer.getInstance().setQuality(CameraConstants.SERVER_QUALITY());
		Loading load = new Loading(waitFrame, startTime);
		load.start();
		switch(newCameraView) {
		case FRONT_PROCCESSED:
		case FRONT_CAM:
			Logger.out("Start front cam...");
			if(rearCam != null && rearCamOn) {
				rearCam.stopCapture();
				rearCam.closeCamera();
				rearCamOn = false;
			}
			if(frontCam != null) {
				frontCam.setWhiteBalanceManual(USBCamera.WhiteBalance.kFixedIndoor);
				frontCam.setBrightness(CameraConstants.FRONT_BRIGHTNESS());
				frontCam.setFPS(30);
				frontCam.setSize(640, 480);
				frontCam.updateSettings();
				frontCam.openCamera();
				frontCam.startCapture();
				frontCam.getImage(frontFrame); //Remove broken image.
				frontCamOn = true;
			}
			cameraView = FRONT_CAM;
			break;
		case REAR_CAM:
			Logger.out("Start rear cam...");
			if(frontCam != null && frontCamOn) {
				frontCam.stopCapture();
				frontCam.closeCamera();
				frontCamOn = false;
			}
			if(rearCam != null) {
				rearCam.setWhiteBalanceManual(USBCamera.WhiteBalance.kFixedIndoor);
				rearCam.setFPS(30);
				rearCam.setSize(640, 480);
				rearCam.updateSettings();
				rearCam.openCamera();
				rearCam.startCapture();
				rearCam.getImage(rearFrame); //Remove broken image.
				rearCamOn = true;
			}
			cameraView = REAR_CAM;
			break;
		default:
		case NO_CAM:
			Logger.out("Start no cam...");
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
		Logger.out("Stop loading animation...");
		Thread.sleep(500); //Give the camera about a second to fully switch.
		startTime = load.end();
		while(load.running()) {
			Thread.sleep(50);
		}
		load = null; //Dispose the thread.
		Logger.err("Switched cams!");
		cameraView = newCameraView;
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
			Logger.err("Could not start the " + humanName + " nammed \"" + camName + "\"!", e);
		}
		return cam;
	}
	
	/**
	 * Takes a Red, Green, and Blue value and returns the appropriate float. Maybe
	 * @param r Redness
	 * @param g Greenness
	 * @param b Blueness
	 * @return A float
	 */
	public static float getColor(int r, int g, int b) {
		if(r<0) {r=0;}; if(r>0xFF) {r = 0xFF;}; //Limit range for red
		if(g<0) {g=0;}; if(g>0xFF) {g = 0xFF;}; //Limit range for blue
		if(b<0) {b=0;}; if(b>0xFF) {b = 0xFF;}; //Limit range for green
		return (float)(0xFF000000 + (((int)g) << 16) + (((int)b) << 8) + (((int)r)));
	}
}
