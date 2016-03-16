package org.usfirst.frc.team3695.robot.vision;

import com.ni.vision.NIVision.Range;

import edu.wpi.first.wpilibj.Preferences;

public class CameraConstants {
	private static Preferences pref = Preferences.getInstance();
	
	private CameraConstants() throws InstantiationException {
		throw new InstantiationException();
	}
	
	public static final String FRONT_CAM_NAME = "cam0";
	public static final String REAR_CAM_NAME = "cam1";
	
	public static final Range HUE() {
		return setAndGetRange("Hue Low", 160, "Hue High", 180);
	}
	public static final Range SATURATION() {
		return setAndGetRange("Saturation Low", 200, "Saturation High", 255);
	}
	public static final Range VALUE() {
		return setAndGetRange("Value Low", 200, "Value High", 255);
	}
	
	public static final int FRONT_EXPOSURE(){
		return setAndGetNumber("Front Camera Exposure", 50);
	}
	
	public static final int FRONT_BRIGHTNESS(){
		return setAndGetNumber("Front Camera Brightness", 50);
	}
	
	public static final int SERVER_QUALITY() {
		return setAndGetNumber("Server Quality", 50);
	}
	
	private static final Range setAndGetRange(String lowKey, int lowValue, String highKey, int highValue) {
		if(!pref.containsKey(lowKey)) pref.putInt(lowKey, lowValue);
		if(!pref.containsKey(highKey)) pref.putInt(highKey, highValue);
		return new Range(pref.getInt(lowKey, lowValue),pref.getInt(highKey, highValue));
	}
	
	private static final int setAndGetNumber(String key, int value) {
		if(!pref.containsKey(key)) pref.putInt(key, value);
		return pref.getInt(key, value);
	}
}
