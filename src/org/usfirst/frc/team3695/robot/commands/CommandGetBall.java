package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CommandGetBall extends Command {
	public static final int GET_BALL = 0,
							REMOVE_BALL = 1;
	
	private int ballDirection;
	
	/**
	 * Gets the ball.
	 * @param ballDirection either FORWARDS or BACKWARDS
	 */
	public CommandGetBall(int ballDirection) {
		requires(Robot.ballSubsystem);
		this.ballDirection = ballDirection;
	}
	
	
	@Override
	protected void initialize() {
		switch(ballDirection) {
		case GET_BALL:
			Robot.ballSubsystem.goForward();
			break;
		default:
			Robot.ballSubsystem.goBackwards();
			break;
		}
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.ballSubsystem.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}