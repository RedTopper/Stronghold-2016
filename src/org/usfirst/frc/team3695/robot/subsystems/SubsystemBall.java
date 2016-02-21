package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;

import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This subsystem controls the motor that is used to move the
 * ball into the arm.
 */
public class SubsystemBall extends Subsystem {
	private TalonSRX motor = new TalonSRX(Constants.BALL_MOTOR_PORT);
	private String motorStatus = "Stopped";
	
	@Override
	protected void initDefaultCommand() {
	}

	public void suckInBall() {
		motor.set((Constants.BALL_MOTOR_INVERT ? -1.0 : 1.0));
		motorStatus = "Moving ball inwards";
	}
	
	public void throwOutBall() {
		motor.set((Constants.BALL_MOTOR_INVERT ? 1.0 : -1.0));
		motorStatus = "Pushing ball outwards";
	}
	
	public void stop() {
		motor.set(0.0);
		motorStatus = "Stopped";
	}
	
	public void log() {
		SmartDashboard.putString("Ball Motor", motorStatus);
	}
}
