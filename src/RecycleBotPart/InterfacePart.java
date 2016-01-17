package RecycleBotPart;

import org.usfirst.frc.team3695.robot.*;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class InterfacePart extends BotPart {

	//private SmartDashboard dash;
	
	private boolean FrontRight;
	private boolean FrontLeft;
	private boolean RearRight;
	private boolean RearLeft;

	public InterfacePart(Robot bot){
		super(bot);
		
		//dash = new SmartDashboard();

		FrontRight = true;
		FrontLeft = true;
		RearRight = true;
		RearLeft = false;

		SmartDashboard.putBoolean("iFrontRight", FrontRight);
		SmartDashboard.putBoolean("iFrontLeft", FrontLeft);
		SmartDashboard.putBoolean("iRearRight", RearRight);
		SmartDashboard.putBoolean("iRearLeft", RearLeft);
	}

	public void teleopInit(){

		FrontRight = SmartDashboard.getBoolean("iFrontRight");
		FrontLeft = SmartDashboard.getBoolean("iFrontLeft");
		RearRight = SmartDashboard.getBoolean("iRearRight");
		RearLeft = SmartDashboard.getBoolean("iRearLeft");
	}

	public boolean getFrontRight()
	{return FrontRight;}

	public boolean getFrontLeft()
	{return FrontLeft;}

	public boolean getRearRight()
	{return RearRight;}

	public boolean getRearLeft()
	{return RearLeft;}
	
	public void putNumber(String key, double d) {
		SmartDashboard.putNumber(key, d);
	}
}
