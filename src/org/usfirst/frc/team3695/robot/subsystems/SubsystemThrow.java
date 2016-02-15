package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SubsystemThrow extends Subsystem {
	private Solenoid throwing;
	
	public SubsystemThrow() {
		super();
		throwing = new Solenoid(Constants.THROW_SOLENOID_PORT);
	}
	
	@Override
	protected void initDefaultCommand() {
	}
	
	/**
	 * @deprecated
	 * Changes the state of the solenoid.
	 * 
	 * Note - This is a test method. Please add different, more relevant
	 * methods to this subsystem. For example, launchBall() or resetBall() etc.
	 * @param b True for on, false otherwise.
	 */
	public void setSolenoid(boolean b) {
		throwing.set(b);
	}
}
