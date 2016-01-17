
package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.commands.DriveCommand;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
	
	private Talon frontLeft;
	private Talon frontRight;
	private Talon rearLeft;
	private Talon rearRight;
	
	//TODO: Uncomment for encoders: private Encoder leftEncoder, rightEncoder;
	
	private RobotDrive driveTrain;
	
	private BuiltInAccelerometer builtInAccelerometer;
	
	public DriveSubsystem() {
		super();
		frontLeft = new Talon(Constants.FRONT_LEFT_MOTOR_PORT);
		frontRight = new Talon(Constants.FRONT_RIGHT_MOTOR_PORT);
		rearLeft = new Talon(Constants.REAR_LEFT_MOTOR_PORT);
		rearRight = new Talon(Constants.REAR_RIGHT_MOTOR_PORT);
		driveTrain = new RobotDrive(frontLeft,rearLeft,frontRight, rearRight);
		
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, Constants.FRONT_LEFT_MOTOR_INVERT);
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontRight, Constants.FRONT_RIGHT_MOTOR_INVERT);
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearLeft, Constants.REAR_LEFT_MOTOR_INVERT);
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearRight, Constants.REAR_RIGHT_MOTOR_INVERT);
		
		//TODO: Uncomment for encoders: leftEncoder = new Encoder(Constants.FRONT_LEFT_MOTOR_PORT, Constants.REAR_LEFT_MOTOR_PORT);
		//TODO: Uncomment for encoders: rightEncoder = new Encoder(Constants.FRONT_RIGHT_MOTOR_PORT, Constants.REAR_LEFT_MOTOR_PORT);
		
		//TODO: Uncomment for encoders: leftEncoder.setDistancePerPulse(Constants.DISTANCE_PER_PULSE);
		//TODO: Uncomment for encoders: rightEncoder.setDistancePerPulse(Constants.DISTANCE_PER_PULSE);
		
		builtInAccelerometer = new BuiltInAccelerometer();
	}
	
    public void initDefaultCommand() {
		setDefaultCommand(new DriveCommand());
    }
    
	/**
	 * The log method puts interesting information to the SmartDashboard.
	 */
	public void log() {
		//TODO: Uncomment for encoders: SmartDashboard.putNumber("Left Distance", leftEncoder.getDistance());
		//TODO: Uncomment for encoders: SmartDashboard.putNumber("Right Distance", rightEncoder.getDistance());
		//TODO: Uncomment for encoders: SmartDashboard.putNumber("Left Speed", leftEncoder.getRate());
		//TODO: Uncomment for encoders: SmartDashboard.putNumber("Right Speed", rightEncoder.getRate());
		
		SmartDashboard.putNumber("Acceleration X:", builtInAccelerometer.getX());
		SmartDashboard.putNumber("Acceleration Y:", builtInAccelerometer.getY());
		SmartDashboard.putNumber("Acceleration Z:", builtInAccelerometer.getZ());
	}
	
	/**
	 * Arcade style driving for the DriveTrain.
	 * @param left Speed in range [-1,1]
	 * @param right Speed in range [-1,1]
	 */
	public void drive(double left, double right) {
		driveTrain.arcadeDrive(left, right);
	}
	
	/**
	 * @param joy This should "just work".
	 */
	public void drive(Joystick joy) {
		drive(joy);
		//If we are using an XBox or PS3 controller, get the axis independently and send them as doubles to drive.
	}
	
	/**
	 * Reset the robots sensors to the zero states.
	 */
	public void reset() {
		//Zero units (such as a gyro) here
		//TODO: Uncomment for encoders: leftEncoder.reset();
		//TODO: Uncomment for encoders: rightEncoder.reset();
	}
	
	/**
	 * @return The distance driven (average of left and right encoders).
	 */
	public double getDistance() {
		//TODO: Uncomment for encoders: return (leftEncoder.getDistance() + rightEncoder.getDistance())/2;
		return 0.0;
	}
}

