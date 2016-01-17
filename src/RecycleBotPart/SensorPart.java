package RecycleBotPart;

import org.usfirst.frc.team3695.robot.*;
import edu.wpi.first.wpilibj.Joystick;

public class SensorPart extends BotPart {

	private Joystick driveStick;
	private Joystick opStick;
	
	public SensorPart(Robot bot){
		super(bot);
		
		driveStick = new Joystick(1);
		driveStick = new Joystick(2);
	}
	
	public Joystick getDriveStick()
	{return driveStick;}
	
	public Joystick getOpStick()
	{return opStick;}
}
