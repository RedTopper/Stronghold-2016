package org.usfirst.frc.team3695.robot.util;

import com.ni.vision.NIVision.Range;

import edu.wpi.first.wpilibj.Preferences;

/**
 * Static utility methods go in this class.
 */
public class Util {
	private static Preferences pref = Preferences.getInstance();
	
	/**
	 * Static utility methods go in this class.
	 * @throws InstantiationException
	 */
	public Util() throws InstantiationException {
		throw new InstantiationException();
	}
	
	/**
	 * Either sets a backup range or returns the one set by the driver on the smartdash preferences.
	 * @param type The type of control this preference is for. Example: "REC" is for image recognition, and "CAM" is for camera.
	 * @param lowKey The name of the low value.
	 * @param lowValue The default value of the low value
	 * @param highKey The name of the high value
	 * @param highValue The default value of the high value
	 * @return The range that was either just created by calling this method, or the one set in robot preferences.
	 */
	public static final Range setAndGetRange(String type, String lowKey, int lowValue, String highKey, int highValue) {
		if(!pref.containsKey(type + ": " + lowKey)) pref.putInt(type + ": " + lowKey, lowValue);
		if(!pref.containsKey(type + ": " + highKey)) pref.putInt(type + ": " + highKey, highValue);
		return new Range(pref.getInt(type + ": " + lowKey, lowValue),pref.getInt(type + ": " + highKey, highValue));
	}
	
	/**
	 * Either sets a backup value or returns the one set by the driver on the smart dash preferences.
	 * @param type The type of control this preference is for. Example: "REC" is for image recognition, and "CAM" is for camera.
	 * @param key The name of the value
	 * @param value The default value if one has not been set yet.
	 * @return The value that was either just created, or a previous one set by the robot preferences.
	 */
	public static final int setAndGetNumber(String type, String key, int value) {
		if(!pref.containsKey(type + ": " + key)) pref.putInt(type + ": " + key, value);
		return pref.getInt(type + ": " + key, value);
	}
	
	/**
	 * Either sets a backup value or returns the one set by the driver on the smart dash preferences.
	 * @param type The type of control this preference is for. Example: "REC" is for image recognition, and "CAM" is for camera.
	 * @param key The name of the value
	 * @param value The default value if one has not been set yet. This is a double unlike setAndGetNumber(...)
	 * @return The value that was either just created, or a previous one set by the robot preferences.
	 */
	public static final double setAndGetDouble(String type, String key, double value) {
		if(!pref.containsKey(type + ": " + key)) pref.putDouble(type + ": " + key, value);
		return pref.getDouble(type + ": " + key, value);
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
		return (float)(0x00000000 + (((int)b) << 16) + (((int)g) << 8) + (((int)r)));
	}
	
	/**
	 * Convert a hue, saturation, and value to a float compatible with NIVision.
	 * @param hue double from 0-1
	 * @param saturation double from 0-1
	 * @param value double from 0-1
	 * @return a float based on NIVision.
	 */
	public static float hsvToRgb(double hue, double saturation, double value) {		
	    int h = (int)(hue * 6);
	    double f = hue * 6 - h;
	    double p = value * (1 - saturation);
	    double q = value * (1 - f * saturation);
	    double t = value * (1 - (1 - f) * saturation);

	    switch (h) {
	      case 0: return getColor((int)(value * 256.0), (int)(t * 256.0), (int)( p * 256.0));
	      case 1: return getColor((int)(q * 256.0), (int)(value * 256.0), (int)( p * 256.0));
	      case 2: return getColor((int)(p * 256.0), (int)(value * 256.0), (int)( t * 256.0));
	      case 3: return getColor((int)(p * 256.0), (int)(q * 256.0), (int)( value * 256.0));
	      case 4: return getColor((int)(t * 256.0), (int)(p * 256.0), (int)( value * 256.0));
	      case 5: return getColor((int)(value * 256.0), (int)(p * 256.0), (int)( q * 256.0));
	      default: return getColor(0, 0, 0);
	    }
	}
}
