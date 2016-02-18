package org.usfirst.frc.team3695.robot;

import org.usfirst.frc.team3695.robot.commands.CommandGetBall;
import org.usfirst.frc.team3695.robot.commands.CommandMoveArm;
import org.usfirst.frc.team3695.robot.commands.CommandPhotoelectric;
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
	
	private CommandMoveArm moveArmUp = new CommandMoveArm(CommandMoveArm.MOVE_UP);
	private CommandMoveArm moveArmDown = new CommandMoveArm(CommandMoveArm.MOVE_DOWN);
	
	private boolean povUpStateNotPressed = true,
					povDownStateNotPressed = true;
	
	public OI() {
		//SmartDash
		SmartDashboard.putData("Use camera to rotate RIGHT", new CommandRotateWithCam(CommandRotateWithCam.ROTATE_RIGHT_OVERALL));
		SmartDashboard.putData("Use camera to rotate LEFT", new CommandRotateWithCam(CommandRotateWithCam.ROTATE_LEFT_OVERALL));
		SmartDashboard.putData("Test Analog", new CommandPhotoelectric());
		SmartDashboard.putData("Start GRIP", new CommandStartGRIP());
		
		//Buttons
		Button getBall = new JoystickButton(Controller.OP_JOY(), Controller.OP_SUCK_IN_BALL());
		getBall.whileHeld(new CommandGetBall(CommandGetBall.SUCK_IN_BALL));
		
		Button removeBall = new JoystickButton(Controller.OP_JOY(), Controller.OP_THROW_OUT_BALL());
		removeBall.whileHeld(new CommandGetBall(CommandGetBall.THROW_OUT_BALL));
		
		//Buttons, but also POV hat. See updatePov()
		Button armUp = new JoystickButton(Controller.OP_JOY(), Controller.OP_ARM_DOWN());
		armUp.whenPressed(moveArmUp);
		
		Button armDown = new JoystickButton(Controller.OP_JOY(), Controller.OP_ARM_DOWN());
		armDown.whenPressed(moveArmDown);
	}
	
	/**
	 * Updates the POV hat on a controller.
	 */
	public void updatePov() {
		if(povDownStateNotPressed && Controller.OP_JOY().getPOV(0) == Controller.OP_ARM_UP_POV_DEG) {
			moveArmUp.start();
			povDownStateNotPressed = false;
		}
		if(povUpStateNotPressed && Controller.OP_JOY().getPOV(0) == Controller.OP_ARM_DOWN_POV_DEG) {
			moveArmDown.start();
			povUpStateNotPressed = false;
		}
		if(Controller.OP_JOY().getPOV(0) == -1) {
			povDownStateNotPressed = true;
			povUpStateNotPressed = true;
		}
	}
}

