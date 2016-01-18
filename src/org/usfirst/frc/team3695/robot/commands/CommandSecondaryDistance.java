
package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This Command is designed to breach obstacles
 */
public class CommandSecondaryDistance extends Command {

	double beginningDistance;
	double distance;
	double speed;
	
	boolean complete = false;
	
	/**
	 * Creates a command that is more powerful than CommandDistance because it uses ALL
	 * of the wheels!
	 * @param speed Range 0-1 (1 being FULL POWERRRR).
	 * @param distance The distance in Stephens that the robot will go.
	 */
    public CommandSecondaryDistance(double speed, double distance) {
        requires(Robot.secondaryDrive);
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
    	Robot.secondaryDrive.drive(0,speed);
    	Robot.driveSubsystem.drive(0,speed);
    	if(Robot.driveSubsystem.getDistance() < beginningDistance) {
    		complete = true;
    	}
    	if(Robot.driveSubsystem.getDistance() + beginningDistance > distance) {
    		complete = true;
    	}
    }

    protected boolean isFinished() {
        return complete;
    }

    protected void end() {
    	Robot.secondaryDrive.drive(0,0);
    	Robot.driveSubsystem.drive(0,0);
    }

    protected void interrupted() {
    	complete = true;
    }
}
