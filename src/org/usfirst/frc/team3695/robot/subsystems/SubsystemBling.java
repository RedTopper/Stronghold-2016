package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.util.Loggable;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Totally bling out the robot with colorful LEDs.
 */
public class SubsystemBling extends Subsystem implements Loggable {
	private Solenoid redLEDS;
	private Solenoid blueLEDS;

	protected void initDefaultCommand() {
	}
	
	/**
	 * Creates a colorful robot.
	 */
	public SubsystemBling(){
		super();
		redLEDS = new Solenoid(Constants.RED_LED_PORT);
		blueLEDS = new Solenoid(Constants.BLUE_LED_PORT);
	}
	
	/**
	 * lights LEDs Blue for blue team
	 */
	public void blueAlliance(){
		setLEDS(false,true);
	}
	
	/**
	 * lights LEDs Red for red team
	 */
	public void redAlliance(){
		setLEDS(true,false);
	}
	
	/**
	 * Sets values of both LED Strips
	 * @param redLED turns the red LED strip on/off
	 * @param blueLED turns the red LED strip on/off
	 */
	public void setLEDS(boolean redLED, boolean blueLED){
		redLEDS.set(redLED);
		blueLEDS.set(blueLED);
	}

	public void log(){
		SmartDashboard.putString("Team", DriverStation.getInstance().getAlliance().toString());
	}
}
