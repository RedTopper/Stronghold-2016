package org.usfirst.frc.team3695.robot;

public class Constants {
	private Constants() throws InstantiationException{
		throw new InstantiationException();
	}
	
	//private static final int STEPHEN_BUTTON = 2; 
	//private static final char BRIAN_BUTTON = 'h';
	
	/**
	 * Time in MS between when the latch closes and the arm piston moves downwards.
	 */
	public static final long TIME_TO_LATCH = 2000;
	
	/**
	 * Time it takes for the piston to move down so the driver can fire the ball again.
	 */
	public static final long TIME_TO_MOVE_ARM_PISTON = 2000;

	/**
	 * Joystick control port
	 */
	public static final int DRIVE_JOYSTICK = 0,
							OPERATOR_JOYSTICK = 1;
	
	/**
	 * True if the joystick is an xBox controller.
	 */
	public static final boolean DRIVE_JOYSTICK_IS_XBOX = true,
								OPERATOR_JOYSTICK_IS_XBOX = true;
	
	/**
	 * Port for driving motors.
	 */
	public static final int FRONT_LEFT_MOTOR_PORT = 3,
							FRONT_RIGHT_MOTOR_PORT = 2,
							REAR_LEFT_MOTOR_PORT = 1,
							REAR_RIGHT_MOTOR_PORT = 0,
							BALL_MOTOR_PORT = 4;
	
	/**
	 * Analog Port used for Pnuematics Transducer Gauge
	 */
	public static final int ULTRASONIC_INPUT = 0,
							TRANSDUCER_PORT = 1,
							PHOTO_LOADED_PORT = 2,
							BUTTON_INPUT = 3;
	
	/**
	 * Ultrasonic ranges in inches.
	 */
	public static final double MIN_RANGE = 60,
							   IDEAL_POSITION = 66,
							   MAX_RANGE = 72;
	
	/**
	 * Scalar for Pnuematics (Converts volts to ~ PSI)
	 */
	public static final double TRANSDUCER_SCALAR = 25.0,
							   TRANSDUCER_B = -12.5;
	
	/**
	 * Limit for the Photoelectric Sensor
	 */
	public static final double PHOTO_LIMIT = 0.7;
	
	/**
	 * Limit for the buttons
	 */
	public static final double BUTTON_LIMIT = 0.1;
	
	/**
	 * True if the motor is inverted. False otherwise.
	 */
	public static final boolean FRONT_LEFT_MOTOR_INVERT = true,
								FRONT_RIGHT_MOTOR_INVERT = false,
								REAR_LEFT_MOTOR_INVERT = false,
								REAR_RIGHT_MOTOR_INVERT = false,
								BALL_MOTOR_INVERT = true;
	
	/**
	 * Amount the robot has traveled per encoder pulse.
	 */
	public static final double DISTANCE_PER_PULSE = 0.042;
	
	/**
	 * String that tells the robot which axis is down. By default, 
	 * this value is "Z".
	 */
	public static final String DOWN_AXIS = "Y";
	
	/**
	 * Boolean that tells the robot if down should be negative instead
	 * of positive. By default this is false. (False means the acceleromiter's
	 * number will read 1.0 g's when in it's standard position. True means it'll
	 * read -1.0 g's when in it's standard position.
	 */
	public static final boolean DOWN_IS_NEGATIVE = false;

	/**
	 * The controller will rumble for at least this long before stopping. 
	 */
	public static final long RUMBLE_TIME_MS = 200;
	/**
	 * This value defines the amount of needed g forces to activate rumble. If
	 * the current amount of g force is higher than 1 g + RUMBLE_BOUND_G_FORCE -OR-
	 * the current amount of g force is less than 1 g - RUMBLE_BOUND_G_FORCE then
	 * rumble will be activated on the driver's controller.
	 */
	public static final float RUMBLE_BOUND_G_FORCE = 0.4f;
	
	/**
	 * Camera width and height in pixels.
	 */
	public static final int CAMERA_WIDTH = 640,
							CAMERA_HEIGHT = 480;
	
	/**
	 * Used for camera calibration. These numbers represent the offset from the center of the
	 * camera in pixels. The variables here are the names to use when obtaining them through
	 * the preferences.
	 */
	public static final String CAMERA_CALIBRATION_LR_NAME = "Camera Calibration Left-/Right+",
							   CAMERA_CALIBRATION_UD_NAME = "Camera Calibration Down-/Up+";

	/**
	 * Amount of time that the robot will rotate before timing out in seconds.
	 */
	public static final long MAX_ROTATE_TIME = 5;

	/**
	 * The amount of times code can run while the goal is out of the camera.
	 */
	public static final int MAX_ERRORS = 100;

	/**
	 * Solenoid ports.
	 */
	public static final int BUCKET_SOLENOID_UP = 0,
							BUCKET_SOLENOID_DOWN = 1,
							LATCH_SOLENOID_ENGAGE = 2,
							LATCH_SOLENOID_DISENGAGE = 3,
							ARM_PISTON_SOLENOID_UP = 4,
							ARM_PISTON_SOLENOID_DOWN = 5;

	
	public static final double NO_BOOST_MULTIPLIER = 0.7;
}
