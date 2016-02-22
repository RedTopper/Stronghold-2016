package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SubsystemSensors extends Subsystem {
	AnalogInput photoPickup = new AnalogInput(Constants.PHOTO_PICKUP_PORT);
	AnalogInput photoLoaded = new AnalogInput(Constants.PHOTO_LOADED_PORT);
	AnalogInput ultrasonicInput = new AnalogInput(Constants.ULTRASONIC_INPUT);

	
	@Override
	protected void initDefaultCommand() {
	}
	
	public double getPhotoPickupVoltage() {
		return photoPickup.getVoltage();
	}
	
	public double getPhotoLoadedVoltage() {
		return photoLoaded.getVoltage();
	}
	
	public double getUltrasonicVoltage() {
		return ultrasonicInput.getVoltage();
	}
	
	public void log() {
		double pickup;
		double loaded;
		boolean detectFire;
		boolean detectPickup;
		pickup = getPhotoPickupVoltage();
		loaded = getPhotoLoadedVoltage();
		SmartDashboard.putNumber("Analog Read", pickup);
		if (pickup > Constants.PHOTO_LIMIT){
			detectPickup = true;
		}
		else{
			detectPickup = false;
		}
		if (loaded > Constants.PHOTO_LIMIT){
			detectFire = true;
		}
		else{
			detectFire = false;
		}
		SmartDashboard.putBoolean("Pickup",detectPickup);
		SmartDashboard.putBoolean("Loaded",detectFire);
	}
}
