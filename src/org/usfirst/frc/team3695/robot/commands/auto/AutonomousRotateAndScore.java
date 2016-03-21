
package org.usfirst.frc.team3695.robot.commands.auto;

import org.usfirst.frc.team3695.robot.commands.CommandRotateWithCam;
import org.usfirst.frc.team3695.robot.enumeration.objective.RotateWithCam;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The main autonomous commands.
 */
public class AutonomousRotateAndScore extends CommandGroup 
{
    public AutonomousRotateAndScore(RotateWithCam rotateRightOverall)
    {
    	addSequential(new CommandRotateWithCam(RotateWithCam.ROTATE_LEFT_OVERALL));
    	//addSequential(new CommandLaunchBall()); // TODO: DO SOMETHING WICKED :O
    }
}
