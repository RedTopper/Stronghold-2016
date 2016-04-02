package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.enumeration.Cam;
import org.usfirst.frc.team3695.robot.enumeration.objective.RotateWithCam;
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
	private RotateWithCam objective;
	private boolean complete;
	private int stage = 0;
	private int calibration = Util.setAndGetNumber("ROT", "Calibration Value", 10);
	private int center = Util.setAndGetNumber("CAM", "Cross X", 320) / 2;
	private int errors = 0;
	
	private long lastTime = 0;
	private Camera cam = Camera.getInstance();
	
	/**
	 * This will tell the robot witch way to move to face the goal the quickest.
	 * @param direction use RotateWithCam.ROTATE_LEFT_OVERALL or 
	 * RotateRightWithCam.ROTATE_RIGHT_OVERALL to tell the robot to 
	 * move in a direction.
	 */
    public CommandRotateWithCam(RotateWithCam objective) {
    	requires(Robot.driveSubsystem);
        this.objective = objective;
        setTimeout(CameraConstants.MAX_ROTATE_TIME);
    }

    protected void initialize() {
    	complete = false;
    	if(Robot.AUTOING && Robot.STOP_AUTO != null) {
    		complete = true;
    		return;
    	}
    	calibration = Util.setAndGetNumber("ROT", "Calibration Value", 10);
    	center = Util.setAndGetNumber("CAM", "Cross X", 320) / 2; //Image rec half normal size.
    	stage = 0;
    	if(cam != null) {
    		cam.controllerable(false);
    		cam.switchCam(Cam.FRONT_PROCESSED);
    	} else {
    		Logger.err("The camera isn't a thing, yo.");
    	}
    	errors = 0;
    }

    protected void execute(){
    	if(complete) {
    		return;
    	}
		if(!cam.isProccessingCamera()) {
			Robot.driveSubsystem.tankdrive(0, 0);
			lastTime = System.currentTimeMillis(); 			//Wait for the camera to switch over.
			return;
		}
		if(lastTime + 700 > System.currentTimeMillis()) { 	//Wait for the camera to actually update the images.
			Robot.driveSubsystem.tankdrive(0, 0);
			return;
		}
    	double goalX = cam.getGoalXY()[0];
    	
    	switch(objective) {
    	case ROTATE_RIGHT_OVERALL:
    		if(stage == 0) {
    			Robot.driveSubsystem.tankdrive(0.73, -0.73);
    			if(goalX != -1.0 && goalX > 0.0) {
    				stage++;
    			}
    		}
    		break;
    	case ROTATE_LEFT_OVERALL:
    		if(stage == 0) {
    			Robot.driveSubsystem.tankdrive(-0.73, 0.73);
    			if(goalX != -1.0 && goalX > 0.0) {
    				stage++;
    			}
    		}
    		break;
    	case NOTHING:
    		complete = true;
    		break;
    	}
    	
    	if(stage == 1) {
    		if(goalX != -1.0) {
	    		if(goalX > (double)(center + calibration)) {
	    			Robot.driveSubsystem.tankdrive(0.73, -0.73);
	    		} else if(goalX < (double)(center - calibration)) {
	    			Robot.driveSubsystem.tankdrive(-0.73, 0.73);
	    		} else {
	    			complete = true;
	    			Logger.out("So the camera ended here: " + goalX);
	    		}
    		} else {
    			if(errors++ > CameraConstants.MAX_CAMERA_MISSES) {
    				Logger.err("Quit because I could not find a goal! Did the image get distorted?");
    				Robot.STOP_AUTO = "Can't find goal to rotate to!";
    				complete = true;
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
    		cam.switchCam(Cam.FRONT_CAM);
    	} else {
    		Logger.err("The camera isn't a thing, yo.");
    	}
    }

    protected void interrupted() {
    	Logger.err("You can't just interrupt the camera like that! ...  Ok, maybe you can.");
    	end();
    }
}
