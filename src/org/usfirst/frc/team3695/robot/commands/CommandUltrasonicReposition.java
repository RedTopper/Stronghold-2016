package org.usfirst.frc.team3695.robot.commands;
import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.Constants;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;
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
		requires(Robot.driveSubsystem);
    }
	@Override
	protected void initialize() {
	}
	@Override
	protected void execute() {
		double ultrasonicInches = ultrasonicInput.getValue() / 5.32;
		if(Constants.MIN_RANGE > ultrasonicInches) {Robot.driveSubsystem.tankdrive(-1.0, -1.0);}
		else if(Constants.MAX_RANGE < ultrasonicInches) {Robot.driveSubsystem.tankdrive(1.0, 1.0);}
		else if(Constants.MIN_RANGE < ultrasonicInches && ultrasonicInches < Constants.MAX_RANGE) {/* ( ͡° ͜ʖ ͡°) */}
		else {Robot.driveSubsystem.tankdrive(1.0, 1.0);}
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
		end();
	}

}
