
package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command controls the drive subsystem with joysticks.
 */
public class DistanceCommand extends Command {

	double beginningDistance;
	double distance;
	double speed;
	
	boolean isFinished = false;
	
    public DistanceCommand(double speed, double distance) {
        requires(Robot.driveSubsystem);
        this.beginningDistance = Robot.driveSubsystem.getDistance();
        this.distance = distance;
        this.speed = speed;
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.driveSubsystem.drive(0,speed);
    	if(Robot.driveSubsystem.getDistance() < beginningDistance) {
    		end();
    	}
    	if(Robot.driveSubsystem.getDistance() + beginningDistance > distance) {
    		end();
    	}
    }

    protected boolean isFinished() {
        return isFinished;
    }

    protected void end() {
    	Robot.driveSubsystem.drive(0, 0);
    	isFinished = true;
    }

    protected void interrupted() {
    	end();
    }
}
