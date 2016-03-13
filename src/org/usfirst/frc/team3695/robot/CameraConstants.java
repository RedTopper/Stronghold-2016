package org.usfirst.frc.team3695.robot;

import com.ni.vision.NIVision.Range;

import edu.wpi.first.wpilibj.Preferences;

public class CameraConstants {
	private CameraConstants() throws InstantiationException {
		throw new InstantiationException();
	}
	
	public static final String FRONT_CAM_NAME = "cam0";
	public static final String REAR_CAM_NAME = "cam1";
	
	public static final Range HUE() {
		return new Range(Preferences.getInstance().getInt("Hue Low", 160),Preferences.getInstance().getInt("Hue High", 180));
	}
	public static final Range SATURATION() {
		return new Range(Preferences.getInstance().getInt("Saturation Low", 200),Preferences.getInstance().getInt("Saturation High", 255));
	}
	public static final Range VALUE() {
		return new Range(Preferences.getInstance().getInt("Value Low", 200),Preferences.getInstance().getInt("Value High", 255));
	}
	
	public static final int FRONT_EXPOSURE(){
		return Preferences.getInstance().getInt("Front Camera Exposure", 50);
	}
	
	public static final int FRONT_BRIGHTNESS(){
		return Preferences.getInstance().getInt("Front Camera Brightness", 50);
	}
	
	public static final int SERVER_QUALITY() {
		return Preferences.getInstance().getInt("Server Quality", 50);
	}
}
