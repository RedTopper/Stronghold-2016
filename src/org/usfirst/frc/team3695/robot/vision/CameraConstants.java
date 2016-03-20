package org.usfirst.frc.team3695.robot.vision;

import org.usfirst.frc.team3695.robot.util.Util;

import com.ni.vision.NIVision.Range;

public class CameraConstants {
	private CameraConstants() throws InstantiationException {
		throw new InstantiationException();
	}
	
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
	 * Name of the front cam.
	 */
	public static final String FRONT_CAM_NAME = "cam0";
	
	/**
	 * Name of the rear cam.
	 */
	public static final String REAR_CAM_NAME = "cam1";
	
	/**
	 * The hue for the image rec.
	 * @return The backup value as defined in the method or the value stored in the preferences if available.
	 */
	public static final Range HUE() {
		return Util.setAndGetRange("Hue Low", 110, "Hue High", 130);
	}
	
	/**
	 * The saturation for the image rec.
	 * @return The backup value as defined in the method or the value stored in the preferences if available.
	 */
	public static final Range SATURATION() {
		return Util.setAndGetRange("Saturation Low", 200, "Saturation High", 255);
	}
	
	/**
	 * The value for the image rec.
	 * @return The backup value as defined in the method or the value stored in the preferences if available.
	 */
	public static final Range VALUE() {
		return Util.setAndGetRange("Value Low", 200, "Value High", 255);
	}
	
	/**
	 * The brightness of the front cam during imagerec.
	 * @return The backup value as defined in the method or the value stored in the preferences if available.
	 */
	public static final int FRONT_BRIGHTNESS(){
		return Util.setAndGetNumber("Front Camera Brightness", 50);
	}
	
	/**
	 * The quality of the server. Higher looks better, but takes more bandwidth. Do not go higher than 80 or lower than 30.
	 * @return The backup value as defined in the method or the value stored in the preferences if available.
	 */
	public static final int SERVER_QUALITY() {
		return Util.setAndGetNumber("Server Quality", 50);
	}
}
