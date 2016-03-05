package org.usfirst.frc.team3695.robot.commands.pneumatics;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CommandCompressorToggle extends Command {
	public CommandCompressorToggle() {
		requires(Robot.compressorSubsystem); 
	}
	
	protected void initialize() {
		Robot.compressorSubsystem.setState(true);
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.compressorSubsystem.setState(false);
	}

	protected void interrupted() {	
		end();
	}
}
