package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CommandMoveArmRaw extends Command {
	public static final int LOCK_LATCH = 0,
							UNLOCK_LATCH = 1,
							PISTON_UP = 2,
							PISTON_DOWN = 3;
	private int objective;
	
	public CommandMoveArmRaw(int objective) {
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
