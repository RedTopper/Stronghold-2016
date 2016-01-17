
package org.usfirst.frc.team3695.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import RecycleBotPart.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
	private ArmPart arm;
	private DrivePart drive;
	private InterfacePart driveStation;
	private LiftPart lift;
	private SensorPart sensor;
	
    public void robotInit() {
    	
    	sensor = new SensorPart(this);
    	arm = new ArmPart(this);
    	drive = new DrivePart(this);
    	driveStation = new InterfacePart(this);
    	lift = new LiftPart(this);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    	arm.updateAuto();
    	drive.updateAuto();
    	driveStation.updateAuto();
    	lift.updateAuto();
    	sensor.updateAuto();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
    	arm.updateTeleop();
    	drive.updateTeleop();
    	driveStation.updateTeleop();
    	lift.updateTeleop();
    	sensor.updateTeleop();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    	arm.updateTest();
    	drive.updateTest();
    	driveStation.updateTest();
    	lift.updateTest();
    	sensor.updateTest();
    }

	/**
	 * This function is called at the start of teleoperated mode
	 */
	public void teleopInit(){

		driveStation.teleopInit();
		drive.teleopInit();
	}
    
    public ArmPart getArm()
    {return arm;}
    
    public DrivePart getDrive()
    {return drive;}
    
    public InterfacePart getStation()
    {return driveStation;}
    
    public LiftPart getLift()
    {return lift;}
    
    public SensorPart getSensor()
    {return sensor;}
    
}
