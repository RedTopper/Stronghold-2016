package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

public class CommandRotateWithCam extends Command {
	
	Preferences prefs = Preferences.getInstance();
	private int CAMERA_CALIBRATION_LR = prefs.getInt(Constants.CAMERA_CALIBRATION_LR_NAME, 0);
	
	/**
	 * Used for telling the robot to move a certain direction to aim
	 * at the goal. Use these in the constructor of the class.
	 */
	public static final String ROTATE_RIGHT = "RIGHT",
							   ROTATE_LEFT = "LEFT",
							   ALIGN_CENTER = "CENTER";
	
	private String direction;
	private boolean complete;
	private int stage = 0;
	private int error = 0;
	
    public CommandRotateWithCam(String string) {
    	requires(Robot.driveSubsystem);
        requires(Robot.networkTables);
        direction = string;
        setTimeout(Constants.MAX_ROTATE_TIME);
    }

    protected void initialize() {
    }

    protected void execute(){
    	if (error  > Constants.MAX_ERRORS) {
    		Robot.STOP_AUTO = "The robot had too many camera errors.";
    		complete = true;
    	}
    	switch(direction) {
    	case ROTATE_RIGHT:
    		if(stage == 0 && Robot.networkTables.getRawGoalX() < (Constants.CAMERA_WIDTH/2) - CAMERA_CALIBRATION_LR){
    			Robot.driveSubsystem.drive(0, 1);
    		} else {
    			stage++;
    			Robot.driveSubsystem.drive(0, -0.15);
    		}
    		if (Robot.networkTables.getRawGoalX() != -1.0) { //We can see the goal.
	    		if (stage == 1 && Robot.networkTables.getRawGoalX() > (Constants.CAMERA_WIDTH/2) + CAMERA_CALIBRATION_LR) {
	    			Robot.driveSubsystem.drive(0, -0.15);
	    		} else {
	    			stage++;
	    		}
	    		if(stage == 2) {
	    			complete = true;
	    		}
    		} else {
    			error++;
    		}
    		break;
    	case ROTATE_LEFT:
    		double rawX = (Robot.networkTables.getRawGoalX() == -1.0 ? Constants.CAMERA_WIDTH : Robot.networkTables.getRawGoalX()); //If of screen then max.
    		if(stage == 0 && rawX > (Constants.CAMERA_WIDTH/2) + CAMERA_CALIBRATION_LR){ //For this we don't care about if the goal is of screen.
    			Robot.driveSubsystem.drive(0, -1);
    		} else {
    			stage++;
    			Robot.driveSubsystem.drive(0, 0.15);
    		}
	    	if (Robot.networkTables.getRawGoalX() != -1.0) { //We can see the goal.
	    		if (stage == 1 && Robot.networkTables.getRawGoalX() < (Constants.CAMERA_WIDTH/2) - CAMERA_CALIBRATION_LR) {
	    			Robot.driveSubsystem.drive(0, 0.15);
	    		} else {
	    			stage++;
	    		}
	    		if(stage == 2) {
	    			complete = true;
	    		}
    		} else {
    			error++;
    		}
    		break;
    	default:
    		direction = ROTATE_RIGHT;
    		break;
    	}
    }

    protected boolean isFinished() {
    	if(isTimedOut()){ 
    		Robot.STOP_AUTO = "The time it took for the goal to be found was too high.";
    	}
		return isTimedOut() || complete;
    }

    protected void end() {
    	Robot.driveSubsystem.drive(0, 0);
    }

    protected void interrupted() {
    	end();
    }
}
