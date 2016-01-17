package org.usfirst.frc.team3695.robot;

public class Constants {
	private Constants() throws InstantiationException{
		throw new InstantiationException();
	}
	//private static int STEPHEN_BUTTON = 2;
	//private static char BRIAN_BUTTON = 'h';
	
	public static final int LEFT_JOYSTICK = 0;
	public static final int RIGHT_JOYSTICK = 1;
	public static final int ENABLE_HIGH_BEAMS = 0;
	
	public static final int FRONT_LEFT_MOTOR_PORT = 0;
	public static final int FRONT_RIGHT_MOTOR_PORT = 2;
	public static final int REAR_LEFT_MOTOR_PORT = 1;
	public static final int REAR_RIGHT_MOTOR_PORT = 3;
	
	public static final boolean FRONT_LEFT_MOTOR_INVERT = true;
	public static final boolean FRONT_RIGHT_MOTOR_INVERT = true;
	public static final boolean REAR_LEFT_MOTOR_INVERT = true;
	public static final boolean REAR_RIGHT_MOTOR_INVERT = true;
}
