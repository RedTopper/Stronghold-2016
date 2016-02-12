package org.usfirst.frc.team3695.robot;

public class Constants {
	private Constants() throws InstantiationException{
		throw new InstantiationException();
	}
	
	//private static final int STEPHEN_BUTTON = 2;
	//private static final char BRIAN_BUTTON = 'h';
	
	/**
	 * This variable determines if the code should behave as the robot that will be used
	 * for competition. This variable is true if the robot is the competition robot, and
	 * false if the robot is the simple, more stripped down practice robot.
	 */
	public static final boolean IS_OFFICIAL_ROBOT = true;
	
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
}