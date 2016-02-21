package org.usfirst.frc.team3695.robot.commands;
import org.usfirst.frc.team3695.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CommandPhotoelectric extends Command {
	double pickup;
	double loaded;
	boolean fire;
	boolean detect;
	double limit = 0.7;
	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		pickup = Robot.sensorsSubsystem.getPhotoPickupVoltage();
		loaded = Robot.sensorsSubsystem.getPhotoLoadedVoltage();
		SmartDashboard.putNumber("Test output = " , pickup);
		if (pickup > limit){
			detect = true;
		}
		else{
			detect = false;
		}
		SmartDashboard.putBoolean("Pickup = ",detect);
		if (loaded > limit){
			fire = true;
		}
		else{
			fire = false;
		}
		SmartDashboard.putBoolean("Pickup = ",detect);
		SmartDashboard.putBoolean("Loaded = ",fire);
	}

	@Override
	protected void initialize() {
	}
	
	@Override
	protected void interrupted() {
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
