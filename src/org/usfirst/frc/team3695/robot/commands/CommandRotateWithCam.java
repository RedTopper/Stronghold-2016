package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Constants;
import org.usfirst.frc.team3695.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will move the robot to face a goal (hopefully) with
 * a camera.
 */
public class CommandRotateWithCam extends Command {
	/**
	 * Used for telling the robot to move a certain direction to aim
	 * at the goal. Use these in the constructor of the class. These
	 * also represent the overall direction the robot will move.
	 */
	public static final int ROTATE_LEFT_OVERALL = 0,
							ROTATE_RIGHT_OVERALL = 1;
	
	/**
	 * Used to tell the robot the current direction it should be moving. The
	 * values here are usually temporary.
	 */
	private static final int ROT_LEFT = 0,
			 				 ROT_RIGHT = 1;
	
	Preferences prefs = Preferences.getInstance();
	private int CAMERA_CALIBRATION_LR = prefs.getInt(Constants.CAMERA_CALIBRATION_LR_NAME, 0);
	
	private int direction;
	private boolean complete;
	private int stage = 0;
	private int error = 0;
	private int rotDir = 0;
	private long startPauseTime = 0;
	
	/**
	 * This will tell the robot witch way to move to face the goal the quickest.
	 * Use CommandRotateWithCam.ROTATE_LEFT_OVERALL or CommandRotateRightWithCam.ROTATE_RIGHT_OVERALL
	 * as the direction.
	 * @param int direction as stated above.
	 */
    public CommandRotateWithCam(int direction) {
    	requires(Robot.driveSubsystem);
        requires(Robot.networkTables);
        this.direction = direction;
        setTimeout(Constants.MAX_ROTATE_TIME);
    }

    protected void initialize() {
    	error = 0;
    	stage = 0;
    	rotDir = 0;
    	complete = false;
    }

    protected void execute(){
    	if (error  > Constants.MAX_ERRORS) {
    		Robot.STOP_AUTO = "The robot had too many camera errors.";
    		complete = true;
    	}
    	
    	double goalX = Robot.networkTables.getRawGoalX();
    	if(goalX == -1.0) { 
			error++;
			switch(rotDir) {
			case ROT_LEFT:
				goalX = Constants.CAMERA_WIDTH + 1;
				break;
			case ROT_RIGHT:
				goalX = 0;
				break;
			}
		}
    	
    	switch(direction) {
    	case ROTATE_RIGHT_OVERALL:
    		if(stage == 0) {
    			rotDir = ROT_RIGHT;
    			stage++;
    			break;
    		}
    		if(stage == 1 && goalX < Constants.CAMERA_WIDTH/2){
    			Robot.driveSubsystem.drive(1.0, 0);
    		} else {
    			rotDir = ROT_LEFT;
    			stage++;
    			break;
    		}
    		if(stage == 2) {
    			startPauseTime = System.currentTimeMillis();
    			stage++;
    		}
    		if(stage == 3 && startPauseTime < System.currentTimeMillis() - 1000) { //Magic number. Wait one second.
    			stage++;
    		}
    		if (stage == 4 && goalX > (Constants.CAMERA_WIDTH/2) + CAMERA_CALIBRATION_LR) {
    			Robot.driveSubsystem.drive(-0.3, 0);
    		} else {
    			stage++;
    			break;
    		}
    		if(stage == 5) {
    			complete = true;
    		}
    		break;
    	case ROTATE_LEFT_OVERALL:
    		if(stage == 0) {
    			rotDir = ROT_LEFT;
    			stage++;
    			break;
    		}
    		if(stage == 1 && goalX > Constants.CAMERA_WIDTH/2){
    			Robot.driveSubsystem.drive(-1.0, 0);
    		} else {
    			rotDir = ROT_RIGHT;
    			stage++;
    			break;
    		}
    		if(stage == 2) {
    			startPauseTime = System.currentTimeMillis();
    			stage++;
    		}
    		if(stage == 3 && startPauseTime < System.currentTimeMillis() - 1000) { //Magic number. Wait one second.
    			stage++;
    		}
    		if (stage == 4 && goalX < (Constants.CAMERA_WIDTH/2) - CAMERA_CALIBRATION_LR) {
    			Robot.driveSubsystem.drive(0.3, 0);
    		} else {
    			stage++;
    			break;
    		}
    		if(stage == 5) {
    			complete = true;
    		}
    		break;
    	default:
    		direction = ROTATE_RIGHT_OVERALL;
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
