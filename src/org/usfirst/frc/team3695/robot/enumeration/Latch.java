package org.usfirst.frc.team3695.robot.enumeration;

public enum Latch {
	UNKNOWN,
	
	LATCH_NOT_LOCKED,
	LATCH_LOCKING, //Same as not locked. Gives more detail to driver.
	
	LATCH_LOCKED,
	LATCH_UNLOCKING, //Same as locked. Gives more detail to driver.
	
}
