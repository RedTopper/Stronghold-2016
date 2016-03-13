package org.usfirst.frc.team3695.robot;

import com.ni.vision.NIVision.Range;

import edu.wpi.first.wpilibj.Preferences;

public class CameraConstants {
	private CameraConstants() throws InstantiationException {
		throw new InstantiationException();
	}
	
	public static final Range Hue = new Range(Preferences.getInstance().getInt("Hue Low", 160),Preferences.getInstance().getInt("Hue High", 180));
	public static final Range Saturation = new Range(Preferences.getInstance().getInt("Saturation Low", 200),Preferences.getInstance().getInt("Saturation High", 255));
	public static final Range Value = new Range(Preferences.getInstance().getInt("Value Low", 200),Preferences.getInstance().getInt("Value High", 255));
	
	public static final String FRONT_CAM_NAME = "cam1";
	public static final String REAR_CAM_NAME = "cam2";
	
	public static final int FRONT_EXPOSURE = Preferences.getInstance().getInt("Front Camera Exposure", 50);
	public static final int FRONT_BRIGHTNESS = Preferences.getInstance().getInt("Front Camera Brightness", 50);
	
	public static final int SERVER_QUALITY = Preferences.getInstance().getInt("Server Quality", 50);
}
