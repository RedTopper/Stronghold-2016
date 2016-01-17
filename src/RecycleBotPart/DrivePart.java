package RecycleBotPart;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.TalonSRX;

public class DrivePart extends BotPart {

	private static int STEPHEN_BUTTON = 2; //button to switch from mecanum and tank drive.
	
	private TalonSRX frontLeft;
	private TalonSRX rearLeft;
	private TalonSRX frontRight;
	private TalonSRX rearRight;
	
	private Joystick driveStick;
	
	private RobotDrive driveTrain;
	
	private Robot bot;
	
	public DrivePart(Robot kbot){
		super(kbot);
		
		frontLeft = new TalonSRX(1);
		rearLeft = new TalonSRX(2);
		frontRight = new TalonSRX(3);
		rearRight = new TalonSRX(4);
		
		bot = kbot;
		
		driveStick = bot.getSensor().getDriveStick();
		
		driveTrain = new RobotDrive(frontLeft,rearLeft,frontRight,rearRight);
		
	}

	public void teleopInit(){

		driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, bot.getStation().getFrontLeft());
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontRight, bot.getStation().getFrontRight());
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearLeft, bot.getStation().getRearLeft());
		driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearRight, bot.getStation().getRearRight());	
	}

	public void updateTeleop(){
		
		if(driveStick.getRawButton(STEPHEN_BUTTON))
			driveTrain.mecanumDrive_Cartesian(0, driveStick.getY(), driveStick.getX(), 0);
		else
			driveTrain.mecanumDrive_Cartesian(driveStick.getX(), driveStick.getY(), 0, 0);
	}
}
