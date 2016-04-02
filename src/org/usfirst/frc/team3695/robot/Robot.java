
package org.usfirst.frc.team3695.robot;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.usfirst.frc.team3695.robot.enumeration.objective.Defense;
import org.usfirst.frc.team3695.robot.enumeration.objective.RotateWithCam;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemBall;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemBling;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemCompressor;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemSensors;
import org.usfirst.frc.team3695.robot.subsystems.pneumatics.SubsystemArm;
import org.usfirst.frc.team3695.robot.subsystems.pneumatics.SubsystemBucket;
import org.usfirst.frc.team3695.robot.util.Logger;

import edu.wpi.first.wpilibj.IterativeRobot;
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
	//Generic variables
	private long lastTime = System.currentTimeMillis();
	private double ticksPerSecond = 0;
	
	//Choosers
	private static SendableChooser autoChooser;
	private static SendableChooser rumbleChooser;
	private static SendableChooser driveChooser;
	private static SendableChooser boostChooser;
	private static SendableChooser camChooser;
	
	//Auto
	private Autonomous autonomousCommand;
    
	//Static subsystems
    public static SubsystemDrive driveSubsystem;
    public static SubsystemSensors sensorsSubsystem;
    public static SubsystemBall ballSubsystem;
    public static SubsystemBucket bucketSubsystem;
    public static SubsystemArm armSubsystem;
    public static SubsystemCompressor compressorSubsystem;
    public static SubsystemBling blingSubsystem;
    public static OI oi;
    
    //Error message to notify auto to stop.
    public static String STOP_AUTO = null;
    public static boolean AUTOING = false;
    
    public void robotInit() {
    	//Print message
    	Logger.err("Starting the robot.");
    	
        // Initialize all subsystems
    	driveSubsystem = new SubsystemDrive();
    	sensorsSubsystem = new SubsystemSensors();
    	ballSubsystem = new SubsystemBall();
    	bucketSubsystem = new SubsystemBucket();
    	armSubsystem = new SubsystemArm();
    	compressorSubsystem = new SubsystemCompressor(); 
    	blingSubsystem = new SubsystemBling();
        oi = new OI();
        
        //Set up autoChooser for robot
        autoChooser = new SendableChooser();
        autoChooser.addDefault("Do Nothing", Defense.NOTHING);
        autoChooser.addObject("Low Bar", Defense.LOW_BAR);
        autoChooser.addObject("Ramparts", Defense.RAMPARTS);
        autoChooser.addObject("Rough Terrain", Defense.ROUGH_TERRAIN);
        autoChooser.addObject("Moat", Defense.MOAT);
        autoChooser.addObject("Rock Wall", Defense.ROCK_WALL);
        
        //set up cameraChooser for the robot.
        camChooser = new SendableChooser();
        camChooser.addDefault("Do not use camera", RotateWithCam.NOTHING);
        camChooser.addObject("Rotate robot LEFT", RotateWithCam.ROTATE_LEFT_OVERALL);
        camChooser.addObject("Rotate robot RIGHT", RotateWithCam.ROTATE_RIGHT_OVERALL);
        
        //Set up rumbleChooser for robot
        rumbleChooser = new SendableChooser();
        rumbleChooser.addDefault("Rumble ON", true);
        rumbleChooser.addObject("Rumble OFF", false);
        
        //Set up driveChooser for choice on drive style.
        driveChooser = new SendableChooser();
        driveChooser.addDefault("Yu Drive", true);
        driveChooser.addObject("Intuitive Drive", false); //FIXME: Broken. Function needs work.
        
        //Set up boostChooser for inverse boost
        boostChooser = new SendableChooser();
        boostChooser.addDefault("Boost Button", true);
        boostChooser.addObject("Slow Button", false);
        
        //Put choosers on robot smart dash.
        SmartDashboard.putData("Auto Mode", autoChooser);
        SmartDashboard.putData("Rumble Mode", rumbleChooser);
        SmartDashboard.putData("Drive Mode", driveChooser);
        SmartDashboard.putData("Boost Mode", boostChooser);
        SmartDashboard.putData("Cam Mode", camChooser);
        

        //Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(Scheduler.getInstance()); //Shows everything the robot is running. In theory.
        
        //Flash when Robot Turns Boots Up - Debug/status
        blingSubsystem.setFlashTime(5000);
    }

    //AUTONOMOUS ZONE:
    public void autonomousInit() {
    	blingSubsystem.setFlashTime(15000);
    	STOP_AUTO = null;
    	AUTOING = true;
        autonomousCommand = new Autonomous((Defense)(autoChooser.getSelected()), (RotateWithCam)(camChooser.getSelected()));
    	autonomousCommand.start();
    }

    public void autonomousPeriodic() {
        log();
        oi.updateJoyManual();
        Scheduler.getInstance().run();
    }

    //DISABLED ZONE:
    public void disabledInit(){
    	AUTOING = false;
    	if(autonomousCommand != null) {
    		autonomousCommand.cancel();
    		autonomousCommand = null;
    	}
    }
    
    public void disabledPeriodic() {
    	log();
    	oi.updateJoyManual();
    	Scheduler.getInstance().run();
    }
    
    //TELEOP ZONE:
    public void teleopInit() {
    	AUTOING = false;
    	if(autonomousCommand != null) {
    		autonomousCommand.cancel();
    		autonomousCommand = null;
    	}
    }
    
    public void teleopPeriodic() {
        log();
        oi.updateJoyManual();
        Scheduler.getInstance().run();
    }
    
    //INFORMATION ZONE: add log functions here.
    /**
     * Updates information on the smart dash board. Called before the scheduler runs on
     * any given loop.
     */
    private void log() {
    	double currentTime = System.currentTimeMillis();
    	ticksPerSecond = (1000.0 / (double)(currentTime - lastTime));
    	if(currentTime - lastTime > 200) {
    		Logger.err("Can't keep up! Did the system time change, or is the server overloaded? Running " + ((long)currentTime - (long)lastTime) + "ms behind");
    	}
    	lastTime = System.currentTimeMillis();
    	logUnsafe();
    	driveSubsystem.log();
    	sensorsSubsystem.log();
    	bucketSubsystem.log();
    	armSubsystem.log();
    	compressorSubsystem.log();
    	blingSubsystem.log();
    	
    	//Puts a reason for stopping auto on the dash.
    	SmartDashboard.putString("Auto Status: ", (STOP_AUTO == null ? "Everything is normal." : STOP_AUTO));
    }
    
    private void logUnsafe() {
		final Runtime r = Runtime.getRuntime();
		
		//big decimal for ticks per second
		BigDecimal tps = new BigDecimal(ticksPerSecond);
		
		//round big decimals
		tps = tps.setScale(2, RoundingMode.HALF_UP);
		
		//define MiB.
		final long MB = 1024 * 1024;
		SmartDashboard.putString("System Information:",
				"|Ticks Per Second: " + tps 
				+ "|Threads: " + Thread.activeCount()
				+ "|Free mem: " + (r.freeMemory() / MB) 
				+ "|Used mem: " + ((r.totalMemory() - r.freeMemory()) / MB) + "MiB" 
				+ "|Allocated mem: " + (r.totalMemory() / MB) + "MiB"
				+ "|Max mem: " + (r.maxMemory() / MB) + "MiB"
				+ "|Total Free mem: " + ((r.freeMemory() + (r.maxMemory() - r.totalMemory())) / MB) + "MiB\n");
		
    }
    
    public static boolean isRumbleEnabled() {
    	return ((boolean) rumbleChooser.getSelected());
    }
    
    public static boolean isYuEnabled() {
    	return ((boolean) driveChooser.getSelected());
    }
    
    public static boolean isBoostButtonBoost() {
    	return ((boolean) boostChooser.getSelected());
    }
}
