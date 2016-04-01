package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class CommandLights extends Command {

	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		if (DriverStation.getInstance().getAlliance().toString().contains("Red"))
			Robot.blingSubsystem.redAlliance();
		else
			Robot.blingSubsystem.blueAlliance();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		end();
	}

}
