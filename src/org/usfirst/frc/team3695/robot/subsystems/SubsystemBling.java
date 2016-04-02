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
	
	private int timeCount = 0;
	private boolean magic = false;
	private long curTime;
	private long flashUntil;

	protected void initDefaultCommand() {}
	
	/**
	 * Creates a colorful robot.
	 */
	public SubsystemBling(){
		super();
		redLEDS = new Solenoid(Constants.RED_LED_PORT);
		blueLEDS = new Solenoid(Constants.BLUE_LED_PORT);
		curTime = System.currentTimeMillis();
		flashUntil = 0;
	}
	
	/**
	 * Updates alliance lights based on currently selected alliance in driver station
	 */
	private void updateAlliance(){
		if (DriverStation.getInstance().getAlliance().toString().contains("Red"))
			redAlliance();
		else
			blueAlliance();
	}
	
	/**
	 * sets the amount of time the robot will flash for
	 * @param time Time in Millis
	 */
	public void setFlashTime(int time){
		flashUntil = curTime + time;
	}
	
	/**
	 * Used during auto or other functions to do non-abusive flash timing
	 */
	public void flash(){
		timeCount++;
		if (timeCount % 10 == 0){
			setLEDS(magic, !magic);
			magic = !magic;
			timeCount = 0;
		}
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
		//Display Current Team (used for debug)
		SmartDashboard.putString("Team", DriverStation.getInstance().getAlliance().toString());
		//update current time
		curTime = System.currentTimeMillis();
		//Flashing Logic
		if ((flashUntil - curTime) >= 0) //if still flashing (based on time)
			flash();
//		else if (DriverStation.getInstance().getMatchTime() > 10.0)
//			flash();
		else //if not still flashing (Teleop)
			updateAlliance();
	}
}
