package org.usfirst.frc.team3695.robot.commands.pneumatics;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CommandCompressorToggle extends Command {

	boolean complete = true;
	
	public CommandCompressorToggle() {
		requires(Robot.compressorSubsystem);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		Robot.compressorSubsystem.toggle();
	}

	@Override
	protected boolean isFinished() {
		return complete;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}
	
	

}
