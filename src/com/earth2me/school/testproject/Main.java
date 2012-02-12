package com.earth2me.school.testproject;

/**
 * Encloses main entry point for application. Cannot be instantiated.
 * 
 * @category APCS
 * @version 1.0.0
 */
public final class Main
{
	/**
	 * Prevents instantiation of Main. Do not use. Do not remove.
	 * 
	 * @author Paul Buonopane
	 * @deprecated
	 */
	private Main()
	{
		// Intentionally blank.
	}

	/**
	 * Main entry point.
	 * 
	 * @author Paul Buonopane
	 * @param args
	 *            Command-line parameters.
	 */
	public static void main(String[] args)
	{
		// Run a new instance of TestProgram. execute will take care of
		// disposal.
		execute(new TestProgram());
	}

	/**
	 * Bring a program through its full lifecycle, including disposal.
	 * 
	 * @author Paul Buonopane
	 * @param program
	 *            The program to execute.
	 */
	private static void execute(IProgram program)
	{
		// Determine the (unqualified) type name of the program using reflection.
		String programName = program.getClass().getSimpleName();
		
		// Run the program, and dispose of it when done.
		Console.writeLine("Executing program: %s", programName);
		try
		{
			program.run();
		}
		finally
		{
			Console.writeLine("Disposing program: %s", programName);
			program.dispose();
		}
	}
}
