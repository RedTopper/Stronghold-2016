package org.usfirst.frc.team3695.robot.subsystems.pneumatics;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.enumeration.Catapult;
import org.usfirst.frc.team3695.robot.enumeration.Latch;
import org.usfirst.frc.team3695.robot.util.Loggable;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This subsystem uses pneumatics to move up and down the arm
 * of the robot.
 */
public class SubsystemArm extends Subsystem implements Loggable {
	private Solenoid armPistonUp;
	private Solenoid armPistonDown;
	private Solenoid latchEngage;
	private Solenoid latchDisengage;
	
	private Catapult armPistonState = Catapult.UNKNOWN,
					   armPistonNextState = Catapult.UNKNOWN;

	private Latch latchCurrentState = Latch.UNKNOWN,
				  latchNextState = Latch.UNKNOWN;
	
	/**
	 * The time the object was last told to update.
	 */
	private long lastLatchUpdateTime = 0,
				 lastPistonStateTime = 0;
	
	/**
	 * True if this specific thing is locked down from activating.
	 */
	private boolean subsystemLatchingLocked = false,
					subsystemPistonLocked = false;
	
	/**
	 * Creates a very complicated method of managing the state of both
	 * the latch and catapult.
	 */
	public SubsystemArm() {
		super();
		armPistonUp = new Solenoid(Constants.ARM_PISTON_SOLENOID_UP);
		armPistonDown = new Solenoid(Constants.ARM_PISTON_SOLENOID_DOWN);
		latchEngage = new Solenoid(Constants.LATCH_SOLENOID_ENGAGE);
		latchDisengage = new Solenoid(Constants.LATCH_SOLENOID_DISENGAGE);
	}
	
	@Override
	protected void initDefaultCommand() {
	}
	
	/**
	 * Starts to lock the latch.
	 */
	public void engageLatch() {
		if(!subsystemLatchingLocked) {
			latchEngage.set(true);
			latchDisengage.set(false);
			latchCurrentState = Latch.LATCH_LOCKING; //Currently unlocked, but is locking.
			changeLatchState(Latch.LATCH_LOCKED);
		}
	}
	
	/**
	 * Starts to unlock the latch.
	 */
	public void disengageLatch() {
		if(!subsystemLatchingLocked) {
			latchEngage.set(false);
			latchDisengage.set(true);
			latchCurrentState = Latch.LATCH_UNLOCKING; //Currently locked, but is unlocking.
			changeLatchState(Latch.LATCH_NOT_LOCKED);
		}
	}
	
	/**
	 * Starts to move the piston down. This actually moves the arm up.
	 */
	public void movePistonDown() {
		if(!subsystemPistonLocked) {
			armPistonDown.set(true);
			armPistonUp.set(false);
			armPistonState = Catapult.PISTON_MOVING_DOWN; //Currently up, but is moving down.
			changePistonState(Catapult.PISTON_NOT_UP);
		}
	}
	
	/**
	 * Starts to move the piston up. This actually moves the arm down.
	 */
	public void movePistonUp() {
		if(!subsystemPistonLocked) {
			armPistonDown.set(false);
			armPistonUp.set(true);
			armPistonState = Catapult.PISTON_MOVING_UP; //Currently down, but is moving up.
			changePistonState(Catapult.PISTON_UP);
		}
	}
	
	/**
	 * Returns if the latch is FULLY locked or FULLY unlocked. If the latch is unlocking,
	 * this method should return the last state it was in until it completely moves to the 
	 * next state.
	 * @return TRUE if the latch is fully locked or was fully locked and is unlocking, FALSE
	 * if it is fully unlocked or was fully unlocked and is locking.
	 */
	public boolean isLatchLocked() {
		if (latchNextState != Latch.UNKNOWN && lastLatchUpdateTime + Constants.TIME_TO_LATCH < System.currentTimeMillis()) {
			if(latchNextState == Latch.LATCH_LOCKED) {
				latchCurrentState = Latch.LATCH_LOCKED;
			} else {
				latchCurrentState = Latch.LATCH_NOT_LOCKED;
			}
			latchNextState = Latch.UNKNOWN;
			subsystemLatchingLocked = false;
		}
		return latchCurrentState == Latch.LATCH_LOCKED || latchCurrentState == Latch.LATCH_UNLOCKING;
	}

	/**
	 * Returns if the piston is FULLY up or FULLY down. If the piston is moving, this
	 * method should return the last state it was in until it completely moves to the
	 * next state.
	 * @return TRUE if the piston is fully up or was fully up and is moving down, FALSE
	 * if it is fully down or was fully down and is moving up.
	 */
	public boolean isPistonUp() {
		if (armPistonNextState != Catapult.UNKNOWN && lastPistonStateTime + Constants.TIME_TO_MOVE_ARM_PISTON < System.currentTimeMillis()) {
			if(armPistonNextState == Catapult.PISTON_UP) {
				armPistonState = Catapult.PISTON_UP;
			} else {
				armPistonState = Catapult.PISTON_NOT_UP;
			}
			armPistonNextState = Catapult.UNKNOWN;
			subsystemPistonLocked = false;
		}
		return armPistonState == Catapult.PISTON_UP || armPistonState == Catapult.PISTON_MOVING_DOWN;
	}
	
	/**
	 * Use this method to initiate the changing of the status of the latch.
	 * @param status Use static ints for the latch.
	 */
	private void changeLatchState(Latch status) {
		subsystemLatchingLocked = true;
		latchNextState = status;
		lastLatchUpdateTime = System.currentTimeMillis();
	}

	/**
	 * Use this method to initiate the changing of the status of the piston.
	 * @param status Use static ints for the piston.
	 */
	private void changePistonState(Catapult status) {
		subsystemPistonLocked = true;
		armPistonNextState = status;
		lastPistonStateTime = System.currentTimeMillis();
	}

	public void log() {
		isPistonUp();
		isLatchLocked();
		
		String latchString = "Unknown.";
		if(latchCurrentState == Latch.LATCH_LOCKED) {latchString = "Latch locked.";}
		if(latchCurrentState == Latch.LATCH_LOCKING) {latchString = "Latch locking...";}
		if(latchCurrentState == Latch.LATCH_UNLOCKING) {latchString = "Latch unlocking...";}
		if(latchCurrentState == Latch.LATCH_NOT_LOCKED) {latchString = "Latch unlocked.";}
		SmartDashboard.putString("Latch State", latchString);
		
		String pistonString = "Unknown";
		if(armPistonState == Catapult.PISTON_UP) {pistonString = "Piston is up. Arm is down.";}
		if(armPistonState == Catapult.PISTON_MOVING_UP) {pistonString = "Piston moving up...";}
		if(armPistonState == Catapult.PISTON_MOVING_DOWN) {pistonString = "Piston moving down...";}
		if(armPistonState == Catapult.PISTON_NOT_UP) {pistonString = "Piston is down. Arm is up.";}
		SmartDashboard.putString("Piston State", pistonString);
		
		SmartDashboard.putString("Piston Subsystem Status", (subsystemPistonLocked ? "Subsystem is running a task!" : "Ready to go!" ));
		SmartDashboard.putString("Latching Subsystem Status", (subsystemLatchingLocked ? "Subsystem is running a task!" : "Ready to go!" ));
	}
}
