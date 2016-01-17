package RecycleBotPart;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.Solenoid;

/** 
 * @author Team 3695
 * @version 2.0 
 * 
 * Return of the robot.
 */
public class ArmPart extends BotPart {

	//This arm does something - DRIVE TEST
	
	private static int BUTTON_OUT = 4;
	private static int BUTTON_IN = 5;
	
	private Solenoid one = new Solenoid(1);
	private Solenoid two = new Solenoid(2);
	
	public ArmPart(Robot bot){
		super(bot);
		
	}
	
	public void updateTeleop() {
		if(bot.getSensor().getOpStick().getRawButton(BUTTON_OUT)) {
			one.set(true);
			two.set(false);
		} else if(bot.getSensor().getOpStick().getRawButton(BUTTON_IN)) {
			one.set(false);
			two.set(true);
		} else {
			one.set(false);
			two.set(false);
		}
	}
	
	public void updateAuto() {
	
	}
}

