
package org.usfirst.frc.team3695.robot;

import org.usfirst.frc.team3695.robot.commands.CommandRotateWithCam;
import org.usfirst.frc.team3695.robot.commands.auto.AutonomousForwardOnly;
import org.usfirst.frc.team3695.robot.commands.auto.AutonomousRotateAndScore;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemBall;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemNetworkTables;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemSensors;
import org.usfirst.frc.team3695.robot.subsystems.pneumatics.SubsystemArm;
import org.usfirst.frc.team3695.robot.subsystems.pneumatics.SubsystemBucket;

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
    public static SubsystemSensors sensorsSubsystem;
    public static SubsystemBall ballSubsystem;
    public static SubsystemBucket bucketSubsystem;
    public static SubsystemArm armSubsystem;
    public static OI oi;
    
    public static String STOP_AUTO = null;
    
    public void robotInit() {
        // Initialize all subsystems
    	driveSubsystem = new SubsystemDrive();
    	networkTables = new SubsystemNetworkTables();
    	sensorsSubsystem = new SubsystemSensors();
    	ballSubsystem = new SubsystemBall();
    	bucketSubsystem = new SubsystemBucket();
    	armSubsystem = new SubsystemArm();
        oi = new OI();
        
        //Set up autoChooser for robot
        autoChooser = new SendableChooser();
        autoChooser.addDefault("Forward ONLY", new AutonomousForwardOnly());
        autoChooser.addObject("Robot is LEFT of goal", new AutonomousRotateAndScore(CommandRotateWithCam.ROTATE_RIGHT_OVERALL));
        autoChooser.addObject("Robot is RIGHT of goal", new AutonomousRotateAndScore(CommandRotateWithCam.ROTATE_LEFT_OVERALL));
        
        //Set up rumbleChooser for robot
        rumbleChooser = new SendableChooser();
        rumbleChooser.addDefault("Rumble ON", true);
        rumbleChooser.addObject("Rumble OFF", false);
        
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
        autonomousCommand = (Command) autoChooser.getSelected(); //Get chosen auto.
    	autonomousCommand.start();
    }

    public void autonomousPeriodic() {
        log();
        oi.updatePov();
        Scheduler.getInstance().run();
    }

    //DISABLED ZONE (Make sure nothing is dangerous here!
    //When the robot is disabled, it should be DISABLED!
    public void disabledInit(){

    }
    
    public void disabledPeriodic() {
    	log();
    	oi.updatePov();
    }
    
    //TELEOP ZONE:
    public void teleopInit() {
    	if(autonomousCommand != null) {
    		autonomousCommand.cancel();
    	}
    }
    
    public void teleopPeriodic() {
        log();
    	oi.updatePov();
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
    	sensorsSubsystem.log();
    	bucketSubsystem.log();
    	
    	//Puts a reason for stopping auto on the dash.
    	SmartDashboard.putString("Auto Status: ", (STOP_AUTO == null ? "Everything is normal." : STOP_AUTO));
    }
    
    public static boolean isRumbleEnabled() {
    	return ((boolean) rumbleChooser.getSelected());
    }
}
