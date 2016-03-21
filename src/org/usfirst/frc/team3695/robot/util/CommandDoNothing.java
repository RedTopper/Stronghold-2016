package org.usfirst.frc.team3695.robot.util;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will do nothing for some amount of time 
 * in MS.
 */
public class CommandDoNothing extends Command {
	private long past;
	private long delay; 
	
	/**
	 * Does nothing.
	 * @param delayms Time to do nothing (ms).
	 */
	public CommandDoNothing (long delayms) {
		this.delay = delayms;
		requires(Robot.driveSubsystem);
	}
	
	protected void initialize() {
		past = System.currentTimeMillis();
	}

	protected void execute() {
		Robot.driveSubsystem.tankdrive(0,0);
	}

	protected boolean isFinished() {
		return past + delay < System.currentTimeMillis();
	}

	protected void end() {
	}

	protected void interrupted() {
		end();
	}
}
