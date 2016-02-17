package org.usfirst.frc.team3695.robot.commands;
import org.usfirst.frc.team3695.robot.Constants;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CommandPhotoelectric extends Command {
	AnalogInput photoPickup = new AnalogInput(Constants.PHOTO_PICKUP_PORT);
	AnalogInput photoLoaded = new AnalogInput(Constants.PHOTO_LOADED_PORT);
	double pickup;
	double loaded;
	boolean fire;
	boolean detect;
	int limit = 2;
	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		pickup = photoPickup.getVoltage();
		loaded = photoLoaded.getVoltage();
		SmartDashboard.putNumber("Test output = " , pickup);
		if (pickup > limit){
			detect = true;
		}
		else{
			detect = false;
		}
		SmartDashboard.putBoolean("Pickup = ",detect);
		if (loaded > limit){
			fire = true;
		}
		else{
			fire = false;
		}
		SmartDashboard.putBoolean("Pickup = ",detect);
		SmartDashboard.putBoolean("Loaded = ",fire);
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
