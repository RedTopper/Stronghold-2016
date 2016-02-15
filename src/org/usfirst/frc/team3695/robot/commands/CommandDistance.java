
package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command makes the robot drive forward at a certain speed for a certain distance.
 */
public class CommandDistance extends Command {

	double beginningDistance;
	double distance;
	double speed;
	
	boolean complete = false;
	
	/**
	 * Creates a command that tells the robot to go a distance.
	 * @param speed Range 0-1 (1 being full power).
	 * @param distance The distance in Stephens that the robot will go.
	 */
    public CommandDistance(double speed, double distance) {
        requires(Robot.driveSubsystem);
        this.beginningDistance = Robot.driveSubsystem.getDistance();
        this.distance = distance;
        this.speed = speed;
    }

    protected void initialize() {
    }

    protected void execute() {
    	if(Robot.driveSubsystem.getDistance() < 0) {
    		complete = true;
    		return; //Protect the robot!
    	}
    	Robot.driveSubsystem.drive(0,speed);
    	if(Robot.driveSubsystem.getDistance() < beginningDistance) {
    		complete = true; //Distance can't be negative!
    	}
    	if(Robot.driveSubsystem.getDistance() + beginningDistance > distance) {
    		complete = true;
    	}
    }

    protected boolean isFinished() {
        return complete;
    }

    protected void end() {
    	Robot.driveSubsystem.drive(0, 0);
    }

    protected void interrupted() {
    	end();
    }
}
