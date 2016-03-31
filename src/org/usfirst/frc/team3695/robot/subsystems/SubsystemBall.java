package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.util.Loggable;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This subsystem controls the motor that is used to move the
 * ball into the arm.
 */
public class SubsystemBall extends Subsystem implements Loggable {
	private CANTalon motor = new CANTalon(Constants.BALL_MOTOR_PORT);
	private String motorStatus = "Stopped.";
	
	protected void initDefaultCommand() {
	}

	/**
	 * Attempt to pull a ball into the robot.
	 */
	public void suckInBall() {
		motor.set((Constants.BALL_MOTOR_INVERT ? -1.0 : 1.0));
		motorStatus = "Moving ball inwards.";
	}
	
	/**
	 * Attempt to expel a ball from the bucket.
	 */
	public void throwOutBall() {
		motor.set((Constants.BALL_MOTOR_INVERT ? 1.0 : -1.0));
		motorStatus = "Pushing ball outwards.";
	}
	
	/**
	 * Stop the roller completely.
	 */
	public void stop() {
		motor.set(0.0);
		motorStatus = "Stopped.";
	}

	public void log() {
		SmartDashboard.putString("Ball Motor", motorStatus);
	}
}
