
package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This Command is designed to breach obstacles
 */
public class FullForwardCommand extends Command {

	boolean complete = false;
	
    public FullForwardCommand() {
        requires(Robot.secondaryDrive);
        requires(Robot.driveSubsystem);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.secondaryDrive.drive(0,1);
    	Robot.driveSubsystem.drive(0,1);
    }

    protected boolean isFinished() {
        return complete;
    }

    protected void end() {
    	Robot.secondaryDrive.drive(0,0);
    	Robot.driveSubsystem.drive(0,0);
    }

    protected void interrupted() {
    	complete = true;
    	end();
    }
}
