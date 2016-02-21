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
	private Solenoid armPistonUp;
	private Solenoid armPistonDown;
	private Solenoid latchEngage;
	private Solenoid latchDisengage;
	
	private String latchState = "Unknown";
	private String armState = "Unknown";
	
	public SubsystemArm() {
		super();
		armPistonUp = new Solenoid(Constants.ARM_SOLENOID_UP);
		armPistonDown = new Solenoid(Constants.ARM_SOLENOID_DOWN);
		latchEngage = new Solenoid(Constants.LATCH_SOLENOID_ENGAGE);
		latchDisengage = new Solenoid(Constants.LATCH_SOLENOID_DISENGAGE);
	}
	
	@Override
	protected void initDefaultCommand() {
	}
	
	/**
	 * Fires the ball. 
	 */
	public void fireBall() {
		armPistonDown.set(true);
		armPistonUp.set(false);
		latchEngage.set(false);
		latchDisengage.set(true);
		armState = "Fired (Pison down)";
		latchState = "Disengaged";
	}
	
	/**
	 * Reset the arm.
	 */
	public void resetArm() {
		armPistonDown.set(false);
		armPistonUp.set(true);
		latchEngage.set(false);
		latchDisengage.set(true);
		armState = "Resetting... (Pison moving up)";
		latchState = "Disengaged";
	}
	
	/**
	 * Locks the latch.
	 */
	public void engageLatch() {
		armPistonDown.set(false);
		armPistonUp.set(true);
		latchEngage.set(true);
		latchDisengage.set(false);
		armState = "Resetting... (Piston holding)";
		latchState = "Engaging";
	}

	/**
	 * Gets the pistons out of the way.
	 */
	public void getPisonsOutOfTheWay() {
		armPistonDown.set(true);
		armPistonUp.set(false);
		armState = "Resetting... (Pison moving down)";
	}
	
	public void ready() {
		armState = "Ready (Pison down)";
	}
	
	public void log() {
		SmartDashboard.putString("Latch State", latchState);
		SmartDashboard.putString("Arm State", armState);
	}
}
