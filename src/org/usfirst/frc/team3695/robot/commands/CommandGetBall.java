package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a ball from the playing field into the
 * arm.
 */
public class CommandGetBall extends Command {
	public static final int SUCK_IN_BALL = 0,
							THROW_OUT_BALL = 1;
	
	private int ballDirection;
	
	/**
	 * Gets the ball.
	 * @param ballDirection either FORWARDS or BACKWARDS
	 */
	public CommandGetBall(int ballDirection) {
		requires(Robot.ballSubsystem);
		this.ballDirection = ballDirection;
	}
	
	protected void initialize() {
		switch(ballDirection) {
		case SUCK_IN_BALL:
			Robot.ballSubsystem.suckInBall();
			break;
		default:
			Robot.ballSubsystem.throwOutBall();
			break;
		}
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.ballSubsystem.stop();
	}

	protected void interrupted() {
		end();
	}
}
