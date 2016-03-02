package org.usfirst.frc.team3695.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controller {
	private static final Joystick driver = new Joystick(Constants.DRIVE_JOYSTICK);
	private static final Joystick operator = new Joystick(Constants.OPERATOR_JOYSTICK);
	/*
	 * Controls for driver(XBOX):
	 * Left Trigger: Reverse
	 * Right Trigger: Forward(priority over reverse)
	 * Left Stick X-Axis: Left/Right(Left Stick)
	 * B Button: Boost
	 * Left Bumper: Target Left With Cam
	 * Right Bumper: Target Right With Cam
	 * 
	 */
	//Driver controls.
	/**
	 * DRIVER CONTROL<br>
	 * If the controller is an xBox controller:<br>
	 * left trigger: reverse<br>
	 * right trigger: forward (priority)<br>
	 * <br>
	 * If the controller is anything else:<br>
	 * xAxis: forward/backward.<br>
	 * @return A double from -1.0 to 1.0
	 */
	public static double DRIVE_Y_AXIS() {
		if(Constants.DRIVE_JOYSTICK_IS_XBOX) {
			double left = driver.getRawAxis(2);
			double right = driver.getRawAxis(3);
			if(right > 0.05) { //Returning right will ALWAYS take priority.
				return right * -1.0;
			} else {
				return left;
			}
		} else {
			return driver.getY();
		}
	}
	
	/**
	 * DRIVER CONTROL<br>
	 * If the controller is an xBox controller:<br>
	 * yAxis: left/right.<br>
	 * <br>
	 * If the controller is anything else:<br>
	 * yAxis: left/right.<br>
	 * @return A double from -1.0 to 1.0
	 */
	public static double DRIVE_X_AXIS() {
		if(Constants.DRIVE_JOYSTICK_IS_XBOX) {
			return driver.getX();
		} else {
			return driver.getX();
		}
	}
	
	/**
	 * DRIVER CONTROL<br>
	 * If the controller is an xBox controller:<br>
	 * B Button (2)<br>
	 * <br>
	 * If the controller is anything else:<br>
	 * Button 1<br>
	 * @return Button number.
	 */
	public static int DRIVE_BOOST() {
		if(Constants.DRIVE_JOYSTICK_IS_XBOX) {
			return 2;
		} else {
			return 1;
		}
	}
	
	/**
	 * DRIVER CONTROL<br>
	 * If the controller is an xBox controller:<br>
	 * L Button (5)<br>
	 * <br>
	 * If the controller is anything else:<br>
	 * Button 4<br>
	 * @return Button number.
	 */
	public static int DRIVE_TARGET_LEFT_WITH_CAM() {
		if(Constants.DRIVE_JOYSTICK_IS_XBOX) {
			return 5;
		} else {
			return 4;
		}
	}
	
	/**
	 * DRIVER CONTROL<br>
	 * If the controller is an xBox controller:<br>
	 * R Button (6)<br>
	 * <br>
	 * If the controller is anything else:<br>
	 * Button 5<br>
	 * @return Button number.
	 */
	public static int DRIVE_TARGET_RIGHT_WITH_CAM() {
		if(Constants.DRIVE_JOYSTICK_IS_XBOX) {
			return 6;
		} else {
			return 5;
		}
	}
	
	
	
	
	
	
	//Operator controls.
	/**
	 * OPERATOR CONTROL<br>
	 * If the controller is an xBox controller:<br>
	 * R Button (6)<br>
	 * <br>
	 * If the controller is anything else:<br>
	 * Button 5<br>
	 * @return Button number.
	 */
	public static int OP_SUCK_IN_BALL() {
		if(Constants.OPERATOR_JOYSTICK_IS_XBOX) {
			return 6;
		} else {
			return 5;
		}
	}
	
	/**
	 * OPERATOR CONTROL<br>
	 * If the controller is an xBox controller:<br>
	 * L Button (5)<br>
	 * <br>
	 * If the controller is anything else:<br>
	 * Button 4<br>
	 * @return Button number.
	 */
	public static int OP_THROW_OUT_BALL() {
		if(Constants.OPERATOR_JOYSTICK_IS_XBOX) {
			return 5;
		} else {
			return 4;
		}
	}
	
	/**
	 * OPERATOR CONTROL<br>
	 * If the controller is an xBox controller:<br>
	 * A Button (1)<br>
	 * <br>
	 * If the controller is anything else:<br>
	 * Button 1<br>
	 * @return Button number.
	 */
	public static int OP_FIRE_BALL() {
		if(Constants.OPERATOR_JOYSTICK_IS_XBOX) {
			return 1;
		} else {
			return 1;
		}
	}
	
	/**
	 * OPERATOR CONTROL<br>
	 * If the controller is an xBox controller:<br>
	 * Y Button (4)<br>
	 * <br>
	 * If the controller is anything else:<br>
	 * Button 0<br>
	 * @return Button number.
	 */
	public static int OP_ARM_UP() {
		if(Constants.OPERATOR_JOYSTICK_IS_XBOX) {
			return 4;
		} else {
			return 3;
		}
	}
	
	/**
	 * OPERATOR CONTROL<br>
	 * If the controller is an xBox controller:<br>
	 * B Button (2)<br>
	 * <br>
	 * If the controller is anything else:<br>
	 * Button 0<br>
	 * @return Button number.
	 */
	public static int OP_ARM_DOWN() {
		if(Constants.OPERATOR_JOYSTICK_IS_XBOX) {
			return 2;
		} else {
			return 2;
		}
	}
	
	
	
	
	
	
	//POV HAT
	/**
	 * OPERATOR CONTROL<br>
	 * The degree amount the POV must be to move the arm UP. See
	 * OP_ARM_UP().
	 */
	public static final int OP_ARM_UP_POV_DEG = 0;
	
	/**
	 * OPERATOR CONTROL<br>
	 * The degree amount the POV must be to move the arm DOWN. See
	 * OP_ARM_DOWN().
	 */
	public static final int OP_ARM_DOWN_POV_DEG = 180;
	
	
	
	
	
	
	//Raw joystick values.
	/**
	 * Use this to obtain the joystick for the driver.
	 * @return The driver joystick
	 */
	public static Joystick DRIVE_JOY() {
		return driver;
	}
	
	/**
	 * Use this to obtain the joystick for the operator.
	 * @return The operator joystick
	 */
	public static Joystick OP_JOY() {
		return operator;
	}
	
	
	
	
	
	//Copy-able method.
	/**
	 *  CONTROL<br>
	 * If the controller is an xBox controller:<br>
	 * Name (0)<br>
	 * <br>
	 * If the controller is anything else:<br>
	 * Button 0<br>
	 * @return
	 */
	public static int method_name() {
		if(Constants.DRIVE_JOYSTICK_IS_XBOX) {
			return 0;
		} else {
			return 0;
		}
	}
}
