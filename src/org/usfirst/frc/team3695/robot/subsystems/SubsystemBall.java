package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SubsystemBall extends Subsystem {
	Jaguar motor = new Jaguar(Constants.BALL_MOTOR_PORT);
	
	@Override
	protected void initDefaultCommand() {
	}

	public void suckInBall() {
		motor.set((Constants.BALL_MOTOR_INVERT ? -1.0 : 1.0));
	}
	
	public void throwOutBall() {
		motor.set((Constants.BALL_MOTOR_INVERT ? 1.0 : -1.0));
	}
	
	public void stop() {
		motor.set(0.0);
	}
}
