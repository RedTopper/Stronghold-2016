package org.usfirst.frc.team3695.robot.vision;

import org.usfirst.frc.team3695.robot.util.Logger;
import org.usfirst.frc.team3695.robot.util.Util;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.Rect;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;

public class Loading extends Thread implements Runnable{
	/**
	 * Distance away from the center that the particles are.
	 */
	public static final int BIG_RADIUS = 64;
	
	/**
	 * Radius of the small particles
	 */
	public static final int SMALL_RADIUS = 8;
	
	/**
	 * Speeds up or slows down the loading thing. Not quite sure how
	 * it works.
	 */
	public static final double ANIMATION_TIME_MAGIC = 3;
	
	/**
	 * Distance away the small particles are.
	 */
	public static final double DISTANCE = 0.1;
	
	/**
	 * Frames per second the thread executes at.
	 */
	public static final int FPS = 30;
	
	private double currentTimeSeconds = 0.0;
	private boolean loop = true;
	private boolean running = true;
	private Image waitImage;
	
	/**
	 * Creates a neat loading screen when something is loading. It looks like
	 * the Windows 8/8.1/10 loading screen.
	 * @param waitImage The image that will be written to when the thread starts.
	 * @param currentTimeSeconds The current time that the animation was started at.
	 */
	public Loading(Image waitImage, double currentTimeSeconds) {
		this.currentTimeSeconds = currentTimeSeconds;
		this.waitImage = waitImage;
	}
	
	public void run() {
		while(loop) {
			try {
				NIVision.imaqDrawShapeOnImage(waitImage, waitImage, new Rect(0,0,480,640), DrawMode.PAINT_VALUE, ShapeMode.SHAPE_RECT ,Util.hsvToRgb((currentTimeSeconds/2000.0) % 1.0, 0.7, 1.0));
				for(int i = 0; i < 5; i++) {
					double x = calcX(currentTimeSeconds - (i * DISTANCE));
					double y = calcY(currentTimeSeconds - (i * DISTANCE));
					NIVision.imaqDrawShapeOnImage(waitImage, waitImage, getRectFromPoint((int)x,(int)y), DrawMode.PAINT_VALUE, ShapeMode.SHAPE_OVAL , Util.getColor(0xFF, 0xFF, 0xFF));
				}
				currentTimeSeconds += (double)1000/(double)FPS;
				CameraServer.getInstance().setImage(waitImage);
				Thread.sleep((int)((double)1000/(double)FPS));
			} catch (Exception e) {
				loop = false;
				Logger.err("The main thread exited! ", e);
			}
		}
		running = false;
	}
	
	/**
	 * ends the execution of the loading screen. It may take a 
	 * few ms for the loading thread to actually quit, so it's a good
	 * idea to check with Loading.running() if the thread is still running.
	 * 
	 * @return The time the loop was stopped at in seconds. This time does
	 * not represent the time that would appear on a wall clock, but instead
	 * the amount of time that the loading screen was active for.
	 */
	public synchronized double end() {
		loop = false;
		return currentTimeSeconds;
	}
	
	/**
	 * Used to determine if the thread is messing with the camera server or not.
	 * @return True if this thread is running, false otherwise. It's necessary to call 
	 * Loading.end() before this method is used to check if the program is stopped.
	 */
	public synchronized boolean running() {
		return running;
	}
	
	/**
	 * Calculates the x position of one of the small dots based on the amount of seconds
	 * that has passed.
	 * @param time Decimal representation of seconds.
	 * @return The x position of one of the dots.
	 */
	public static double calcX(double time) {
		return BIG_RADIUS * Math.cos(-1 * ANIMATION_TIME_MAGIC * time - 0.8 * Math.sin(ANIMATION_TIME_MAGIC * time) + 3.0 * Math.PI/2.0);
	}
	
	/**
	 * Calculates the y position of one of the small dots based on the amount of seconds
	 * that has passed.
	 * @param time Decimal representation of seconds.
	 * @return The y position of one of the dots.
	 */
	public static double calcY(double time) {
		return BIG_RADIUS * Math.sin(-1 * ANIMATION_TIME_MAGIC * time - 0.8 * Math.sin(ANIMATION_TIME_MAGIC * time) + 3.0 * Math.PI/2.0);
	}
	
	/**
	 * Creates a rectangle to be put on the CameraServer based off an XY position.
	 * @param x The X position of the rectangle where 0, 0 is the origin.
	 * @param y The Y position of the rectangle where 0, 0  is the origin.
	 * @return A rectangle (actually, a square) to be used to draw the circles on the CameraServer.
	 */
	public static Rect getRectFromPoint(int x, int y) {
		return new Rect((480/2) - SMALL_RADIUS - y, (640/2) - SMALL_RADIUS + x, SMALL_RADIUS, SMALL_RADIUS);
	}
}
