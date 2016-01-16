package TestBotPart;
import org.usfirst.frc.team3695.robot.Robot;

public abstract class BotPart {
	protected Robot bot;
	
	public BotPart(Robot bot){
		this.bot = bot;
	}

	public void updateTeleop(){
	}
	
	public void updateAuto(){
	}
	
	public void updateTest(){
	}
}
