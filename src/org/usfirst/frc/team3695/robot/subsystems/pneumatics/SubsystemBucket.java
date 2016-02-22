package org.usfirst.frc.team3695.robot.subsystems.pneumatics;

import org.usfirst.frc.team3695.robot.Constants;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This subsystem uses pneumatics to move up and down the arm
 * of the robot.
 */
public class SubsystemBucket extends Subsystem {
	private Solenoid bucketDown;
	private Solenoid bucketUp;
	
	private String bucketPosition = "Unknown.";
	
	public SubsystemBucket() {
		super();
		bucketDown = new Solenoid(Constants.BUCKET_SOLENOID_DOWN);
		bucketUp = new Solenoid(Constants.BUCKET_SOLENOID_UP);
	}
	
	@Override
	protected void initDefaultCommand() {
	}
	
	public void moveBucketUp() {
		bucketDown.set(false);
		bucketUp.set(true);
		bucketPosition = "Bucket is up.";
	}
	
	public void moveBucketDown() {
		bucketDown.set(true);
		bucketUp.set(false);
		bucketPosition = "Bucket is down.";
	}
	
	public void log() {
		SmartDashboard.putString("Bucket Position", bucketPosition);
	}
}
