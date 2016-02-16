
package org.usfirst.frc.team3695.robot;

import org.usfirst.frc.team3695.robot.commands.AutonomousForwardOnly;
import org.usfirst.frc.team3695.robot.commands.AutonomousRotateAndScore;
import org.usfirst.frc.team3695.robot.commands.CommandRotateWithCam;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemNetworkTables;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	private static SendableChooser autoChooser;
	private static SendableChooser rumbleChooser;
	private Command autonomousCommand;
    
    public static SubsystemDrive driveSubsystem;
    public static SubsystemNetworkTables networkTables;
    public static OI oi;
    
    public static String STOP_AUTO = null;
    
    public void robotInit() {
        // Initialize all subsystems
    	driveSubsystem = new SubsystemDrive();
    	networkTables = new SubsystemNetworkTables();
        oi = new OI();
        
        //Set up autoChooser for robot
        autoChooser = new SendableChooser();
        autoChooser.addDefault("Forward ONLY", new AutonomousForwardOnly());
        autoChooser.addObject("Robot is LEFT of goal", new AutonomousRotateAndScore(CommandRotateWithCam.ROTATE_RIGHT));
        autoChooser.addObject("Robot is RIGHT of goal", new AutonomousRotateAndScore(CommandRotateWithCam.ROTATE_LEFT));
        autoChooser.addObject("Robot is CENTER of goal", new AutonomousRotateAndScore(CommandRotateWithCam.ALIGN_CENTER));
       
        //Set up rumbleChooser for robot
        rumbleChooser = new SendableChooser();
        rumbleChooser.addDefault("Rumble ON", Boolean.valueOf(true));
        rumbleChooser.addObject("Rumble OFF", Boolean.valueOf(false));
        
        //Put choosers on robot smart dash.
        SmartDashboard.putData("Auto Mode", autoChooser);
        SmartDashboard.putData("Rumble", rumbleChooser);

        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(Scheduler.getInstance()); //Shows everything the robot is running.
        SmartDashboard.putData(driveSubsystem); //Shows what command the driveSubsystem is running.
    }

    //AUTONOMOUS ZONE:
    public void autonomousInit() {
    	STOP_AUTO = null;
        autonomousCommand = (Command) autoChooser.getSelected(); // Instantiate the command used for the autonomous period
    	autonomousCommand.start(); // schedule the autonomous command
    }

    public void autonomousPeriodic() {
        log();
        Scheduler.getInstance().run();
    }

    //DISABLED ZONE (Make sure nothing is dangerous here!
    //When the robot is disabled, it should be DISABLED!
    public void disabledInit(){

    }
    
    public void disabledPeriodic() {
    	log();
    }
    
    //TELEOP ZONE:
    public void teleopInit() {
    	if(autonomousCommand != null) {
    		autonomousCommand.cancel();
    	}
    }
    
    public void teleopPeriodic() {
        log();
        Scheduler.getInstance().run();
    }
    
    //INFORMATION ZONE: add log functions here.
    /**
     * Updates information on the smart dash board. Called before the scheduler runs on
     * any given loop.
     */
    private void log() {
    	networkTables.updateInfo();
    	networkTables.log();
    	driveSubsystem.log();
    	
    	//Puts a reason for stopping auto on the dash.
    	SmartDashboard.putString("Auto Status: ", (STOP_AUTO == null ? "Everything is normal." : STOP_AUTO));
    }
    
    public static boolean isRumbleEnabled() {
    	return ((Boolean) rumbleChooser.getSelected());
    }
}
