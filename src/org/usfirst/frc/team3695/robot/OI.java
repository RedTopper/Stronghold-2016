package org.usfirst.frc.team3695.robot;

import org.usfirst.frc.team3695.robot.commands.CommandCompressorToggle;
import org.usfirst.frc.team3695.robot.commands.CommandGetBall;
import org.usfirst.frc.team3695.robot.commands.CommandMoveArm;
import org.usfirst.frc.team3695.robot.commands.CommandMoveArmRaw;
import org.usfirst.frc.team3695.robot.commands.CommandMoveBucket;
import org.usfirst.frc.team3695.robot.commands.CommandRotateWithCam;
import org.usfirst.frc.team3695.robot.commands.CommandStartGRIP;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private CommandMoveBucket moveBucketUp = new CommandMoveBucket(CommandMoveBucket.MOVE_UP);
	private CommandMoveBucket moveBucketDown = new CommandMoveBucket(CommandMoveBucket.MOVE_DOWN);
	
	private boolean povUpStateNotPressed = true,
					povDownStateNotPressed = true;
	
	public OI() {
		//SmartDash
		SmartDashboard.putData("Use camera to rotate RIGHT", new CommandRotateWithCam(CommandRotateWithCam.ROTATE_RIGHT_OVERALL));
		SmartDashboard.putData("Use camera to rotate LEFT", new CommandRotateWithCam(CommandRotateWithCam.ROTATE_LEFT_OVERALL));
		
		SmartDashboard.putData("Lock latch", new CommandMoveArmRaw(CommandMoveArmRaw.LOCK_LATCH));
		SmartDashboard.putData("Unlock latch", new CommandMoveArmRaw(CommandMoveArmRaw.UNLOCK_LATCH));
		SmartDashboard.putData("Move arm piston up (arm down)", new CommandMoveArmRaw(CommandMoveArmRaw.PISTON_UP));
		SmartDashboard.putData("Move arm piston down (arm up)", new CommandMoveArmRaw(CommandMoveArmRaw.PISTON_DOWN));

		SmartDashboard.putData("Fire", new CommandMoveArm(CommandMoveArm.FIRE));
		SmartDashboard.putData("Reset", new CommandMoveArm(CommandMoveArm.RESET));
		
		SmartDashboard.putData("Start GRIP", new CommandStartGRIP());
		
		//Compressor Toggle
		SmartDashboard.putData("Comp toggle", new CommandCompressorToggle());
		
		
		//Buttons
		Button getBall = new JoystickButton(Controller.OP_JOY(), Controller.OP_SUCK_IN_BALL());
		getBall.whileHeld(new CommandGetBall(CommandGetBall.SUCK_IN_BALL));
		
		Button removeBall = new JoystickButton(Controller.OP_JOY(), Controller.OP_THROW_OUT_BALL());
		removeBall.whileHeld(new CommandGetBall(CommandGetBall.THROW_OUT_BALL));
		
		//Buttons, but also POV hat. See updatePov()
		Button armUp = new JoystickButton(Controller.OP_JOY(), Controller.OP_ARM_DOWN());
		armUp.whenPressed(moveBucketUp);
		
		Button armDown = new JoystickButton(Controller.OP_JOY(), Controller.OP_ARM_DOWN());
		armDown.whenPressed(moveBucketDown);
	}
	
	/**
	 * Updates the POV hat on a controller.
	 */
	public void updatePov() {
		if(povDownStateNotPressed && Controller.OP_JOY().getPOV(0) == Controller.OP_ARM_UP_POV_DEG) {
			moveBucketUp.start();
			povDownStateNotPressed = false;
		}
		if(povUpStateNotPressed && Controller.OP_JOY().getPOV(0) == Controller.OP_ARM_DOWN_POV_DEG) {
			moveBucketDown.start();
			povUpStateNotPressed = false;
		}
		if(Controller.OP_JOY().getPOV(0) == -1) {
			povDownStateNotPressed = true;
			povUpStateNotPressed = true;
		}
	}
}

