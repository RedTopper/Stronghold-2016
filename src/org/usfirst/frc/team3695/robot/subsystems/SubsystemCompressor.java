package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.util.Loggable;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SubsystemCompressor extends Subsystem implements Loggable {
	private Compressor comp = new Compressor();

	protected void initDefaultCommand() {
	}
	
	/**
	 * @return whether or not compressor is running
	 */
	public boolean isEnabled(){
		return comp.enabled();
	}
	
	/**
	 * sets compressor state
	 * @param state Desired compressor state
	 */
	public void setState(boolean state){
		if (state)
			comp.start();
		else
			comp.stop();
	}
	
	/**
	 * toggles compressor's current state
	 */
	public void toggle(){
		setState(!isEnabled());
	}
	
	public void log(){
		SmartDashboard.putBoolean("Compressor Status", isEnabled());
	}
}
