
package org.usfirst.frc.team3695.robot.commands.auto;

import org.usfirst.frc.team3695.robot.commands.CommandRotateWithCam;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The main autonomous commands.
 */
public class AutonomousRotateAndScore extends CommandGroup 
{
    public AutonomousRotateAndScore(String direction)
    {
    	addSequential(new CommandRotateWithCam(CommandRotateWithCam.ALIGN_CENTER));
    	//addSequential(new CommandLaunchBall()); // TODO: DO SOMETHING WICKED :O
    }
}
