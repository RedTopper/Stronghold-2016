
package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.commands.SecondaryDriveCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This subsystem controls the middle drive motors and absolutely no drivetrain variables
 * There are also no sensors (like the accelerometer and stuff) that are included.
 * (Main Drive Train does those)
 */
public class SecondaryDrive extends Subsystem {
	
	private Talon middleLeft;
	private Talon middleRight;

	
	public SecondaryDrive() {
		super();
		middleLeft = new Talon(Constants.MIDDLE_LEFT_MOTOR_PORT);
		middleRight = new Talon(Constants.MIDDLE_RIGHT_MOTOR_PORT);
	}
	
    public void initDefaultCommand() {
		setDefaultCommand(new SecondaryDriveCommand());
    }
    
	/**
	 * The log method puts interesting information to the SmartDashboard.
	 */
	public void log() {}
	
	/**
	 * Arcade style driving for the DriveTrain.
	 * @param left Speed in range [-1,1]
	 * @param right Speed in range [-1,1]
	 */
	public void drive(double x, double y) {
		middleLeft.set(y * Constants.MIDDLE_LEFT_MOTOR_INVERT);
		middleRight.set(y * Constants.MIDDLE_RIGHT_MOTOR_INVERT);
	}
	
	/**
	 * @param joy This should "just work".
	 */
	public void drive(Joystick joy) {
		drive(joy.getX(),joy.getY());
	}
	
	/**
	 * Full Forward, might be useful
	 */
	public void justGo(){
		middleLeft.set(1 * Constants.MIDDLE_LEFT_MOTOR_INVERT);
		middleRight.set(1 * Constants.MIDDLE_RIGHT_MOTOR_INVERT);
	}
	
	/**
	 * Immediate stop Motors, Might be used for safety...
	 */
	public void justStop(){
		middleLeft.set(0);
		middleRight.set(0);
	}
}

