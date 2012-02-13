package com.earth2me.school.testproject;

/**
 * Data structure identifying the results of a call to
 * TestProgram.getTestResults.
 * 
 * @category APCS
 * @version 1.0.0
 */
final class GetTestResultsArgs
{
	public final boolean success;
	public final TestResults testResults;

	/**
	 * Initializes all fields to the default values of their respective types.
	 * 
	 * @author Paul Buonopane
	 */
	public GetTestResultsArgs()
	{
		success = false;
		testResults = null;
	}

	/**
	 * Initializes all fields to the given values.
	 * 
	 * @author Paul Buonopane
	 * @param success
	 *            Used to initialize the corresponding field.
	 * @param testResults
	 *            Used to initialize the corresponding field.
	 */
	public GetTestResultsArgs(boolean success, TestResults testResults)
	{
		this.success = success;
		this.testResults = testResults;
	}
}
