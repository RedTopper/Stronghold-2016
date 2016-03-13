
package org.usfirst.frc.team3695.robot;

import org.usfirst.frc.team3695.robot.commands.CommandRotateWithCam;
import org.usfirst.frc.team3695.robot.commands.auto.AutonomousForwardOnly;
import org.usfirst.frc.team3695.robot.commands.auto.AutonomousRotateAndScore;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemBall;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemCompressor;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemNetworkTables;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemSensors;
import org.usfirst.frc.team3695.robot.subsystems.pneumatics.SubsystemArm;
import org.usfirst.frc.team3695.robot.subsystems.pneumatics.SubsystemBucket;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
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
	private Camera cam;
	private int lastSelectedCamera = Camera.FRONT_CAM;
	
	private static SendableChooser autoChooser;
	private static SendableChooser rumbleChooser;
	private static SendableChooser driveChooser;
	private static SendableChooser boostChooser;
	private static SendableChooser cameraChooser;
	
	private Command autonomousCommand;
    
    public static SubsystemDrive driveSubsystem;
    public static SubsystemNetworkTables networkTables;
    public static SubsystemSensors sensorsSubsystem;
    public static SubsystemBall ballSubsystem;
    public static SubsystemBucket bucketSubsystem;
    public static SubsystemArm armSubsystem;
    public static SubsystemCompressor compressorSubsystem;
    public static OI oi;
    
    public static String STOP_AUTO = null;
    
    public void robotInit() {
    	//Hopefully start camera
    	cam = new Camera();
    	cam.start();
    	
        // Initialize all subsystems
    	driveSubsystem = new SubsystemDrive();
    	networkTables = new SubsystemNetworkTables();
    	sensorsSubsystem = new SubsystemSensors();
    	ballSubsystem = new SubsystemBall();
    	bucketSubsystem = new SubsystemBucket();
    	armSubsystem = new SubsystemArm();
    	compressorSubsystem = new SubsystemCompressor(); 
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
        
        //Set up driveChooser for choice on drive style.
        driveChooser = new SendableChooser();
        driveChooser.addDefault("Yu Drive", true);
        driveChooser.addObject("Intuitive Drive", false); //FIXME: Broken. Function needs work.
        
        //Set up boostChooser for inverse boost
        boostChooser = new SendableChooser();
        boostChooser.addDefault("Boost Button", true);
        boostChooser.addObject("Slow Button", false);
        
        //Set up cameraChooser for selecting witch camera to view 
        cameraChooser = new SendableChooser();
        cameraChooser.addDefault("Front Camera", Camera.FRONT_CAM);
        cameraChooser.addObject("Front Camera (processed)", Camera.FRONT_PROCCESSED);
        cameraChooser.addObject("Rear Camera", Camera.REAR_CAM);
        cameraChooser.addObject("No Camera", Camera.NO_CAM);
        
        //Put choosers on robot smart dash.
        SmartDashboard.putData("Auto Mode", autoChooser);
        SmartDashboard.putData("Rumble Mode", rumbleChooser);
        SmartDashboard.putData("Drive Mode", driveChooser);
        SmartDashboard.putData("Boost Mode", boostChooser);
        SmartDashboard.putData("Camera Mode", cameraChooser);

        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(Scheduler.getInstance()); //Shows everything the robot is running.
        //SmartDashboard.putData(driveSubsystem); //Shows what command the driveSubsystem is running.
        //SmartDashboard.putData(armSubsystem); //Shows what command the armSubsystem is running.
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
        oi.updateTriggersAsButtons();
        Scheduler.getInstance().run();
        try {Thread.sleep(5);} catch (InterruptedException e){}
    }

    //DISABLED ZONE:
    public void disabledInit(){
    	if(autonomousCommand != null) {
    		autonomousCommand.cancel();
    	}
    }
    
    public void disabledPeriodic() {
    	log();
    	oi.updatePov();
    	oi.updateTriggersAsButtons();
    	Scheduler.getInstance().run();
    	try {Thread.sleep(5);} catch (InterruptedException e){}
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
    	oi.updateTriggersAsButtons();
        Scheduler.getInstance().run();
        try {Thread.sleep(5);} catch (InterruptedException e){}
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
    	armSubsystem.log();
    	compressorSubsystem.log();
    	
    	//Puts a reason for stopping auto on the dash.
    	SmartDashboard.putString("Auto Status: ", (STOP_AUTO == null ? "Everything is normal." : STOP_AUTO));
    	
    	//update the camera if the user selects a different camera to show
    	int currentCamera = (int) cameraChooser.getSelected();
    	if(lastSelectedCamera != currentCamera) {
    		cam.viewCam(currentCamera);
        	lastSelectedCamera = currentCamera;
    	}
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
