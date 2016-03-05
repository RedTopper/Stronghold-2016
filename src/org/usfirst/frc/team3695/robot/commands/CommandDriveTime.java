// Max G, 2016 (<3)
// CommandDriveTime.java
// Makes the robot drive forward for a specified amount of time.

package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CommandDriveTime extends Command {
	private long startTime = 0;
	private float driveTime = 0;
	
	public CommandDriveTime(float timeToDrive) {
		driveTime = timeToDrive;
	}
	
	protected void initialize() {
		startTime = System.currentTimeMillis();
	}

	protected void execute() {
		Robot.driveSubsystem.tankdrive(1, 1);
	}

	protected boolean isFinished() {
		long now = System.currentTimeMillis();
		return (now - startTime) > driveTime;
	}

	protected void end() {
		Robot.driveSubsystem.tankdrive(0, 0);
	}

	protected void interrupted() {
		end();
	}
}
