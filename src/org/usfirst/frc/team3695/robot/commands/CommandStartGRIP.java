package org.usfirst.frc.team3695.robot.commands;

import java.io.IOException;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Starts the GRIP platform on the robot.
 * Grip path might change?
 */
public class CommandStartGRIP extends Command {

	protected void initialize() {
        try {
            new ProcessBuilder("/home/lvuser/grip").inheritIO().start(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
