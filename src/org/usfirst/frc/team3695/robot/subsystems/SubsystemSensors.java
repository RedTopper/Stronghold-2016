package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is a subsystem for all sensors.
 * There is no need to require it (as it should be available to all commands) but
 * it is needed to make all of the sensors behave as "singletons".
 */
public class SubsystemSensors extends Subsystem {
	AnalogInput photoPickup = new AnalogInput(Constants.PHOTO_PICKUP_PORT);
	AnalogInput photoLoaded = new AnalogInput(Constants.PHOTO_LOADED_PORT);
	AnalogInput ultrasonicInput = new AnalogInput(Constants.ULTRASONIC_INPUT);
	AnalogInput loadedInput = new AnalogInput(Constants.BUTTON_INPUT);

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
	
	public double getButtonVoltage(){
		return loadedInput.getVoltage();
	}
	
	public void log() {
		double ultrasonic = getUltrasonicVoltage() * 200;
		SmartDashboard.putNumber("Ultrasonic Value" , ultrasonic);
		boolean rightRange;
		if(ultrasonic > Constants.MIN_RANGE && ultrasonic < Constants.MAX_RANGE) {
			rightRange = true;
		}else{
			rightRange = false;
		}
		SmartDashboard.putBoolean("rightRange", rightRange);
		double pickup = getPhotoPickupVoltage();
		double loaded  = getPhotoLoadedVoltage();
		boolean detectFire;
		boolean detectPickup;
		
		if (pickup > Constants.PHOTO_LIMIT){
			detectPickup = true;
		} else{
			detectPickup = false;
		}
		
		if (loaded < Constants.PHOTO_LIMIT){
			detectFire = true;
		} else{
			detectFire = false;
		}
		
		double detectLoad = getButtonVoltage();
		boolean buttonLoad = false;
		if (detectLoad > Constants.BUTTON_LIMIT){
			buttonLoad = true;
		} else{
			buttonLoad = false;
		}
		SmartDashboard.putNumber("Button Voltage", detectLoad);
		SmartDashboard.putBoolean("Launcher Loaded", buttonLoad);

		SmartDashboard.putBoolean("Pickup",detectPickup);
		SmartDashboard.putBoolean("Loaded",detectFire);
		SmartDashboard.putNumber("Pickup Voltage",pickup);
		SmartDashboard.putNumber("Loaded Voltage",loaded);
	}
}
