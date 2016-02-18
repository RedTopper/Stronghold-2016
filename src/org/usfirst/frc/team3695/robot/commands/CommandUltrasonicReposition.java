package org.usfirst.frc.team3695.robot.commands;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;
//TODO
//TODO define leftIndicator and rightIndicator as buttons, define leftProgressBar and rightProgressBar as progress
//TODO
public class CommandUltrasonicReposition extends Command {
	
	AnalogInput ultrasonicInput = new AnalogInput(0);
	boolean completed;
	double rightBarLength = 12;
	double leftBarLength = 12;
	
    public CommandUltrasonicReposition() {
    }
	@Override
	protected void initialize() {
	}
	@Override
	protected void execute() {
		requires(Robot.driveSubsystem);
		double ultrasonicInches = ultrasonicInput.getValue() / 5.32;
		double minRange = 60;
		double idealPosition = 66;
		double maxRange = 72;
		boolean outOfRange = false;
		boolean maxedRange = false;
		boolean maxedRangeAndBar = false;
		boolean minnedRange = false;
		boolean minnedRangeAndBar = false;
		boolean inTheZone = false;
		leftIndicator.enabled = false;
		rightIndicator.enabled = false;
		
		if(minRange > ultrasonicInches) {
			Robot.driveSubsystem.drive(1.0, 0.0);
			completed = false;
			minnedRange = true;
        }
        else if(maxRange < ultrasonicInches) {
        	Robot.driveSubsystem.drive(-1.0, 0.0);
        	completed = false;
        	maxedRange = true;
        }
        else if(minRange < ultrasonicInches || ultrasonicInches < maxRange) {
        	completed = true;
        	inTheZone = true;
        }
        else {
        	Robot.driveSubsystem.drive(-1.0, 0.0);
        	completed = false;
        	outOfRange = true;
        }
		//////////////////////////////// begin GUI code ///////////////////////////////////////
		
		leftProgressBar.rightToLeft = true;
		rightProgressBar.rightToLeft = false;
		
		if(maxedRange == true) {
			rightProgressBar.value = 0;
			leftProgressBar.rightToLeft = false;
			if(ultrasonicInches - maxRange < leftBarLength) {
				leftProgressBar.value = leftBarLength - (ultrasonicInches - maxRange);
			}
			else {
				leftProgressBar.value = 0;
				maxedRangeAndBar = true;
			}
		}
		else if(outOfRange == true) {
			leftIndicator.enabled = true;
		}
		else if(minnedRange == true) {
			leftProgressBar.value = 0;
			rightProgressBar.rightToLeft = false;
			if(minRange - ultrasonicInches < rightBarLength) {
				rightProgressBar.value = rightBarLength - (minRange - ultrasonicInches);
			}
			else {
				rightProgressBar.value = 0;
				minnedRangeAndBar = true;
			}
		}
		else if(inTheZone == true) {
			rightProgressBar.value = Math.abs(maxRange - ultrasonicInches);
			leftProgressBar.value = Math.abs(ultrasonicInches - minRange);
		}
		if(maxedRangeAndBar == true) {
			leftIndicator.enabled = true;
		}
		if(minnedRangeAndBar == true) {
			rightIndicator.enabled = true;
		}
	}
	@Override
	protected boolean isFinished() {
		return completed;
	}
	@Override
	protected void end() {
		leftIndicator.enabled = false;
		rightIndicator.enabled = false;
		leftProgressBar.value = 0;
		rightProgressBar.value = 0;
	}
	@Override
	protected void interrupted() {
		end(); //Usually default. Change as needed.
	}

}
