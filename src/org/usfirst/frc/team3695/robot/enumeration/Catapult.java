package org.usfirst.frc.team3695.robot.enumeration;

/**
 * This enum defines the current status of the catapult. 
 */
public enum Catapult {
	UNKNOWN,

	PISTON_NOT_UP,
	PISTON_MOVING_UP, //Same as down. Gives more detail to driver.
	
	PISTON_UP,
	PISTON_MOVING_DOWN //Same as up. Gives more detail to driver.
}
