package org.usfirst.frc.team3695.robot.commands;
import java.lang.Object;

import org.usfirst.frc.team3695.robot.Constants;

import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;

public class CommandPhotoelectric extends Command {
	AnalogInput analogSensor0 = new AnalogInput(Constants.PHOTO_PORT);
	double volts;
	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		volts = analogSensor0.getVoltage();
		SmartDashboard.putNumber("Test output = " , volts);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
