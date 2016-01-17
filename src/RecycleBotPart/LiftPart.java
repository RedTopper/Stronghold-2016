package RecycleBotPart;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;

/** 
 * @author Team 3695
 * @version 2.0 
 * 
 * Return of the robot.
 */
public class LiftPart extends BotPart {

	private static int LIFT = 2;
	private static int LOWER = 3;
	
	private TalonSRX four = new TalonSRX(4);
	private Solenoid zero = new Solenoid(0);
	
	public LiftPart(Robot bot){
		super(bot);
	}
	
	public void updateTeleop() {
		if(bot.getSensor().getOpStick().getRawButton(LIFT)) {
			four.set(1);
			zero.set(false);
		} else if(bot.getSensor().getOpStick().getRawButton(LOWER)){
			four.set(0);
			zero.set(false);
		} else {
			four.set(0);
			zero.set(true);
		}
	}

	public void updateAuto() {
		
	}
}
