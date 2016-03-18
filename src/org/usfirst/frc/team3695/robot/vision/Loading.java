package org.usfirst.frc.team3695.robot.vision;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.Rect;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;

public class Loading extends Thread implements Runnable{
	public static final int BIG_RADIUS = 64;
	public static final int SMALL_RADIUS = 8;
	public static final double ANIMATION_TIME_MAGIC = 6;
	public static final double DISTANCE = 0.1;
	public static final int FPS = 30;
	
	double currentTimeSeconds = 0.0;
	boolean running = true;
	Image waitImage;
	
	Rect dot = new Rect(0,0,SMALL_RADIUS,SMALL_RADIUS);
	
	public Loading(Image waitImage, double currentTimeSeconds) {
		this.currentTimeSeconds = currentTimeSeconds;
		this.waitImage = waitImage;
	}
	
	public void run() {
		while(running) {
			try {
				NIVision.imaqDrawShapeOnImage(waitImage, waitImage, new Rect(0,0,480,640), DrawMode.PAINT_VALUE, ShapeMode.SHAPE_RECT , Camera.getColor(0x0, 0x0, 0x0));
				for(int i = 0; i < 5; i++) {
					double x = calcX(currentTimeSeconds - (i * DISTANCE));
					double y = calcY(currentTimeSeconds - (i * DISTANCE));
					NIVision.imaqDrawShapeOnImage(waitImage, waitImage, getRectFromPoint((int)x,(int)y), DrawMode.PAINT_VALUE, ShapeMode.SHAPE_RECT , Camera.getColor(0xFF, 0xFF, 0xFF));
				}
				currentTimeSeconds += 1000/FPS;
				CameraServer.getInstance().setImage(waitImage);
				Thread.sleep(1000/FPS);
			} catch (Exception e) {
				running = false;
				DriverStation.reportError("The loading thread exited because of: " + e.toString(), true);
			}
			
		}
	}
	
	public double end() {
		running = false;
		return currentTimeSeconds;
	}
	
	public static double calcX(double time) {
		return (BIG_RADIUS*Math.cos(-1 * ANIMATION_TIME_MAGIC * time - 0.8 * Math.sin(ANIMATION_TIME_MAGIC * time) + 3.0*Math.PI/2.0));
	}
	
	public static double calcY(double time) {
		return -1.0 * (BIG_RADIUS*Math.sin(-1 * ANIMATION_TIME_MAGIC * time - 0.8 * Math.sin(ANIMATION_TIME_MAGIC * time) + 3.0*Math.PI/2.0));
	}
	
	public static Rect getRectFromPoint(int x, int y) {
		return new Rect((480/2) - SMALL_RADIUS - y, (640/2) - SMALL_RADIUS + x, SMALL_RADIUS, SMALL_RADIUS);
	}
}
