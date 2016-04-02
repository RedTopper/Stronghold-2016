package org.usfirst.frc.team3695.robot.vision;

import org.usfirst.frc.team3695.robot.util.Util;

import com.ni.vision.NIVision.Range;

public class CameraConstants {
	private CameraConstants() throws InstantiationException {
		throw new InstantiationException();
	}
	
	/**
	 * Amount of times the camera can miss the goal after discovering it for the first time.
	 * Any amount more than this will cancel the running command and return an error
	 * during autonomous. 
	 */
	public static final int MAX_CAMERA_MISSES = 50;
	
	/**
	 * Amount of time that the robot will rotate before timing out in seconds.
	 */
	public static final int MAX_ROTATE_TIME = 7;
	
	/**
	 * Amount of time that the robot will rotate before timing out in seconds.
	 */
	public static final int MAX_FORWARD_TIME = 5;
	
	/**
	 * The minimum size of the convex hull of the goal.
	 */
	public static final int GOAL_MIN_AREA = 600;
	
	/**
	 * Camera width and height in pixels. This is used for image recognition because it is
	 * faster to process.
	 */
	public static final int LOW_RES_CAMERA_WIDTH = 320,
							LOW_RES_CAMERA_HEIGHT = 240;
	
	/**
	 * Camera width and height in pixels.
	 */
	public static final int HIGH_RES_CAMERA_WIDTH = 640,
							HIGH_RES_CAMERA_HEIGHT = 480;
	
	/**
	 * Name of the front camera.
	 */
	public static final String FRONT_CAM_NAME = "cam0";
	
	/**
	 * Name of the rear camera.
	 */
	public static final String REAR_CAM_NAME = "cam1";

	/**
	 * Brightness that should be used when driving around normally.
	 */
	public static final int FRONT_BRIGHTNESS = 0;
	
	/**
	 * The hue for the image recognition.
	 * @return The backup value as defined in the method or the value stored in the preferences if available.
	 */
	public static final Range HUE() {
		return Util.setAndGetRange("REC", "Hue Low", 110, "Hue High", 130); //Set default ranges here
	}
	
	/**
	 * The saturation for the image recognition.
	 * @return The backup value as defined in the method or the value stored in the preferences if available.
	 */
	public static final Range SATURATION() {
		return Util.setAndGetRange("REC", "Saturation Low", 200, "Saturation High", 255); //Set default ranges here
	}
	
	/**
	 * The value for the image recognition.
	 * @return The backup value as defined in the method or the value stored in the preferences if available.
	 */
	public static final Range VALUE() {
		return Util.setAndGetRange("REC", "Value Low", 200, "Value High", 255); //Set default ranges here
	}
	
	/**
	 * The brightness of the front camera during image recognition.
	 * @return The backup value as defined in the method or the value stored in the preferences if available.
	 */
	public static final int FRONT_BRIGHTNESS(){
		return Util.setAndGetNumber("CAM", "Front Brightness", 50); //Set default value here
	}
	
	/**
	 * The quality of the server. Higher looks better, but takes more bandwidth. Do not go higher than 80 or lower than 30.
	 * @return The backup value as defined in the method or the value stored in the preferences if available.
	 */
	public static final int SERVER_QUALITY() {
		return Util.setAndGetNumber("CAM", "Server Quality", 50); //Set default value here
	}
}
