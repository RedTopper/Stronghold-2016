package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.GetBall;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves a ball from the playing field into the arm.
 */
public class CommandGetBall extends Command {
	private GetBall objective;
	
	/**
	 * Gets or removes the ball from the possession of the arm.
	 * @param objective use CommandGetBall.SUCK_IN_BALL or CommandGetBall.THROW_OUT_BALL
	 * to either grab or remove the ball from the arm.
	 */
	public CommandGetBall(GetBall objective) {
		requires(Robot.ballSubsystem);
		this.objective = objective;
	}
	
	protected void initialize() {
		switch(objective) {
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
