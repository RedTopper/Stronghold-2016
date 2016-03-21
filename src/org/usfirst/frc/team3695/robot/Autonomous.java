package org.usfirst.frc.team3695.robot;

import org.usfirst.frc.team3695.robot.commands.CommandDriveTime;
import org.usfirst.frc.team3695.robot.commands.CommandDriveWithCam;
import org.usfirst.frc.team3695.robot.commands.CommandRotateWithCam;
import org.usfirst.frc.team3695.robot.commands.pneumatics.CommandMoveArm;
import org.usfirst.frc.team3695.robot.commands.pneumatics.CommandMoveBucket;
import org.usfirst.frc.team3695.robot.enumeration.objective.Defense;
import org.usfirst.frc.team3695.robot.enumeration.objective.MoveArm;
import org.usfirst.frc.team3695.robot.enumeration.objective.MoveBucket;
import org.usfirst.frc.team3695.robot.enumeration.objective.RotateWithCam;
import org.usfirst.frc.team3695.robot.util.CommandDoNothing;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Autonomous extends CommandGroup {
	public Autonomous(Defense objectiveDefense, RotateWithCam objectiveDirection) {
		switch(objectiveDefense) {
		case NOTHING:
			break;
		case LOW_BAR:
			addSequential(new CommandMoveBucket(MoveBucket.MOVE_DOWN));
			addSequential(new CommandDriveTime(3000, 0.6));
			finalize(objectiveDirection);
			break;
		case MOAT:
			addSequential(new CommandDriveTime(3500, 0.6));
			finalize(objectiveDirection);
			break;
		case RAMPARTS:
			addSequential(new CommandDriveTime(3500, 0.6));
			finalize(objectiveDirection);
			break;
		case ROCK_WALL:
			addSequential(new CommandDriveTime(3500, 0.6));
			finalize(objectiveDirection);
			break;
		case ROUGH_TERRAIN:
			addSequential(new CommandDriveTime(3500, 0.6));
			finalize(objectiveDirection);
			break;
		}
	}
	
	private void finalize(RotateWithCam objectiveDirection) {
		switch(objectiveDirection) {
		case NOTHING:
			break;
		case ROTATE_RIGHT_OVERALL:
		case ROTATE_LEFT_OVERALL:
			addSequential(new CommandMoveBucket(MoveBucket.MOVE_UP));
			addSequential(new CommandRotateWithCam(objectiveDirection));
			addSequential(new CommandDriveWithCam());
			addSequential(new CommandMoveBucket(MoveBucket.MOVE_DOWN));
			addSequential(new CommandDoNothing(1000));
			addSequential(new CommandMoveArm(MoveArm.FIRE));
			break;
		}
	}
}
