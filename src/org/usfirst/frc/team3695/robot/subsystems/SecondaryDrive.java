
package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
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

	private RobotDrive driveTrain2;
	
	public SecondaryDrive() {
		super();
		middleLeft = new Talon(Constants.MIDDLE_LEFT_MOTOR_PORT);
		middleRight = new Talon(Constants.MIDDLE_RIGHT_MOTOR_PORT);
		driveTrain2 = new RobotDrive(middleLeft, middleRight);
		
		driveTrain2.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, Constants.MIDDLE_LEFT_MOTOR_INVERT);
		driveTrain2.setInvertedMotor(RobotDrive.MotorType.kFrontRight, Constants.MIDDLE_RIGHT_MOTOR_INVERT);
	}
	
    public void initDefaultCommand() {
    	//Secondary Drive is disabled by default
		//setDefaultCommand(new SecondaryDriveCommand()); 
    }
    
	/**
	 * The log method isn't used, but may be
	 */
	public void log() {}
	
	/**
	 * 'Tank' driving for the DriveTrain.
	 * @param x Speed in range [-1,1] not used
	 * @param y Speed in range [-1,1]
	 */
	public void drive(double x, double y) {
		driveTrain2.arcadeDrive(x, y);
	}
	
	/**
	 * @param joy This should work
	 */
	public void drive(Joystick joy) {
		drive(joy.getX(),joy.getY());
	}
	
	/**
	 * Full Forward, might be useful
	 */
	public void justGo(){
		driveTrain2.arcadeDrive(0, 1);
	}
	
	/**
	 * Immediate stop Motors, Might be used for safety...
	 */
	public void justStop(){
		driveTrain2.arcadeDrive(0, 0);
	}
}

