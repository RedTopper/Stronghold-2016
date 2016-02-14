package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CommandRotateRightWithCam extends Command {

    public CommandRotateRightWithCam() {
        requires(Robot.driveSubsystem);
    }

    protected void initialize() {
    }

    protected void execute() {
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
