package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command fires the arm (ball launcher) or resets 
 * it's state.
 */
public class CommandMoveArm extends Command {
	public static final int FIRE_ARM = 0,
							RESET_ARM = 1,
							RESET_LATCH = 2;
	private int objective;
	private boolean complete = false;
	private long timeReset = 0;
	/**
	 * Creates a command to move the arm based on an objective.
	 * @param objective Use CommandMoveArm.FIRE_ARM to fire the arm,
	 * CommandMoveArm.RESET_ARM to move the arm back to the robot and
	 * CommandMoveArm.RESET_LATCH to close the latch.
	 */
	public CommandMoveArm(int objective) {
		requires(Robot.armSubsystem);
		this.objective = objective;
	}
	
	protected void initialize() {
		switch(objective) {
		case FIRE_ARM:
			Robot.armSubsystem.fireBall();
			complete = true;
			break;
		case RESET_ARM:
			Robot.armSubsystem.resetArm();
			complete = true;
			break;
		case RESET_LATCH:
			Robot.armSubsystem.engageLatch();
			timeReset = System.currentTimeMillis();
			complete = false;
			break;
		}
	}

	protected void execute() {
		if(timeReset + Constants.TIME_BETWEEN_LATCH_AND_PISTON_RESET < System.currentTimeMillis()) {
			Robot.armSubsystem.getPisonsOutOfTheWay();
		}
		if(timeReset + Constants.TIME_BETWEEN_LATCH_AND_PISTON_RESET 
					 + Constants.TIME_BETWEEN_PISTON_RESET_AND_READY < System.currentTimeMillis()) {
			Robot.armSubsystem.ready();
			complete = true;
		}
	}

	protected boolean isFinished() {
		return complete;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
