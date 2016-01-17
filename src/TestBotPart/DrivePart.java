package TestBotPart;
import org.usfirst.frc.team3695.robot.Robot;

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
		driverStick = this.bot.getSensor().getDriveStick();
		
		frontLeft = new Talon(0);
		frontRight = new Talon(3);
		rearLeft = new Talon(2);
		rearRight = new Talon(1);
	}

	public void updateAuto() {
		// TODO Auto-generated method stub
		
	}
	
	public void teleopInit(){
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontRight, false);
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearRight, false);
	}
	
	public void updateTeleop(){
		
	}

}
