package org.usfirst.frc.team3695.robot.commands.pneumatics;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.objective.MoveArmRaw;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command moves the mechanics of the arm as raw states. This is for debug.
 */
public class CommandMoveArmRaw extends Command {
	private MoveArmRaw objective;
	private long timeStart;
	private long runtime;
	
	/**
	 * Messes with the pneumatics directly.
	 * @param objective MoveArmRaw.LOCK_LATCH will lock the latch,<br>
	 * MoveArmRaw.UNLOCK_LATCH will unlock the latch,<br>
	 * MoveArmRaw.PISTON_UP will move the arm piston up (and move the arm down),<br>
	 * MoveArmRaw.PISTON_DOWN will move the arm piston down (and move the arm up).
	 */
	public CommandMoveArmRaw(MoveArmRaw objective) {
		requires(Robot.armSubsystem);
		this.objective = objective;
	}
	
	protected void initialize() {
		timeStart = System.currentTimeMillis();
		switch(objective) {
		case LOCK_LATCH:
			Robot.armSubsystem.engageLatch();
			runtime = Constants.TIME_TO_LATCH;
			break;
		case UNLOCK_LATCH:
			Robot.armSubsystem.disengageLatch();
			runtime = Constants.TIME_TO_LATCH;
			break;
		case PISTON_UP:
			Robot.armSubsystem.movePistonUp();
			runtime = Constants.TIME_TO_MOVE_ARM_PISTON;
			break;
		case PISTON_DOWN:
			Robot.armSubsystem.movePistonDown();
			runtime = Constants.TIME_TO_MOVE_ARM_PISTON;
			break;
		}
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return timeStart + runtime < System.currentTimeMillis() ;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
