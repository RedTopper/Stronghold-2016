package TestBotPart;
import org.usfirst.frc.team3695.robot.Robot;
//test commit

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
