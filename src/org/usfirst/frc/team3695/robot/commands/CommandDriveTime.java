// Max G, 2016 (<3)
// CommandDriveTime.java
// Makes the robot drive forward for a specified amount of time.

package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CommandDriveTime extends Command {
	private long startTime = 0;
	private long driveTime = 0;
	private double power = 0.0;
	
	public CommandDriveTime(long timeToDrive, double power) {
		driveTime = timeToDrive;
		this.power = power;
	}
	
	protected void initialize() {
		startTime = System.currentTimeMillis();
	}

	protected void execute() {
		Robot.driveSubsystem.tankdrive(power, power);
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
