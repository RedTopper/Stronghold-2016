package org.usfirst.frc.team3695.robot.vision;

import org.usfirst.frc.team3695.robot.Logger;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.Rect;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;

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
	
	double currentTimeSeconds = 0.0;
	boolean loop = true;
	boolean running = true;
	Image waitImage;
	
	Rect dot = new Rect(0,0,SMALL_RADIUS,SMALL_RADIUS);
	
	public Loading(Image waitImage, double currentTimeSeconds) {
		this.currentTimeSeconds = currentTimeSeconds;
		this.waitImage = waitImage;
	}
	
	public void run() {
		while(loop) {
			try {
				NIVision.imaqDrawShapeOnImage(waitImage, waitImage, new Rect(0,0,480,640), DrawMode.PAINT_VALUE, ShapeMode.SHAPE_RECT ,hsvToRgb((currentTimeSeconds/1000.0) % 1.0, 0.7, 1.0));
				for(int i = 0; i < 5; i++) {
					double x = calcX(currentTimeSeconds - (i * DISTANCE));
					double y = calcY(currentTimeSeconds - (i * DISTANCE));
					NIVision.imaqDrawShapeOnImage(waitImage, waitImage, getRectFromPoint((int)x,(int)y), DrawMode.PAINT_VALUE, ShapeMode.SHAPE_OVAL , Camera.getColor(0xFF, 0xFF, 0xFF));
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
	
	public synchronized double end() {
		loop = false;
		return currentTimeSeconds;
	}
	
	public synchronized boolean running() {
		return running;
	}
	
	public static double calcX(double time) {
		return BIG_RADIUS*Math.cos(-1 * ANIMATION_TIME_MAGIC * time - 0.8 * Math.sin(ANIMATION_TIME_MAGIC * time) + 3.0*Math.PI/2.0);
	}
	
	public static double calcY(double time) {
		return BIG_RADIUS*Math.sin(-1 * ANIMATION_TIME_MAGIC * time - 0.8 * Math.sin(ANIMATION_TIME_MAGIC * time) + 3.0*Math.PI/2.0);
	}
	
	public static Rect getRectFromPoint(int x, int y) {
		return new Rect((480/2) - SMALL_RADIUS - y, (640/2) - SMALL_RADIUS + x, SMALL_RADIUS, SMALL_RADIUS);
	}
	
	/**
	 * Convert a hue, saturation, and value to a float compatible with NIVision.
	 * @param hue double from 0-1
	 * @param saturation double from 0-1
	 * @param value double from 0-1
	 * @return a float based on NIVision.
	 */
	public static float hsvToRgb(double hue, double saturation, double value) {		
	    int h = (int)(hue * 6);
	    double f = hue * 6 - h;
	    double p = value * (1 - saturation);
	    double q = value * (1 - f * saturation);
	    double t = value * (1 - (1 - f) * saturation);

	    switch (h) {
	      case 0: return Camera.getColor((int)(value * 256.0), (int)(t * 256.0), (int)( p * 256.0));
	      case 1: return Camera.getColor((int)(q * 256.0), (int)(value * 256.0), (int)( p * 256.0));
	      case 2: return Camera.getColor((int)(p * 256.0), (int)(value * 256.0), (int)( t * 256.0));
	      case 3: return Camera.getColor((int)(p * 256.0), (int)(q * 256.0), (int)( value * 256.0));
	      case 4: return Camera.getColor((int)(t * 256.0), (int)(p * 256.0), (int)( value * 256.0));
	      case 5: return Camera.getColor((int)(value * 256.0), (int)(p * 256.0), (int)( q * 256.0));
	      default: return Camera.getColor(0, 0, 0);
	    }
	}
}
