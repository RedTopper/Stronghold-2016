package org.usfirst.frc.team3695.robot;

import org.usfirst.frc.team3695.robot.commands.ExampleCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private Joystick driveStick = new Joystick(Constants.DRIVE_JOYSTICK);
	private Joystick operatorStick = new Joystick(Constants.OPERATOR_JOYSTICK);
	
	public OI() {
		//SmartDash
		SmartDashboard.putNumber("Robot Feedback: ", 0);
		
		//Buttons
		Button enableHighBeams = new JoystickButton(driveStick, Constants.ENABLE_HIGH_BEAMS);
		enableHighBeams.whenPressed(new ExampleCommand());
	}
	
	public Joystick getDriveStick() {
		return driveStick;
	}
	
	public Joystick getOperatorStick() {
		return operatorStick;
	}
}

