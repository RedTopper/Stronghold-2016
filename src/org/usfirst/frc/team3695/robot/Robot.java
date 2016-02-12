
package org.usfirst.frc.team3695.robot;

import org.usfirst.frc.team3695.robot.commands.CommandAutonomous;
import org.usfirst.frc.team3695.robot.subsystems.SubsystemDrive;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Command autonomousCommand;
	ITable table;
    
    public static SubsystemDrive driveSubsystem;
    public static OI oi;
    
    public void robotInit() {
        // Initialize all subsystems
    	driveSubsystem = new SubsystemDrive();
        oi = new OI();
        
        // Instantiate the command used for the autonomous period
        autonomousCommand = new CommandAutonomous();

        // Show what command your subsystem is running on the SmartDashboard
        //SmartDashboard.putData(driveSubsystem);
        
        //Network Tables (Scary!)
        table = NetworkTable.getTable("GRIP").getSubTable("raw");
    }

    //AUTONOMOUS ZONE:
    public void autonomousInit() {
    	autonomousCommand.start(); // schedule the autonomous command
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
    	autonomousCommand.cancel();
    }
    
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        log();
    }
    
    //INFORMATION ZONE:
    private void log() {
    	//add log functions here.
    	double[] centerY = table.getNumberArray("centerY", new double[] {-1.0});
        SmartDashboard.putNumber("Goal Y position:", (centerY.length > 0 ? centerY[0] : -1.0));
    	driveSubsystem.log();
    }
}
