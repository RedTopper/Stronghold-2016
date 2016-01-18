
package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command controls the drive subsystem with joysticks.
 */
public class CommandDrive extends Command {

    public CommandDrive() {
        requires(Robot.driveSubsystem);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.driveSubsystem.drive(Robot.oi.getDriveStick());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.driveSubsystem.drive(0, 0);
    }

    protected void interrupted() {
    	end();
    }
}
