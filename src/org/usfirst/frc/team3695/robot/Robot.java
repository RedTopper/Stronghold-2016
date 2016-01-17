
package org.usfirst.frc.team3695.robot;

import org.usfirst.frc.team3695.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	//TODO: Uncomment for auto: Command autonomousCommand;
    
    public static DriveSubsystem drive;
    public static OI oi;
    
    public void robotInit() {
        // Initialize all subsystems
        drive = new DriveSubsystem();
        oi = new OI();
        
        // instantiate the command used for the autonomous period
        //TODO: Uncomment for auto: autonomousCommand = new Autonomous();

        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(drive);
    }

    public void autonomousInit() {
    	//TODO: Uncomment for auto: autonomousCommand.start(); // schedule the autonomous command
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        log();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }
    
    public void disabledPeriodic() {
    	
    }
    
    public void teleopInit() {
    	//TODO: Uncomment for auto: autonomousCommand.cancel();
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        log();
    }
    
	/**
	 * The log method puts interesting information to the SmartDashboard.
	 */
    private void log() {
        drive.log();
    }
}
