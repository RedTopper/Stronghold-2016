package org.usfirst.frc.team3695.robot.commands;
import org.usfirst.frc.team3695.robot.Constants;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CommandPhotoelectric extends Command {
	AnalogInput analogSensor0 = new AnalogInput(Constants.PHOTO_PORT);
	double volts;
	@Override
	protected void end() {
	}

	@Override
	protected void execute() {
		volts = analogSensor0.getVoltage();
		SmartDashboard.putNumber("Test output = " , volts);
	}

	@Override
	protected void initialize() {
	}
	
	@Override
	protected void interrupted() {
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
