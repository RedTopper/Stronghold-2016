package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.util.Loggable;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is a subsystem for all sensors.
 * There is no need to require it (as it should be available to all commands) but
 * it is needed to make all of the sensors behave as "singletons".
 */
public class SubsystemSensors extends Subsystem implements Loggable {
	
	/**
	 * Various sensors used for the robot.
	 */
	private AnalogInput photoLoaded = new AnalogInput(Constants.PHOTO_LOADED_PORT),
						ultrasonicInput = new AnalogInput(Constants.ULTRASONIC_INPUT),
						loadedInput = new AnalogInput(Constants.BUTTON_INPUT),
						pressureGauge = new AnalogInput(Constants.TRANSDUCER_PORT);

	protected void initDefaultCommand() {
	}
	
	public double getPhotoLoadedVoltage() {
		return photoLoaded.getVoltage();
	}
	
	public double getUltrasonicVoltage() {
		return ultrasonicInput.getVoltage();
	}
	
	/**
	 * @return True if the arm switch is pressed, false
	 * otherwise.
	 */
	public boolean isSwitchPressed(){
		double switchVoltage = loadedInput.getVoltage();
		return switchVoltage < 0.1;
	}
	
	/**
	 * Gets the pressure of the pneumatics.
	 * @return An amount of pressure in PSI.
	 */
	public double getPressure(){
		double psi = pressureGauge.getVoltage() * Constants.TRANSDUCER_SCALAR + Constants.TRANSDUCER_B;
		if (psi < 0.0) {
			psi = 0.0;
		}
		return psi;
	}
	
	public void log() {
		SmartDashboard.putNumber("System Pressure", getPressure());
		SmartDashboard.putBoolean("Catapult Completely Down", isSwitchPressed());
		/*
		double ultrasonic = getUltrasonicVoltage() * 200;
		SmartDashboard.putNumber("Ultrasonic Value" , ultrasonic);
		boolean rightRange;
		if(ultrasonic > Constants.MIN_RANGE && ultrasonic < Constants.MAX_RANGE) {
			rightRange = true;
		}else{
			rightRange = false;
		}
		SmartDashboard.putBoolean("Firing Range", rightRange);
		double loaded  = getPhotoLoadedVoltage();
		boolean detectFire;
		
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

		SmartDashboard.putBoolean("Loaded",detectFire);
		SmartDashboard.putNumber("Loaded Voltage",loaded);
		*/
	}
}
