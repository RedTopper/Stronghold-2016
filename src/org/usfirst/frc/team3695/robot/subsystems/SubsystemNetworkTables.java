package org.usfirst.frc.team3695.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 * Kind of convenient way to access network tables from other
 * parts of the robot.
 */
public class SubsystemNetworkTables extends Subsystem {
	ITable table;
	
	double rawGoalX;
	double rawGoalY;
	
	public SubsystemNetworkTables() {
        table = NetworkTable.getTable("GRIP").getSubTable("raw");
	}
	
	@Override
	protected void initDefaultCommand() {
	}
	
	public double getRawGoalX() {
		return rawGoalX;
	}
	
	public double getRawGoalY() {
		return rawGoalY;
	}
	
	public void log() {
		SmartDashboard.putNumber("Goal X Position", getRawGoalX());
		SmartDashboard.putNumber("Goal Y Position", getRawGoalY());
	}
	
	public void updateInfo() {
		double[] centerX = table.getNumberArray("centerX", new double[] {-1.0});
		rawGoalX = (centerX.length > 0 ? centerX[0] : -1.0);
		
		double[] centerY = table.getNumberArray("centerY", new double[] {-1.0});
		rawGoalY = (centerY.length > 0 ? centerY[0] : -1.0);
	}
}
