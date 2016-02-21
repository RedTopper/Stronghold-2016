package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SubsystemSensors extends Subsystem {
	AnalogInput photoPickup = new AnalogInput(Constants.PHOTO_PICKUP_PORT);
	AnalogInput photoLoaded = new AnalogInput(Constants.PHOTO_LOADED_PORT);
	AnalogInput ultrasonicInput = new AnalogInput(Constants.ULTRASONIC_INPUT);
	double pickup;
	double loaded;
	boolean fire;
	boolean detect;
	double limit = 0.7;
	
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
		pickup = Robot.sensorsSubsystem.getPhotoPickupVoltage();
		loaded = Robot.sensorsSubsystem.getPhotoLoadedVoltage();
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
}
