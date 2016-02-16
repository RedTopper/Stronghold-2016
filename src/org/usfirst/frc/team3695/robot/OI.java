package org.usfirst.frc.team3695.robot;

import org.usfirst.frc.team3695.robot.commands.CommandGetBall;
import org.usfirst.frc.team3695.robot.commands.CommandPhotoelectric;
import org.usfirst.frc.team3695.robot.commands.CommandRotateWithCam;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public OI() {
		//SmartDash
		SmartDashboard.putData("Use camera to rotate RIGHT", new CommandRotateWithCam(CommandRotateWithCam.ROTATE_RIGHT));
		SmartDashboard.putData("Use camera to rotate LEFT", new CommandRotateWithCam(CommandRotateWithCam.ROTATE_LEFT));
		SmartDashboard.putData("Use camera to align CENTER", new CommandRotateWithCam(CommandRotateWithCam.ALIGN_CENTER));
		SmartDashboard.putData("Test Analog", new CommandPhotoelectric());
		
		Button getBall = new JoystickButton(Controller.OP_JOY(), Controller.OP_SUCK_IN_BALL());
		getBall.whileHeld(new CommandGetBall(CommandGetBall.SUCK_IN_BALL));
		
		Button removeBall = new JoystickButton(Controller.OP_JOY(), Controller.OP_THROW_OUT_BALL());
		removeBall.whileHeld(new CommandGetBall(CommandGetBall.THROW_OUT_BALL));
		//[Deprecated] (Left as Example)
		//6 Wheel Drive Button ('Obstacle Button') 
		//Button doObstacleMagically = new JoystickButton(driveStick, Constants.ENABLE_6WHEEL_DRIVE);
		//doObstacleMagically.whileActive(new CommandSecondaryDrive());
	}
}

