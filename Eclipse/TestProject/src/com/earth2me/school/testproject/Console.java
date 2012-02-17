package com.earth2me.school.testproject;

/**
 * Provides a wrapper for stdin, stderr, and stdout.
 * 
 * @category APCS
 * @version 1.0.0
 */
final class Console
{
	/**
	 * Prevents instantiation of Main. Do not use. Do not remove.
	 * 
	 * @author Paul Buonopane
	 * @deprecated
	 */
	private Console()
	{
		// Intentionally blank.
	}

	/**
	 * Print a line of text to the console via stdout.
	 * 
	 * @author Paul Buonopane
	 * @param text
	 *            The text to print. Will be followed by a carriage return
	 *            and/or line feed, depending on the platform.
	 */
	public static void writeLine(String text)
	{
		System.out.println(text);
	}

	/**
	 * Print a line of text to the console via stdout using the specified
	 * format.
	 * 
	 * @author Paul Buonopane
	 * @param format
	 *            The format to print. Will be followed by a carriage return
	 *            and/or line feed, depending on the platform.
	 * @param args
	 *            The arguments to pass to the format.
	 */
	public static void writeLine(String format, Object... args)
	{
		System.out.println(String.format(format, args));
	}
}
