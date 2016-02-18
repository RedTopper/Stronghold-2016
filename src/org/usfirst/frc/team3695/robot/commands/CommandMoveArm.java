package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CommandMoveArm extends Command {
	public static final int MOVE_UP = 0,
							MOVE_DOWN = 1;
	
	private int dir = 0;
	
	public CommandMoveArm(int direction) {
		requires(Robot.throwSubsystem);
		this.dir = direction;
	}

	@Override
	protected void initialize() {
		switch (dir) {
		case MOVE_UP:
			Robot.throwSubsystem.fireDown(false);
			Robot.throwSubsystem.fireUp(true);
			break;
		case MOVE_DOWN:
			Robot.throwSubsystem.fireDown(true);
			Robot.throwSubsystem.fireUp(false);
			break;
		}
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
