package org.usfirst.frc.team3695.robot.util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Output extends Thread implements Runnable {
	private Output() {} //Can't construct this!
	
	/**
	 * The live instance of this thread.
	 */
	private static Output instance;
	
	/**
	 * The que of messages to print.
	 */
	private List<String> messages = new CopyOnWriteArrayList<>();
	
	/**
	 * Statuses for messages.
	 */
	public static final String DEBUG = "DEBUG:   ",
							   INFO = "INFO:    ",
							   WARNING = "WARNING: ",
							   ERROR = "ERROR:   ";
	
	/**
	 * The current number of messages that have been printed.
	 */
	int message = 0;
	
	/**
	 * Adds a message to the output stream.
	 * @param status The status as defined by the constants Output.DEBUG, Output.INFO,
	 * Output.WARNING, and Output.ERROR. This method also appends the message count
	 * of the logger incase prints get out of order.
	 * @param str The message.
	 */
	public synchronized void addMessage(String status, String str) {
		message++;
		String spacing = "";
		for(int i = 0; i < 3 - (int)(Math.log10(message)); i++) {
			spacing += " ";
		}
		messages.add(status + "[" + spacing + message + "][LOG]" + str);
	}
	
	/**
	 * Get the singleton of the output class. Also starts this thread
	 * on the first call.
	 * @return The only instance of this class.
	 */
	public static Output getInstance() {
		if(instance == null) {
			instance = new Output();
			instance.start();
			return instance;
		}
		return instance;
	}
	
	public void run() {
		try {Thread.sleep(1000);} catch (InterruptedException e) {} //Wait a second to awaken the debugger.
		System.out.println("WARNING: INITIALIZING LOGGER! ---------------------------------"); //Tells the user that the logger and robot has started.
		while(true) {
			//Sleep for .1 seconds so the messages print out in order! This is the whole point of the class btw.
			try {Thread.sleep(80);} catch (InterruptedException e) {System.out.println("ERROR: The debugger has awoken. This should not have happened!");} 
			if(messages.size() > 0) {
				System.out.println(messages.remove(0)); //Dequeue message and print it.
			}
		}
	}
}
