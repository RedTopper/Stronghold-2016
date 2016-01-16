package TestBotPart;
import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class DrivePart extends BotPart {
	
	private Talon left;
	private Talon right;
	private Joystick driverStick;
	private RobotDrive driveTrain;
	
	public DrivePart(Robot bot) {
		super(bot);
		driverStick = this.bot.getSensor().getDriveStick();
		left = new Talon(0);
		right = new Talon(1);
		// TODO Auto-generated constructor stub
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
