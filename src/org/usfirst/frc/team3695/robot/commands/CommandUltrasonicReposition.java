package org.usfirst.frc.team3695.robot.commands;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.Constants;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//TODO
//TODO define leftIndicator and rightIndicator as buttons, define leftProgressBar and rightProgressBar as progress
//TODO
//TODO I think that I should just take out the double and reverse complicated ass GUI
//TODO and just turn it into a progress bar with raw value and two pipes over it at
//TODO the max and min ranges, with a completion indicator next to it, wdut?
//TODO
public class CommandUltrasonicReposition extends Command {
	
	AnalogInput ultrasonicInput = new AnalogInput(0);
	boolean completed = true;
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
		requires(Robot.sensorsSubsystem);
		double ultrasonicInches = ultrasonicInput.getValue() / 5.32;
		boolean outOfRange = false;
		boolean maxedRange = false;
		boolean maxedRangeAndBar = false;
		boolean minnedRange = false;
		boolean minnedRangeAndBar = false;
		boolean inTheZone = false;
		
		if(MIN_RANGE > ultrasonicInches) {
			Robot.driveSubsystem.drive(1.0, 0.0);
			completed = false;
			minnedRange = true;
        } else if(MAX_RANGE < ultrasonicInches) {
        	Robot.driveSubsystem.drive(-1.0, 0.0);
        	completed = false;
        	maxedRange = true;
        } else if(MIN_RANGE < ultrasonicInches || ultrasonicInches < MAX_RANGE) {
        	completed = true;
        	inTheZone = true;
        } else {
        	Robot.driveSubsystem.drive(-1.0, 0.0);
        	completed = false;
        	outOfRange = true;
        }
		//////////////////////////////// begin GUI code ///////////////////////////////////////
		leftIndicator.enabled = false;
		rightIndicator.enabled = false;
		leftProgressBar.rightToLeft = true;
		rightProgressBar.rightToLeft = false;
		
		if(maxedRange == true) {
			rightProgressBar.value = 0;
			leftProgressBar.rightToLeft = false;
			if(ultrasonicInches - MIN_RANGE < leftBarLength) {
				leftProgressBar.value = leftBarLength - (ultrasonicInches - MAX_RANGE);
			} else {
				leftProgressBar.value = 0;
				maxedRangeAndBar = true;
			}
		} else if(outOfRange == true) {
			leftIndicator.enabled = true;
		} else if(minnedRange == true) {
			leftProgressBar.value = 0;
			rightProgressBar.rightToLeft = false;
			if(MIN_RANGE - ultrasonicInches < rightBarLength) {
				rightProgressBar.value = rightBarLength - (MIN_RANGE - ultrasonicInches);
			} else {
				rightProgressBar.value = 0;
				minnedRangeAndBar = true;
			}
		} else if(inTheZone == true) {
			rightProgressBar.value = Math.abs(MAX_RANGE - ultrasonicInches);
			leftProgressBar.value = Math.abs(ultrasonicInches - MIN_RANGE);
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
		end();
	}

}
