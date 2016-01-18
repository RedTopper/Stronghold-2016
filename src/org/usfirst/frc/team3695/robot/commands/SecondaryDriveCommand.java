
package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command controls the second drive subsystem with/or without joysticks.
 */
public class SecondaryDriveCommand extends Command {
	
	boolean complete = false;
	
    public SecondaryDriveCommand() {
        requires(Robot.secondaryDrive);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.secondaryDrive.drive(Robot.oi.getDriveStick());
    }

    protected boolean isFinished() {
        return complete;
    }

    protected void end() {
    	Robot.secondaryDrive.drive(0, 0);
    }

    protected void interrupted() {
    	complete = true;
    }
}
