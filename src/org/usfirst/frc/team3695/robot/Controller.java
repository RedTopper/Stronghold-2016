package org.usfirst.frc.team3695.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controller {
	private static final Joystick driver = new Joystick(Constants.DRIVE_JOYSTICK);
	private static final Joystick operator = new Joystick(Constants.OPERATOR_JOYSTICK);

	/** XBox "B Button" */
	public static final int DRIVE_BOOST = 2;
	/** XBox "Y Button" */
	public static final int DRIVE_REAR_CAM = 4;
	/** XBox "X Button" */
	public static final int DRIVE_PROCESSED_CAM = 3;
	/** The degree amount the POV must be to rotate the robot left for auto targeting. */
	public static final int DRIVE_TARGET_LEFT = 270;
	/** The degree amount the POV must be to rotate the robot right for auto targeting. */
	public static final int DRIVE_TARGET_RIGHT = 90;
	
	/** XBox "Left Bumper" */
	public static final int OP_RELEASE_BALL = 5;
	/** XBox "Right Bumper" */
	public static final int OP_GRAB_BALL = 6;
	/** XBox "A Button" */
	public static final int OP_FIRE_ARM = 1;
	/** XBox "B Button" */
	public static final int OP_RESET_ARM = 2;
	/** XBox "Left Bumper" */
	public static final int OP_PISTON_UP = 5;
	/** XBox "Right Bumper" */
	public static final int OP_PISTON_DOWN = 6;
	/** The degree amount the POV must be to move the bucket UP. */
	public static final int OP_BUCKET_UP_POV_DEG = 0;
	/** The degree amount the POV must be to move the bucket DOWN. */
	public static final int OP_BUCKET_DOWN_POV_DEG = 180;
	
	/**
	 * Returns the X and Y position of the axis for the driver.
	 * @return an array for the X and Y position.
	 */
	public static final double[] DRIVE_AXIS() {
		double reverse = driver.getRawAxis(2);
		double accelerate = driver.getRawAxis(3);
		double x = driver.getX();
		double y = (accelerate - reverse)*(!(DRIVE_JOY().getRawButton(DRIVE_BOOST) ^ Robot.isBoostButtonBoost())?1.0:Constants.NO_BOOST_MULTIPLIER);
		double left = 0.0;
		double right = 0.0;
		if(Robot.isYuEnabled()) {
			right = (1.5*y - x > 1.0 ? 1.0 : 1.5*y - x);
			right = (1.5*y - x < -1.0 ? -1.0 : right);
			left = (1.5*y + x > 1.0 ? 1.0 : 1.5*y + x);
			left = (1.5*y + x < -1.0 ? -1.0 : left);
		} else {
			right = (y*Math.abs(1.0 - x) > 1.0 ? 1.0 : y*Math.abs(1.0 - x));
			right = (y*Math.abs(1.0 - x) < -1.0 ? -1.0 : right);
			left = (y*Math.abs(1.0 + x) > 1.0 ? 1.0 : y*Math.abs(1.0 + x));
			left = (y*Math.abs(1.0 + x) < -1.0 ? -1.0 : left);
		}
		return new double[]{left,right};
	}

	//Raw joystick values.
	/**
	 * Use this to obtain the joystick for the driver.
	 * @return The driver joystick
	 */
	public static Joystick DRIVE_JOY() {
		return driver;
	}
	
	/**
	 * Use this to obtain the joystick for the operator.
	 * @return The operator joystick
	 */
	public static Joystick OP_JOY() {
		return operator;
	}
}
