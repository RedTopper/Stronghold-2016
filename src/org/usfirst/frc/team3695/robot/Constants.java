package org.usfirst.frc.team3695.robot;

public class Constants {
	private Constants() throws InstantiationException{
		throw new InstantiationException();
	}
	
	//private static final int STEPHEN_BUTTON = 2; 
	//private static final char BRIAN_BUTTON = 'h';
	
	//Joystick controls.
	public static final int DRIVE_JOYSTICK = 0;
	public static final int OPERATOR_JOYSTICK = 1;
	
	//Ports for driving motors.
	public static final int FRONT_LEFT_MOTOR_PORT = 0;
	public static final int FRONT_RIGHT_MOTOR_PORT = 1;
	public static final int REAR_LEFT_MOTOR_PORT = 2;
	public static final int REAR_RIGHT_MOTOR_PORT = 3;
	
	//Motors that should be inverted.
	public static final boolean FRONT_LEFT_MOTOR_INVERT = false;
	public static final boolean FRONT_RIGHT_MOTOR_INVERT = true;
	public static final boolean REAR_LEFT_MOTOR_INVERT = false;
	public static final boolean REAR_RIGHT_MOTOR_INVERT = false;
	
	//Sensor variables
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
}
