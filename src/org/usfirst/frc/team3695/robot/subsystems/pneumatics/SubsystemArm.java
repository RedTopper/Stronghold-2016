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
	private static final int LATCH_LOCKED = 0,
							 LATCH_NOT_LOCKED = 1,
							 PISTON_UP = 2,
							 PISTON_NOT_UP = 3;
	
	private Solenoid armPistonUp;
	private Solenoid armPistonDown;
	private Solenoid latchEngage;
	private Solenoid latchDisengage;
	
	/**
	 * String to put on the SmartDashboard;
	 */
	private String latchState = "Locked",
				   pistonState = "Down";
	
	/**
	 * True or false value of the current state of the object.
	 */
	private boolean latchLocked = true,
					pistonUp = false;
	
	/**
	 * The next state that the pneumatics will be. See the statics of this
	 * class.
	 */
	private int latchNextState = -1,
				pistonNextState = -1;
	
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
	 * Starts to lock the latch.
	 */
	public void engageLatch() {
		if(!subsystemLatchingLocked) {
			latchEngage.set(true);
			latchDisengage.set(false);
			latchState = "Engaging...";
			changeLatchState(LATCH_LOCKED);
		}
	}
	
	/**
	 * Starts to unlock the latch.
	 */
	public void disengageLatch() {
		if(!subsystemLatchingLocked) {
			latchEngage.set(false);
			latchDisengage.set(true);
			latchState = "Disengaging...";
			changeLatchState(LATCH_NOT_LOCKED);
		}
	}
	
	/**
	 * Starts to move the piston down. This actually moves the arm up.
	 */
	public void movePistonDown() {
		if(!subsystemPistonLocked) {
			armPistonDown.set(true);
			armPistonUp.set(false);
			pistonState = "Moving piston down...";
			changePistonState(PISTON_NOT_UP);
		}
	}
	
	/**
	 * Starts to move the piston up. This actually moves the arm down.
	 */
	public void movePistonUp() {
		if(!subsystemPistonLocked) {
			armPistonDown.set(false);
			armPistonUp.set(true);
			pistonState = "Moving piston up...";
			changePistonState(PISTON_UP);
		}
	}
	
	/**
	 * Returns if the latch is fully locked or not locked.
	 * @return true if the latch is fully locked, false otherwise.
	 */
	public boolean isLatchLocked() {
		if (latchNextState != -1 && lastLatchUpdateTime + Constants.TIME_TO_LATCH < System.currentTimeMillis()) {
			latchLocked = (latchNextState == LATCH_LOCKED ? true : false);
			if(latchLocked) {
				latchState = "Latch locked!";
			} else {
				latchState = "Latch unlocked!";
			}
			latchNextState = -1;
			subsystemLatchingLocked = false;
		}
		return latchLocked;
	}

	/**
	 * Returns if the piston is fully up.
	 * @return true if the piston is fully up, false otherwise.
	 */
	public boolean isPistonUp() {
		if (pistonNextState != -1 && lastPistonStateTime + Constants.TIME_TO_MOVE_ARM_PISTON < System.currentTimeMillis()) {
			pistonUp = (pistonNextState == PISTON_UP ? true : false);
			if(pistonUp) {
				pistonState = "Piston up!";
			} else {
				pistonState = "Piston down!";
			}
			pistonNextState = -1;
			subsystemPistonLocked = false;
		}
		return pistonUp;
	}
	
	private void changeLatchState(int status) {
		subsystemLatchingLocked = true;
		latchNextState = status;
		lastLatchUpdateTime = System.currentTimeMillis();
	}

	private void changePistonState(int status) {
		subsystemPistonLocked = true;
		pistonNextState = status;
		lastPistonStateTime = System.currentTimeMillis();
	}

	public void log() {
		SmartDashboard.putString("Latch State", latchState);
		SmartDashboard.putString("Piston State", pistonState);
		SmartDashboard.putString("Piston Locked", (subsystemPistonLocked ? "Subsystem is LOCKED" : "Ready" ));
		SmartDashboard.putString("Latching Locked", (subsystemPistonLocked ? "Subsystem is LOCKED" : "Ready" ));
	}
}
