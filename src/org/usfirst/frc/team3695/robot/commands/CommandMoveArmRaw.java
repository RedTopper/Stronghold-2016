package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves the mechanics of the arm as raw states. This is for debug.
 */
public class CommandMoveArmRaw extends Command {
	public static final int LOCK_LATCH = 0,
							UNLOCK_LATCH = 1,
							PISTON_UP = 2,
							PISTON_DOWN = 3;
	private int objective;
	
	/**
	 * Messes with the pneumatics directly.
	 * @param objective CommandMoveArmRaw.LOCK_LATCH will lock the latch,<br>
	 * CommandMoveArmRaw.UNLOCK_LATCH will unlock the latch,<br>
	 * CommandMoveArmRaw.PISTON_UP will move the arm piston up (and move the arm down),<br>
	 * CommandMoveArmRaw.PISTON_DOWN will move the arm piston down (and move the arm up).
	 */
	public CommandMoveArmRaw(int objective) {
		requires(Robot.armSubsystem);
		this.objective = objective;
	}
	
	protected void initialize() {
		switch(objective) {
		case LOCK_LATCH:
			Robot.armSubsystem.engageLatch();
			break;
		case UNLOCK_LATCH:
			Robot.armSubsystem.disengageLatch();
			break;
		case PISTON_UP:
			Robot.armSubsystem.movePistonUp();
			break;
		case PISTON_DOWN:
			Robot.armSubsystem.movePistonDown();
			break;
		}
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
