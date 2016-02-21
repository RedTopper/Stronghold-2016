package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Controller;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.commands.CommandDrive;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This subsystem controls the driving motors and drive train variables. There are also
 * some unique sensors (like the accelerometer and stuff) that are included.
 */
public class SubsystemDrive extends Subsystem {
	private Talon frontLeft;
	private Talon frontRight;
	private Talon rearLeft;
	private Talon rearRight;
	
	private double[] x_g_buffer = new double[10];
	private double[] y_g_buffer = new double[x_g_buffer.length];
	private double[] z_g_buffer = new double[x_g_buffer.length];
	
	private RobotDrive driveTrain;
	
	private BuiltInAccelerometer builtInAccelerometer;
	
	private long timeStartRumble = 0;
	
	public SubsystemDrive() {
		super();
		
		frontLeft = new Talon(Constants.FRONT_LEFT_MOTOR_PORT);
		frontRight = new Talon(Constants.FRONT_RIGHT_MOTOR_PORT);
		rearLeft = new Talon(Constants.REAR_LEFT_MOTOR_PORT);
		rearRight = new Talon(Constants.REAR_RIGHT_MOTOR_PORT);
		driveTrain = new RobotDrive(frontLeft,rearLeft,frontRight,rearRight);
		
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, Constants.FRONT_LEFT_MOTOR_INVERT);
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontRight, Constants.FRONT_RIGHT_MOTOR_INVERT);
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearLeft, Constants.REAR_LEFT_MOTOR_INVERT);
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearRight, Constants.REAR_RIGHT_MOTOR_INVERT);
		
		builtInAccelerometer = new BuiltInAccelerometer();
	}
	
    public void initDefaultCommand() {
		setDefaultCommand(new CommandDrive());
    }
    
	/**
	 * The log method puts interesting information to the SmartDashboard.
	 */
	public void log() {
		for(int i = 0; i < x_g_buffer.length - 1; i++) {
			x_g_buffer[i] = x_g_buffer[i + 1];
			y_g_buffer[i] = y_g_buffer[i + 1];
			z_g_buffer[i] = z_g_buffer[i + 1];
		}
		
		x_g_buffer[x_g_buffer.length - 1] = builtInAccelerometer.getX();
		y_g_buffer[y_g_buffer.length - 1] = builtInAccelerometer.getY();
		z_g_buffer[z_g_buffer.length - 1] = builtInAccelerometer.getZ();
		
		double x = average(x_g_buffer);
		double y = average(y_g_buffer);
		double z = average(z_g_buffer);
		
		SmartDashboard.putNumber("Speed X m.s:", Math.abs(x * 9.8));
		SmartDashboard.putNumber("Speed Y m.s:", Math.abs(y * 9.8));
		SmartDashboard.putNumber("Speed Z m.s:", Math.abs(z * 9.8));
		rumble(x, y, z);
	}
	
	private void rumble(double x, double y, double z) {
		double downGForce;
		switch(Constants.DOWN_AXIS) {
			case "X":
				downGForce = x * (Constants.DOWN_IS_NEGATIVE ? -1.0f : 1.0f);
				break;
			case "Y":
				downGForce = y * (Constants.DOWN_IS_NEGATIVE ? -1.0f : 1.0f);
				break;
			default:
				downGForce = z * (Constants.DOWN_IS_NEGATIVE ? -1.0f : 1.0f);
				break;
		}
		if(downGForce > 1.0f + Constants.RUMBLE_BOUND_G_FORCE || downGForce < 1.0f - Constants.RUMBLE_BOUND_G_FORCE) {
			timeStartRumble = System.currentTimeMillis();
		}
	}

	/**
	 * Arcade style driving for the DriveTrain.
	 * @param x Speed in range [-1,1]
	 * @param y Speed in range [-1,1]
	 */
	public void drive(double x, double y) {
		driveTrain.arcadeDrive(x, y);
	}
	
	/**
	 * @param joy This should move the robot and rumble the controller.
	 * Passing the joy to this method is simply for rumble.
	 */
	public void drive(Joystick joy) {
		drive(Controller.DRIVE_X_AXIS(),Controller.DRIVE_Y_AXIS());
		if(Robot.isRumbleEnabled()) {
			joy.setRumble(RumbleType.kLeftRumble, (System.currentTimeMillis() < timeStartRumble + Constants.RUMBLE_TIME_MS ? 1.0f : 0.0f));
			joy.setRumble(RumbleType.kRightRumble, (System.currentTimeMillis() < timeStartRumble + Constants.RUMBLE_TIME_MS ? 1.0f : 0.0f));
		}
	}
	
	private double average(double[] list) {
		double sum = 0.0;
		for(double d : list) {
			sum += d;
		}
		return sum / (double)list.length;
	}
}

