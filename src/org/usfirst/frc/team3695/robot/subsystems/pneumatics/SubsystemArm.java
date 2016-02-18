package org.usfirst.frc.team3695.robot.subsystems.pneumatics;

import org.usfirst.frc.team3695.robot.Constants;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SubsystemArm extends Subsystem {
	private Solenoid liftUp;
	private Solenoid liftDown;
	
	public SubsystemArm() {
		super();
		liftUp = new Solenoid(Constants.THROW_SOLENOID_PORT2);
		liftDown = new Solenoid(Constants.THROW_SOLENOID_PORT);
	}
	
	@Override
	protected void initDefaultCommand() {
	}
	
	public void fireUp(boolean state) {
		liftUp.set(state);
	}
	public void fireDown(boolean state){
		liftDown.set(state);
	}
}
