
package org.usfirst.frc.team3695.robot;

import org.usfirst.frc.team3695.robot.commands.AutonomousForward;
import org.usfirst.frc.team3695.robot.commands.AutonomousLeft;
import org.usfirst.frc.team3695.robot.commands.AutonomousRight;
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
	Command autonomousCommand;
    
    public static SubsystemDrive driveSubsystem;
    public static SubsystemNetworkTables networkTables;
    public static OI oi;
    
    public void robotInit() {
        // Initialize all subsystems
    	driveSubsystem = new SubsystemDrive();
    	networkTables = new SubsystemNetworkTables();
        oi = new OI();
        
        //Set up autoChooser for robot
        autoChooser = new SendableChooser();
        autoChooser.addDefault("Forward ONLY", new AutonomousForward());
        autoChooser.addObject("Robot is LEFT of goal", new AutonomousLeft());
        autoChooser.addObject("Robot is RIGHT of goal", new AutonomousRight());
        autoChooser.addObject("Robot is CENTER of goal", new AutonomousLeft());
       
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
    	autonomousCommand.cancel();
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
    }
    
    public static boolean isRumbleEnabled() {
    	return ((Boolean) rumbleChooser.getSelected());
    }
}
