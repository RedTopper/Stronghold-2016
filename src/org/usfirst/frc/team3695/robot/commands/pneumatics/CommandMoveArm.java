package org.usfirst.frc.team3695.robot.commands.pneumatics;

import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.objective.MoveArm;
import org.usfirst.frc.team3695.robot.util.Logger;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command fires the arm (ball launcher) or resets it's state.
 */
public class CommandMoveArm extends Command {
	private MoveArm objective;
	private boolean complete = false;
	
	/**
	 * Creates a command to move the arm based on an objective.
	 * @param objective Use MoveArm.FIRE to fire the arm and
	 * MoveArm.RESET to move the arm back to the robot.
	 */
	public CommandMoveArm(MoveArm objective) {
		requires(Robot.armSubsystem);
		this.objective = objective;
	}
	
	protected void initialize() {
		Logger.debug(Robot.sensorsSubsystem.getPressure() + " PSI Before moving Arm");
    	complete = false;
    	if(Robot.AUTOING && Robot.STOP_AUTO != null) {
    		complete = true;
    		return;
    	}
		switch(objective) {
		case FIRE:
			Robot.armSubsystem.movePistonDown();
			complete = false;
			break;
		case RESET:
			Robot.armSubsystem.disengageLatch();
			Robot.armSubsystem.movePistonUp();
			complete = false;
			break;
		}
	}

	protected void execute() {
    	if(complete) {
    		return;
    	}
		switch(objective) {
		case FIRE:
			if(!Robot.armSubsystem.isPistonUp()) {
				Robot.armSubsystem.disengageLatch();
				complete = true;
			}
			break;
		case RESET:
			if(Robot.armSubsystem.isPistonUp()) {
				Robot.armSubsystem.engageLatch();
				complete = true;
			}
			break;
		}
	}

	protected boolean isFinished() {
		return complete;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
