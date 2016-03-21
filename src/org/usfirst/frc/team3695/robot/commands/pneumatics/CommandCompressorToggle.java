package org.usfirst.frc.team3695.robot.commands.pneumatics;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Used for turning the compressor off and (hopefully)
 * back on again. This command disables the compressor until
 * the command is interrupted.
 */
public class CommandCompressorToggle extends Command {
	
	/**
	 * Create the compressor switching command.
	 */
	public CommandCompressorToggle() {
		requires(Robot.compressorSubsystem); 
	}
	
	protected void initialize() {
		Robot.compressorSubsystem.setState(false);
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.compressorSubsystem.setState(true);
	}

	protected void interrupted() {	
		end();
	}
}
