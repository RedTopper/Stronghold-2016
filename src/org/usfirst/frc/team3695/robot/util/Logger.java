package org.usfirst.frc.team3695.robot.util;

public class Logger {
	/**
	 * Prints the word DEBUG, the class name of the printer, and the message to the
	 * standard output stream.
	 * @param s String to print. Don't do anything fancy (like add a ": " etc). It'll do that
	 * for you.
	 * @return Printed string s (for convenience)
	 */
	public static String debug(String s) {
		Output.getInstance().addMessage(Output.DEBUG, "[" + getCaller() + "]: " + s);
		return s;
	}
	
	/**
	 * Prints the word INFO, the class name of the printer, and the message to the
	 * standard output stream.
	 * @param s String to print. Don't do anything fancy (like add a ": " etc). It'll do that
	 * for you.
	 * @return Printed string s (for convenience)
	 */
	public static String out(String s) {
		Output.getInstance().addMessage(Output.INFO, "[" + getCaller() + "]: " + s);
		return s;
	}
	
	/**
	 * Prints the word WARNING, the class name of the thing that called the method, and
	 * the message to the standard error stream.
	 * @param s String to print. Don't do anything fancy (like add a ": " etc). It'll do that
	 * for you.
	 * @return Printed string s (for convenience)
	 */
	public static String err(String s) {
		Output.getInstance().addMessage(Output.WARNING, "[" + getCaller() + "]: " + s);
		return s;
	}
	
	/**
	 * Prints the word ERROR, te class name of the thing that called the method, and
	 * the message to the standard error stream. Also outputs the full stack trace 2 lines
	 * below the error. Also spits the output with the words 
	 * "---------There was an exception!---------".
	 * @param s String to print. Don't do anything fancy (like add a ": " etc). It'll do that
	 * for you.
	 * @param e Exception to print
	 * @return Printed string s.
	 */
	public static String err(String s, Exception e) {
		Output.getInstance().addMessage(Output.ERROR, "[" + getCaller() + "]: " + s);
		Output.getInstance().addMessage(Output.ERROR, ": ---------------Stack Trace---------------");
		printError(e);
		return s;
	}
	
	/**
	 * Gets the name of the class that isn't this class that called the method.
	 * @return The name of the class.
	 */
	private static String getCaller() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(Logger.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
                return ste.getClassName();
            }
        }
        return null;
	}
	
	/**
	 * Print exception
	 * @param e Exception
	 */
	private static void printError(Exception e) {
		Output.getInstance().addMessage(Output.ERROR,": " + e.getMessage());
		for(int i = 0; i < e.getStackTrace().length; i++) {
			Output.getInstance().addMessage(Output.ERROR, ":     " +  e.getStackTrace()[i]);
		}
	}
}
