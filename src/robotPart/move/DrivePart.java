package robotPart.move;
import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.OI;
import org.usfirst.frc.team3695.robot.Robot;

import robotPart.BotPart;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class DrivePart extends BotPart {
	
	private Talon frontLeft;
	private Talon frontRight;
	private Talon rearLeft;
	private Talon rearRight;
	
	private Joystick driverStick;
	
	private RobotDrive driveTrain;
	
	private Robot bot;
	
	public DrivePart(Robot rbot) {
		super(rbot);
		bot = rbot;
		
		driverStick = OI.getDriveStick();
		
		frontLeft = new Talon(Constants.FRONT_LEFT_MOTOR_PORT);
		frontRight = new Talon(Constants.FRONT_RIGHT_MOTOR_PORT);
		rearLeft = new Talon(Constants.REAR_LEFT_MOTOR_PORT);
		rearRight = new Talon(Constants.REAR_RIGHT_MOTOR_PORT);
		
		driveTrain = new RobotDrive(frontLeft,rearLeft,frontRight, rearRight);
	}

	public void updateAuto() {
		// TODO Autonomous Drive
		
	}
	
	public void teleopInit(){
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, Constants.FRONT_LEFT_MOTOR_INVERT);
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontRight, Constants.FRONT_RIGHT_MOTOR_INVERT);
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearLeft, Constants.REAR_LEFT_MOTOR_INVERT);
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearRight, Constants.REAR_RIGHT_MOTOR_INVERT);
	}
	
	public void updateTeleop(){
		driveTrain.arcadeDrive(driverStick);
	}

}
