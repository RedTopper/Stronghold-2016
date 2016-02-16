package org.usfirst.frc.team3695.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controller {
	private static final Joystick driver = new Joystick(Constants.DRIVE_JOYSTICK);
	private static final Joystick operator = new Joystick(Constants.OPERATOR_JOYSTICK);

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
		if(driver.getIsXbox()) {
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
		if(driver.getIsXbox()) {
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
		if(driver.getIsXbox()) {
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
	 * @return
	 */
	public static int DRIVE_TARGET_LEFT_WITH_CAM() {
		if(driver.getIsXbox()) {
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
	 * @return
	 */
	public static int DRIVE_TARGET_RIGHT_WITH_CAM() {
		if(driver.getIsXbox()) {
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
		if(operator.getIsXbox()) {
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
		if(operator.getIsXbox()) {
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
	 * @return
	 */
	public static int OP_FIRE_BALL() {
		if(driver.getIsXbox()) {
			return 1;
		} else {
			return 1;
		}
	}
	
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
		if(driver.getIsXbox()) {
			return 0;
		} else {
			return 0;
		}
	}
}
