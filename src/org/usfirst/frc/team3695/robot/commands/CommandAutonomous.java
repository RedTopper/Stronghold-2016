
package org.usfirst.frc.team3695.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The main autonomous commands.
 */
public class CommandAutonomous extends CommandGroup {
    public CommandAutonomous() {
    	//The following is example code used for different commands to be executed in order.
    	//Autonomous.start() should be called in the Robot class when it is initialized.
    	
    	
    	//addSequential(new PrepareToPickup());
        //addSequential(new Pickup());
        //addSequential(new SetDistanceToBox(0.10));
        // addSequential(new DriveStraight(4)); // Use Encoders if ultrasonic is broken
        //addSequential(new Place());
        //addSequential(new SetDistanceToBox(0.60));
        // addSequential(new DriveStraight(-2)); // Use Encoders if ultrasonic is broken
        //addParallel(new SetWristSetpoint(-45));
        //addSequential(new CloseClaw());
    }
}
