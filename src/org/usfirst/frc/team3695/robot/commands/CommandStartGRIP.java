package org.usfirst.frc.team3695.robot.commands;

import java.io.IOException;

import edu.wpi.first.wpilibj.command.Command;

public class CommandStartGRIP extends Command {

	@Override
	protected void initialize() {
        try {
            new ProcessBuilder("/home/lvuser/grip").inheritIO().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
	}

}
