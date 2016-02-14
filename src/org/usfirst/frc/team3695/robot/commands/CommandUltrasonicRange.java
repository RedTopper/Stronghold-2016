package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CommandUltrasonicRange extends Command {
    public CommandUltrasonicRange() {
        requires(Robot.driveSubsystem);
        //The following will have to be created in the Robot class for the subsystem to work: 
        //(class header) 		public static SubsystemPositioning positioningSubsystem;
        //(class constructor) 	positioningSubsystem = new SubsystemPositioning();
        
        //Then you can uncomment this to require your subsystem.
        //requires(Robot.positioningSubsystem);
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
