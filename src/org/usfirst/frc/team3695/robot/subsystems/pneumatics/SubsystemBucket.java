package org.usfirst.frc.team3695.robot.subsystems.pneumatics;

import org.usfirst.frc.team3695.robot.Constants;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This subsystem uses pneumatics to move up and down the arm
 * of the robot.
 */
public class SubsystemBucket extends Subsystem {
	private Solenoid solenoid;
	private Solenoid solenoid2;
	
	public SubsystemBucket() {
		super();
		solenoid = new Solenoid(Constants.BUCKET_SOLENOID_PORT);
		solenoid2 = new Solenoid(Constants.BUCKET_SOLENOID_PORT2);
	}
	
	@Override
	protected void initDefaultCommand() {
	}
	
	public void moveBucketUp() {
		solenoid.set(false);
		solenoid2.set(true);
	}
	
	public void moveBucketDown() {
		solenoid.set(true);
		solenoid2.set(false);
	}
	
	public void log() {
		
	}
}
