package org.usfirst.frc.team3695.robot;

import org.usfirst.frc.team3695.robot.commands.CommandBall;
import org.usfirst.frc.team3695.robot.commands.CommandDriveWithCam;
import org.usfirst.frc.team3695.robot.commands.CommandRotateWithCam;
import org.usfirst.frc.team3695.robot.commands.pneumatics.CommandCompressorToggle;
import org.usfirst.frc.team3695.robot.commands.pneumatics.CommandMoveArm;
import org.usfirst.frc.team3695.robot.commands.pneumatics.CommandMoveArmRaw;
import org.usfirst.frc.team3695.robot.commands.pneumatics.CommandMoveBucket;
import org.usfirst.frc.team3695.robot.enumeration.Cam;
import org.usfirst.frc.team3695.robot.enumeration.objective.Ball;
import org.usfirst.frc.team3695.robot.enumeration.objective.MoveArm;
import org.usfirst.frc.team3695.robot.enumeration.objective.MoveArmRaw;
import org.usfirst.frc.team3695.robot.enumeration.objective.MoveBucket;
import org.usfirst.frc.team3695.robot.enumeration.objective.RotateWithCam;
import org.usfirst.frc.team3695.robot.vision.Camera;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private CommandMoveBucket moveBucketUp = new CommandMoveBucket(MoveBucket.MOVE_UP);
	private CommandMoveBucket moveBucketDown = new CommandMoveBucket(MoveBucket.MOVE_DOWN);
	
	private CommandMoveArmRaw unlockLatch = new CommandMoveArmRaw(MoveArmRaw.UNLOCK_LATCH);
	private CommandMoveArmRaw lockLatch = new CommandMoveArmRaw(MoveArmRaw.LOCK_LATCH);
	
	private boolean povUpStateNotPressed = true,
					povDownStateNotPressed = true,
					triggerUnlockLatchStateNotPressed = true,
					triggerLockLatchStateNotPressed = true;
	
	/**
	 * These booleans provide the manual update buttons method with ways to 
	 * keep track of when the buttons need to update. This means that when
	 * a button is pressed, code for that operation will run once. When a button
	 * is released, code will only run once for that too.
	 */
	private boolean driveRearCamNeedsUpdate = false,
					driveFrontCamProcNeedsUpdate = false;
	
	private static Camera cam = Camera.getInstance();
	
	public OI() {
		//SmartDash
		SmartDashboard.putData("Use camera to rotate RIGHT", new CommandRotateWithCam(RotateWithCam.ROTATE_RIGHT_OVERALL));
		SmartDashboard.putData("Use camera to rotate LEFT", new CommandRotateWithCam(RotateWithCam.ROTATE_LEFT_OVERALL));
		SmartDashboard.putData("Use camera to drive", new CommandDriveWithCam());
		
		//Compressor Toggle
		SmartDashboard.putData("Disable Compressor", new CommandCompressorToggle());
		
		//Buttons for OP
		Button getBall = new JoystickButton(Controller.OP_JOY(), Controller.OP_GRAB_BALL);
		getBall.whileHeld(new CommandBall(Ball.SUCK_IN_BALL));
		
		Button removeBall = new JoystickButton(Controller.OP_JOY(), Controller.OP_RELEASE_BALL);
		removeBall.whileHeld(new CommandBall(Ball.THROW_OUT_BALL));
		
		Button fire = new JoystickButton(Controller.OP_JOY(), Controller.OP_FIRE_ARM);
		fire.whenPressed(new CommandMoveArm(MoveArm.FIRE));
		
		Button reset = new JoystickButton(Controller.OP_JOY(), Controller.OP_RESET_ARM);
		reset.whenPressed(new CommandMoveArm(MoveArm.RESET));
	}
	
	/**
	 * Updates the POV hat on a controller.
	 */
	private void updatePov() {
		if(povDownStateNotPressed && Controller.OP_JOY().getPOV(0) == Controller.OP_BUCKET_UP_POV_DEG) {
			moveBucketUp.start();
			povDownStateNotPressed = false;
		}
		if(povUpStateNotPressed && Controller.OP_JOY().getPOV(0) == Controller.OP_BUCKET_DOWN_POV_DEG) {
			moveBucketDown.start();
			povUpStateNotPressed = false;
		}
		if(Controller.OP_JOY().getPOV(0) == -1) {
			povDownStateNotPressed = true;
			povUpStateNotPressed = true;
		}
	}
	
	/**
	 * This method gets the axis for triggers and manually
	 * tries to convert them to buttons.
	 */
	private void updateTriggersAsButtons() {
		double lockLatch = Controller.OP_JOY().getRawAxis(2);
		double unlockLatch = Controller.OP_JOY().getRawAxis(3);
		
		if(triggerLockLatchStateNotPressed && lockLatch > 0.9) {
			this.lockLatch.start();
			triggerLockLatchStateNotPressed = false;
		}
		if(triggerUnlockLatchStateNotPressed && unlockLatch > 0.9) {
			this.unlockLatch.start();
			triggerUnlockLatchStateNotPressed = false;
		}
		if(lockLatch < 0.1) {
			triggerLockLatchStateNotPressed = true;
		}
		if(unlockLatch < 0.1) {
			triggerUnlockLatchStateNotPressed = true;
		}
	}
	
	/**
	 * This is the method to manually do stuff with buttons.
	 * It runs outside of the scope of the scheduler and, as
	 * such, is unsafe.
	 */
	private void updateButtonsManual() {
		//Code for viewing the rear view cam
		if(cam.isControllable() && !driveRearCamNeedsUpdate && Controller.DRIVE_JOY().getRawButton(Controller.DRIVE_REAR_CAM)) {
			if(cam != null) cam.switchCam(Cam.REAR_CAM);
			driveRearCamNeedsUpdate = true;
		}
		if(cam.isControllable() && driveRearCamNeedsUpdate && !Controller.DRIVE_JOY().getRawButton(Controller.DRIVE_REAR_CAM)){
			if(cam != null) cam.switchCam(Cam.FRONT_CAM);
			driveRearCamNeedsUpdate = false;
		}
		
		//Code for viewing the processed vision camera.
		if(cam.isControllable() && !driveFrontCamProcNeedsUpdate && Controller.DRIVE_JOY().getRawButton(Controller.DRIVE_PROCESSED_CAM)) {
			if(cam != null) cam.switchCam(Cam.FRONT_PROCESSED);
			driveFrontCamProcNeedsUpdate = true;
		} 
		if(cam.isControllable() && driveFrontCamProcNeedsUpdate && !Controller.DRIVE_JOY().getRawButton(Controller.DRIVE_PROCESSED_CAM)){
			if(cam != null) cam.switchCam(Cam.FRONT_CAM);
			driveFrontCamProcNeedsUpdate = false;
		}
	}

	/**
	 * This method allows the programmer to manually override specific
	 * functions of the controller. Generally, this method should be unused
	 * because the command scheduler should override the controls that
	 * are needed for the controller to function. If, however, it is 
	 * necessary for one to manually accept input for an axis. d-pad, or
	 * button, that should be done here.
	 */
	public void updateJoyManual() {
		updatePov();
		updateTriggersAsButtons();
		updateButtonsManual();
	}
}

