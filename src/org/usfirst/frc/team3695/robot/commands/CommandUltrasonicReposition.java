package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;

public class CommandUltrasonicReposition extends Command {
	
	AnalogInput ultrasonicInput = new AnalogInput(0);
	boolean completed;
	
	
	
    public CommandUltrasonicReposition() {
        
    }
	
	@Override
	protected void initialize() {
        //TODO Colton/Matt figure out how pidget inputs the range data
	}

	@Override
	protected void execute() {
		
		requires(Robot.driveSubsystem);
		
		double ultrasonicInches = ultrasonicInput.getValue() / 5.32;
		
		double minRange = 60;
		double idealPosition = 66;
		double maxRange = 72;
		
		
		if(minRange > ultrasonicInches) {
        	
			Robot.driveSubsystem.drive(1.0, 0.0);
			completed = false;
        	
        }
        else if(maxRange < ultrasonicInches){
        	
        	Robot.driveSubsystem.drive(-1.0, 0.0);
        	completed = false;
        	
        }
        else if(minRange < ultrasonicInches || ultrasonicInches < maxRange) {
        	
        	completed = true;
        	
        }
        else {
        	
        	Robot.driveSubsystem.drive(-1.0, 0.0);
        	completed = false;
        	
        }
	}

	@Override
	protected boolean isFinished() {
		return completed;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end(); //Usually default. Change as needed.
	}

}
