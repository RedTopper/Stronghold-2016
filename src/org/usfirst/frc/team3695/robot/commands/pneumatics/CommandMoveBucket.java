package org.usfirst.frc.team3695.robot.commands.pneumatics;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves the arm up and down using pistons
 * and fancy pneumatics.
 */
public class CommandMoveBucket extends Command {
	public static final int MOVE_UP = 0,
							MOVE_DOWN = 1;
	
	private int objective = 0;
	
	/**
	 * The direction the arm will move.
	 * @param direction use CommandMoveArm.MOVE_UP or CommandMoveArm.MOVE_DOWN
	 * to move the arm in a direction.
	 */
	public CommandMoveBucket(int objective) {
		requires(Robot.bucketSubsystem);
		this.objective = objective;
	}

	protected void initialize() {
		switch (objective) {
		case MOVE_UP:
			Robot.bucketSubsystem.moveBucketUp();
			break;
		case MOVE_DOWN:
			Robot.bucketSubsystem.moveBucketDown();
			break;
		}
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return true; //Always finished. Always.
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
