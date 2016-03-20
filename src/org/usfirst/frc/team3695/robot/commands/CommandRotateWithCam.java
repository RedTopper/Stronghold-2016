package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Logger;
import org.usfirst.frc.team3695.robot.util.Util;
import org.usfirst.frc.team3695.robot.vision.Camera;
import org.usfirst.frc.team3695.robot.vision.CameraConstants;

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
	
	private int objective;
	private boolean complete;
	private int stage = 0;
	private int calibration = Util.setAndGetNumber("ROT", "Calibration Value", 10);
	private int center = Util.setAndGetNumber("ROT", "Center Offset Value", 0);
	
	private long lastTime = 0;
	private Camera cam = Camera.getInstance();
	
	/**
	 * This will tell the robot witch way to move to face the goal the quickest.
	 * @param direction use CommandRotateWithCam.ROTATE_LEFT_OVERALL or 
	 * CommandRotateRightWithCam.ROTATE_RIGHT_OVERALL to tell the robot to 
	 * move in a direction.
	 */
    public CommandRotateWithCam(int objective) {
    	requires(Robot.driveSubsystem);
        this.objective = objective;
        setTimeout(CameraConstants.MAX_ROTATE_TIME);
    }

    protected void initialize() {
    	calibration = Util.setAndGetNumber("ROT", "Calibration Value", 10);
    	center = Util.setAndGetNumber("ROT", "Center Offset Value", 0);
    	stage = 0;
    	complete = false;
    	if(cam != null) {
    		cam.controllerable(false);
    		cam.switchCam(Camera.FRONT_PROCESSED);
    	} else {
    		Logger.err("The camera isn't a thing, yo.");
    	}
    	lastTime = System.currentTimeMillis(); 	
    }

    protected void execute(){
		if(!cam.isProccessingCamera()) {
			lastTime = System.currentTimeMillis(); 			//Wait for the camera to switch over.
			return;
		}
		if(lastTime + 700 > System.currentTimeMillis()) { 	//Wait for the camera to actually update the images.
			return;
		}
    	double goalX = cam.getGoalXY()[0];
    	
    	switch(objective) {
    	case ROTATE_RIGHT_OVERALL:
    		if(stage == 0) {
    			Robot.driveSubsystem.tankdrive(0.8, -0.8);
    			if(goalX != -1.0 && goalX > 0.0) {
    				stage++;
    			}
    		}
    		break;
    	case ROTATE_LEFT_OVERALL:
    		if(stage == 0) {
    			Robot.driveSubsystem.tankdrive(-0.8, 0.8);
    			if(goalX != -1.0 && goalX > 0.0) {
    				stage++;
    			}
    		}
    		break;
    	default:
    		objective = ROTATE_RIGHT_OVERALL;
    		break;
    	}
    	
    	if(stage == 1) {
    		if(goalX != -1.0) {
	    		if(goalX > (double)(CameraConstants.LOW_RES_CAMERA_WIDTH)/2.0 + calibration + center) {
	    			Robot.driveSubsystem.tankdrive(0.6, -0.6);
	    		} else if(goalX < (double)(CameraConstants.LOW_RES_CAMERA_WIDTH)/2.0 - calibration + center) {
	    			Robot.driveSubsystem.tankdrive(-0.6, 0.6);
	    		} else {
	    			complete = true;
	    			Logger.err("So the camera ended here: " + goalX);
	    		}
    		}
    	}
    }

    protected boolean isFinished() {
    	if(isTimedOut()){ 
    		Robot.STOP_AUTO = "The time it took for the goal to be found was too high.";
    	}
		return isTimedOut() || complete;
    }

    protected void end() {
    	Robot.driveSubsystem.tankdrive(0, 0);
    	if(cam != null) {
    		cam.controllerable(true);
    		cam.switchCam(Camera.FRONT_CAM);
    	} else {
    		Logger.err("The camera isn't a thing, yo.");
    	}
    }

    protected void interrupted() {
    	Logger.err("You can't just interrupt the camera like that! ...  Ok, maybe you can.");
    	end();
    }
}
