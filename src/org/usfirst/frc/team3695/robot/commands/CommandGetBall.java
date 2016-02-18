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
	 * Gets or removes the ball from the possession of the arm.
	 * @param ballDirection use CommandGetBall.SUCK_IN_BALL or CommandGetBall.THROW_OUT_BALL
	 * to either grab or remove the ball from the arm.
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
		case THROW_OUT_BALL:
			Robot.ballSubsystem.throwOutBall();
			break;
		}
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return false; //Not finished until the button is depressed.
	}

	protected void end() {
		Robot.ballSubsystem.stop();
	}

	protected void interrupted() {
		end();
	}
}
