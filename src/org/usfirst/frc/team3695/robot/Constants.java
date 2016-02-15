package org.usfirst.frc.team3695.robot;

import edu.wpi.first.wpilibj.Preferences;

public class Constants {
	private Constants() throws InstantiationException{
		throw new InstantiationException();
	}
	
	//private static final int STEPHEN_BUTTON = 2; 
	//private static final char BRIAN_BUTTON = 'h';
	
	/**
	 * Joystick control port
	 */
	public static final int DRIVE_JOYSTICK = 0,
							OPERATOR_JOYSTICK = 1;
	
	/**
	 * Port for driving motors.
	 */
	public static final int FRONT_LEFT_MOTOR_PORT = 0,
							FRONT_RIGHT_MOTOR_PORT = 1,
							REAR_LEFT_MOTOR_PORT = 2,
							REAR_RIGHT_MOTOR_PORT = 3;
	
	/**
	 * True if the motor is inverted. False otherwise.
	 */
	public static final boolean FRONT_LEFT_MOTOR_INVERT = false,
								FRONT_RIGHT_MOTOR_INVERT = true,
								REAR_LEFT_MOTOR_INVERT = false,
								REAR_RIGHT_MOTOR_INVERT = false;
	
	/**
	 * Amount the robot has traveled per encoder pulse.
	 */
	public static final double DISTANCE_PER_PULSE = 0.042;
	
	/**
	 * String that tells the robot which axis is down. By default, 
	 * this value is "Z".
	 */
	public static final String DOWN_AXIS = "X";
	
	/**
	 * Boolean that tells the robot if down should be negative instead
	 * of positive. By default this is false. (False means the acceleromiter's
	 * number will read 1.0 g's when in it's standard position. True means it'll
	 * read -1.0 g's when in it's standard position.
	 */
	public static final boolean DOWN_IS_NEGATIVE = false;
	
	/**
	 * This value defines the amount of needed g forces to activate rumble. If
	 * the current amount of g force is higher than 1 g + RUMBLE_BOUND_G_FORCE -OR-
	 * the current amount of g force is less than 1 g - RUMBLE_BOUND_G_FORCE then
	 * rumble will be activated on the driver's controller.
	 */
	public static final float RUMBLE_BOUND_G_FORCE = 0.25f;
	
	/**
	 * Camera width and height in pixels.
	 */
	public static final int CAMERA_WIDTH = 1280,
							CAMERA_HEIGHT = 720;
	
	private static Preferences prefs;
	
	/**
	 * Used for camera calibration. These numbers represent the offset from the center of the
	 * camera in pixels.
	 */
	public static final int CAMERA_CALIBRATION_LR = prefs.getInt("Camera Calibration Left-/Right+", 0),
							CAMERA_CALIBRATION_UD = prefs.getInt("Camera Calibration Down-/Up+", 0);
}
