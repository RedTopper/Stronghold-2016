package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;

public class CommandUltrasonicReposition extends Command {
	
	AnalogInput ultrasonicInput = new AnalogInput(0);
	double ultrasonicInches = ultrasonicInput.getValue() / 5.32;
	
	double minRange = 60;
	double idealPosition = 66;
	double maxRange = 72;
	
	
	
    public CommandUltrasonicReposition() {
        requires(Robot.driveSubsystem);
        
        if(minRange > ultrasonicInches) {
        	
        	//drive backward |idealPosition - ultrasonicInches| inches
        	
        }
        else if(maxRange < ultrasonicInches){
        	
        	//drive forward |idealPosition - ultrasonicInches| inches
        	
        }
        else if(minRange < ultrasonicInches || ultrasonicInches < maxRange) {
        	
        	//drive forwards/backwards |idealPosition - ultrasonicInches| inches
        	
        }
        else {
        	
        	//drive forward until ultrasonicInches is within range
        	
        }
    }
	
	@Override
	protected void initialize() {
        //TODO Colton/Matt figure out how pidget inputs the range data
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end(); //Usually default. Change as needed.
	}

}
