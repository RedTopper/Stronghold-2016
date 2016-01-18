
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
	//TODO: Uncomment for auto: 
	//Command autonomousCommand;
	
	//TODO: Uncomment this if teleop does not work: 
	//Command driveCommand;
    
    public static DriveSubsystem driveSubsystem;
    public static OI oi;
    
    public void robotInit() {
        // Initialize all subsystems
    	driveSubsystem = new DriveSubsystem();
        oi = new OI();
        
        // Instantiate the command used for the autonomous period
        //TODO: Uncomment for auto:
        //autonomousCommand = new Autonomous();
        
        //TODO: Uncomment this if teleop does not work:
        //driveCommand = new DriveCommand();

        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(driveSubsystem);
    }

    //AUTONOMOUS ZONE:
    public void autonomousInit() {
    	//TODO: Uncomment for auto:
    	//autonomousCommand.start(); // schedule the autonomous command
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        log();
    }

    //DISABLED ZONE (Make sure nothing is dangerous here!
    //When the robot is disabled, it should be DISABLED!):
    public void disabledInit(){

    }
    
    public void disabledPeriodic() {
    	log(); //May contain useful information about the status of the robot.
    }
    
    //TELEOP ZONE:
    public void teleopInit() {
    	//TODO: Uncomment for auto:
    	//autonomousCommand.cancel();
    	
    	//TODO: Uncomment this if teleop does not work:
    	//driveCommand.start();
    }
    
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        log();
    }
    
    //INFORMATION ZONE:
    private void log() {
    	driveSubsystem.log();
    }
}
