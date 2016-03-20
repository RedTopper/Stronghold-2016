package org.usfirst.frc.team3695.robot.commands;

import org.usfirst.frc.team3695.robot.Robot;
import org.usfirst.frc.team3695.robot.util.Logger;
import org.usfirst.frc.team3695.robot.util.Util;
import org.usfirst.frc.team3695.robot.vision.Camera;
import org.usfirst.frc.team3695.robot.vision.CameraConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will move the robot to better align itself with a goal (hopefully) with
 * a camera.
 */
public class CommandDriveWithCam extends Command {
	private boolean complete;
	private int calibration = Util.setAndGetNumber("FWD", "Calibration Value", 10);
	private int center = Util.setAndGetNumber("FWD", "Center Offset Value", 100);
	
	private long lastTime = 0;
	private Camera cam = Camera.getInstance();
	
	/**
	 * This will tell the robot witch way to move to face the goal the quickest.
	 * @param direction use CommandRotateWithCam.ROTATE_LEFT_OVERALL or 
	 * CommandRotateRightWithCam.ROTATE_RIGHT_OVERALL to tell the robot to 
	 * move in a direction.
	 */
    public CommandDriveWithCam() {
    	requires(Robot.driveSubsystem);
        setTimeout(CameraConstants.MAX_FORWARD_TIME);
    }

    protected void initialize() {
    	calibration = Util.setAndGetNumber("FWD", "Calibration Value", 10);
    	center = Util.setAndGetNumber("FWD", "Center Offset Value", 0);
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
			Robot.driveSubsystem.tankdrive(0, 0);
			lastTime = System.currentTimeMillis(); 			//Wait for the camera to switch over.
			return;
		}
		if(lastTime + 700 > System.currentTimeMillis()) { 	//Wait for the camera to actually update the images.
			Robot.driveSubsystem.tankdrive(0, 0);
			return;
		}
    	double goalY = cam.getGoalXY()[1];
		if(goalY != -1.0) {
    		if(goalY > (double)(CameraConstants.LOW_RES_CAMERA_WIDTH)/2.0 - calibration - center) {
    			Robot.driveSubsystem.tankdrive(0.5, 0.5);
    		} else if(goalY < (double)(CameraConstants.LOW_RES_CAMERA_WIDTH)/2.0 + calibration - center) {
    			Robot.driveSubsystem.tankdrive(-0.5, -0.5);
    		} else {
    			complete = true;
    			Logger.out("So the camera ended here: " + goalY);
    		}
		} else {
			Logger.err("Quit because I could not find a goal! Has the robot been rotated first?");
			Robot.STOP_AUTO = "Can't find goal to drive!";
			complete = true;
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
