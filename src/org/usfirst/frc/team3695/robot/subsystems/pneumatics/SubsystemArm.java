package org.usfirst.frc.team3695.robot.subsystems.pneumatics;

import org.usfirst.frc.team3695.robot.Constants;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This subsystem uses pneumatics to move up and down the arm
 * of the robot.
 */
public class SubsystemArm extends Subsystem {
	private Solenoid armUp;
	private Solenoid armDown;
	private Solenoid latchEngage;
	private Solenoid latchDisengage;
	
	private String latchState = "Unknown";
	private String armState = "Unknown";
	
	public SubsystemArm() {
		super();
		armUp = new Solenoid(Constants.ARM_SOLENOID_UP);
		armDown = new Solenoid(Constants.ARM_SOLENOID_DOWN);
		latchEngage = new Solenoid(Constants.LATCH_SOLENOID_ENGAGE);
		latchDisengage = new Solenoid(Constants.LATCH_SOLENOID_DISENGAGE);
	}
	
	@Override
	protected void initDefaultCommand() {
	}
	
	/**
	 * Fires the ball.
	 */
	public void moveArmUp() {
		armDown.set(false);
		armUp.set(true);
		latchEngage.set(false);
		latchDisengage.set(true);
		armState = "Fired";
		latchState = "Disengaged";
	}
	
	/**
	 * Reset the arm.
	 */
	public void moveArmDown() {
		armDown.set(true);
		armUp.set(false);
		latchEngage.set(false);
		latchDisengage.set(true);
		armState = "Resetting...";
		latchState = "Disengaged";
	}
	
	/**
	 * Locks the latch.
	 */
	public void engageLatch() {
		latchEngage.set(true);
		latchDisengage.set(false);
		latchState = "Engaged";
	}
	
	public void log() {
		SmartDashboard.putString("Latch State", latchState);
		SmartDashboard.putString("Arm State", armState);
	}
}
