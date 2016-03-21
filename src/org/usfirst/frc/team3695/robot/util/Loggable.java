package org.usfirst.frc.team3695.robot.util;

public interface Loggable {
	
	/**
	 * Log interesting data about this subsystem to the SmartDashboard.
	 * This method should be called at the rate the main robot loop is called at.
	 * It's wise to access this method from the main Robot.log() method.<br><br>
	 * 
	 * It's also possible to put code beyond the scope of logging in these methods, 
	 * for example, code for changing lights or code for updating a subsystem.
	 */
	public void log();
}
