package TestBotPart;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;

public class SensorPart extends BotPart {
	private Joystick driveStick;
	private Joystick opStick;
	
	public SensorPart(Robot bot){
		super(bot);
		
		driveStick = new Joystick(0);
		opStick = new Joystick(1);
	}
	
	public Joystick getDriveStick()
	{return driveStick;}
	
	public Joystick getOpStick()
	{return opStick;}
	
	public void updateTeleop() {
		
	}

	public void updateAuto() {
		// TODO Auto-generated method stub
		
	}
}
