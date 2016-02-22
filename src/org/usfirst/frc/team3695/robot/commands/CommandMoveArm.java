package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command fires the arm (ball launcher) or resets it's state.
 */
public class CommandMoveArm extends Command {
	public static final int FIRE = 0,
							RESET = 1;
	
	private int objective;
	private boolean complete = false;
	
	/**
	 * Creates a command to move the arm based on an objective.
	 * @param objective Use CommandMoveArm.FIRE to fire the arm and
	 * CommandMoveArm.RESET to move the arm back to the robot.
	 */
	public CommandMoveArm(int objective) {
		requires(Robot.armSubsystem);
		this.objective = objective;
	}
	
	protected void initialize() {
		switch(objective) {
		case FIRE:
			Robot.armSubsystem.movePistonDown();
			complete = false;
			break;
		case RESET:
			Robot.armSubsystem.movePistonUp();
			complete = false;
			break;
		}
	}

	protected void execute() {
		switch(objective) {
		case FIRE:
			if(!Robot.armSubsystem.isPistonUp()) {
				Robot.armSubsystem.disengageLatch();
				complete = true;
			}
			break;
		case RESET:
			if(Robot.armSubsystem.isPistonUp()) {
				Robot.armSubsystem.engageLatch();
				complete = true;
			}
			break;
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
