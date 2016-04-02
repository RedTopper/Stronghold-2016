package org.usfirst.frc.team3695.robot.subsystems;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Controller;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.commands.CommandDrive;
import org.usfirst.frc.team3695.robot.util.Loggable;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This subsystem controls the driving motors and drive train variables. There are also
 * some unique sensors (like the accelerometer and stuff) that are included.
 */
public class SubsystemDrive extends Subsystem implements Loggable {
	private CANTalon frontLeft;
	private CANTalon frontRight;
	private CANTalon rearLeft;
	private CANTalon rearRight;
	private RobotDrive driveTrain;
	private BuiltInAccelerometer builtInAccelerometer;
	
	/**
	 * An array of the last ten g-force values on the
	 * x, y, and z values of the built in accelerometer.
	 */
	private double[] x_g_buffer = new double[10],
					 y_g_buffer = new double[x_g_buffer.length],
					 z_g_buffer = new double[x_g_buffer.length];
	
	private long timeStartRumble = 0;
	
	/**
	 * Creates the ability to drive the robot.
	 */
	public SubsystemDrive() {
		super();
		
		frontLeft = new CANTalon(Constants.FRONT_LEFT_MOTOR_PORT);
		frontRight = new CANTalon(Constants.FRONT_RIGHT_MOTOR_PORT);
		rearLeft = new CANTalon(Constants.REAR_LEFT_MOTOR_PORT);
		rearRight = new CANTalon(Constants.REAR_RIGHT_MOTOR_PORT);
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
     * Drives the robot like a tank.
     * @param left The speed of the left wheels from -1 to 1, where -1 is the max
     * reverse speed and 1 is the max forward speed.
     * @param right The speed of the right wheels from -1 to 1, where -1 is the max
     * reverse speed and 1 is the max forward speed.
     */
	public void tankdrive(double left, double right) {
		driveTrain.tankDrive(left,right);
	}
	
	/**
	 * Uses a controller to drive the tank.
	 * @param joy The joystick to rumple when the tank is 
	 * being  driven.
	 */
	public void tankdrive(Joystick joy) {
		double[] tank = Controller.DRIVE_AXIS();
		tankdrive(tank[0],tank[1]);
		if(Robot.isRumbleEnabled()) {
			if (System.currentTimeMillis() < timeStartRumble + Constants.RUMBLE_TIME_MS) {
				joy.setRumble(RumbleType.kLeftRumble, 1.0f);
				joy.setRumble(RumbleType.kRightRumble, 1.0f);
				Robot.blingSubsystem.flash();
			} else {
				joy.setRumble(RumbleType.kLeftRumble, 0.0f);
				joy.setRumble(RumbleType.kRightRumble, 0.0f);
			}
		}
	}
	
	/**
	 * Rumbles a controller when some amount of g-force is exceeded. 
	 * See the Constants.RUMBLE_BOUND_G_FORCE for more detail.
	 * @param x X g force.
	 * @param y Y g force.
	 * @param z Z g force.
	 */
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
	 * Averages a list and returns a double.
	 * @param list List to average
	 * @return An average
	 */
	private double average(double[] list) {
		double sum = 0.0;
		for(double d : list) {
			sum += d;
		}
		return sum / (double)list.length;
	}

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
		
		SmartDashboard.putNumber("Speed X", Math.abs(x * 9.8));
		SmartDashboard.putNumber("Speed Y", Math.abs(y * 9.8));
		SmartDashboard.putNumber("Speed Z", Math.abs(z * 9.8));
		rumble(x, y, z);
	}
}

