package org.usfirst.frc.team3695.robot.subsystems.pneumatics;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.enumeration.Bucket;

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
	
	private Bucket bucketCurrentPosition = Bucket.UNKNOWN;
	
	public SubsystemBucket() {
		super();
		bucketDown = new Solenoid(Constants.BUCKET_SOLENOID_DOWN);
		bucketUp = new Solenoid(Constants.BUCKET_SOLENOID_UP);
	}
	
	protected void initDefaultCommand() {
	}
	
	public void moveBucketUp() {
		bucketDown.set(false);
		bucketUp.set(true);
		bucketCurrentPosition = Bucket.BUCKET_UP;
	}
	
	public void moveBucketDown() {
		bucketDown.set(true);
		bucketUp.set(false);
		bucketCurrentPosition = Bucket.BUCKET_DOWN;
	}
	
	public void log() {
		String bucketString = "Unknown.";
		if(bucketCurrentPosition == Bucket.BUCKET_UP) {bucketString = "Bucket is up.";}
		if(bucketCurrentPosition == Bucket.BUCKET_DOWN) {bucketString = "Bucket is down.";}
		SmartDashboard.putString("Bucket Position", bucketString);
	}
}
