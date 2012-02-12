package com.earth2me.school.testproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Compares students' test answers to an answer key and scores them accordingly.
 * Gathers basic statistics based on the results.
 * 
 * @category APCS
 * @version 1.0.0
 */
final class TestProgram implements IProgram
{
	private static final File FILE = new File("scores.txt");

	/**
	 * @author Paul Buonopane
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		// Attempt to get the test results.
		final GetTestResultsArgs testResultsArgs = getTestResults(FILE);
		if (!testResultsArgs.success || testResultsArgs.testResults == null)
		{
			// There was an error getting the test results. Exit.
			return;
		}

		// We should have a valid TestResults object.
		final TestResults testResults = testResultsArgs.testResults;

		// Get the objects that do all the heavy lifting.
		final Student[] students = getStudents(testResults);

		// Make some reports.
		reportIndividual(students);
		reportStudentCount(students);
		reportNumericAverage(students);
		reportFrequency(students);
	}

	/**
	 * Reports the frequency of each letter grade.
	 * 
	 * @author Paul Buonopane
	 * @param students
	 *            The students to observe for frequency of grades.
	 */
	private final void reportFrequency(final Student[] students)
	{
		// This is where we will keep track of the frequencies for each grade.
		Map<Character, Integer> frequencies = new HashMap<Character, Integer>();
		
		for (Student s : students)
		{
			final char grade = s.getGrade();
			int value = 1;
			
			// Check to see if this grade already has a frequency.
			if (frequencies.containsKey(grade))
			{
				// There is already a frequency.  Add to it.
				value += frequencies.get(grade);
			}
			
			// Store the frequency.
			frequencies.put(grade, value);
		}
		
		// Section header
		Console.writeLine("Grade Frequencies:");
		
		// This defines the order in which we will output the grades.
		for (char g : "ABCDF".toCharArray())
		{
			int frequency;
			if (frequencies.containsKey(g))
			{
				// We have a record of this frequency.
				frequency = frequencies.get(g);
			}
			else
			{
				// Nobody scored this grade.
				frequency = 0;
			}
			
			// Plural?
			String label = "student";
			if (frequency != 1)
			{
				label += "s";
			}
			
			// Output this particular grade.
			Console.writeLine(" %s: %d %s", g, frequency, label);
		}
	}

	/**
	 * Reports the numeric average score for all the students who took the test.
	 * 
	 * @author Paul Buonopane
	 * @param students
	 *            The array of students whose scores should be averaged.
	 */
	private final void reportNumericAverage(final Student[] students)
	{
		// Get a sum of all the scores.
		double sum = 0.0;
		for (Student s : students)
		{
			sum += s.getScore();
		}

		// Calculate a percentage.  Double would be overkill.
		final float percent = (float)(sum / students.length * 100);
		
		// Limit the percentage to two decimal places.
		Console.writeLine("Average Score: %%%.2f", percent);
	}

	/**
	 * Reports the number of students who took the test.
	 * 
	 * @author Paul Buonopane
	 * @param students
	 *            The students to count.
	 */
	private final void reportStudentCount(final Student[] students)
	{
		Console.writeLine("Total Student Count: %d", students.length);
	}

	/**
	 * Reports statistics about each individual student to the console.
	 * 
	 * @author Paul Buonopane
	 * @param students
	 *            The students to be reported.
	 */
	private final void reportIndividual(final Student[] students)
	{
		for (Student s : students)
		{
			Console.writeLine("Student %s: %s (%%%d)", s.getId(), s.getGrade(), s.getPercent());
		}
	}

	/**
	 * Gets an array representing the students who took the test.
	 * 
	 * @author Paul Buonopane
	 * @param testResults
	 * @return An array representing the students who took the test.
	 */
	private final Student[] getStudents(final TestResults testResults)
	{
		Student[] students = new Student[testResults.studentIds.length];
		for (int i = 0; i < students.length; i++)
		{
			// Instantiate a Student object for each test result.
			students[i] = new Student(testResults, i);
		}
		return students;
	}

	/**
	 * Attempts to get the test results.
	 * 
	 * @author Paul Buonopane
	 * @param file
	 *            The file to be read.
	 * @return The results of the attempt to read and parse the file.
	 */
	private final GetTestResultsArgs getTestResults(final File file)
	{
		// Read the test results or output an error and signal an exit.
		try
		{
			return new GetTestResultsArgs(true, readTestResults(file));
		}
		catch (FileNotFoundException ex)
		{
			// File could not be found.
			Console.writeLine("Unable to find file: %s", file);
		}
		catch (IOException ex)
		{
			// Error reading the file.
			Console.writeLine("Unable to read from file: %s", ex.getMessage());
		}
		catch (ParseException ex)
		{
			// Error parsing the file.
			Console.writeLine("Could not parse file: %s", file);
			Console.writeLine(ex.getMessage());
		}
		catch (Throwable ex)
		{
			// Unknown exception/error.
			ex.printStackTrace();
		}

		// An error/exception has occurred.
		return new GetTestResultsArgs();
	}

	/**
	 * Reads the test results from a file.
	 * 
	 * @author Paul Buonopane
	 * @return a TestResults object parsed from the text file.
	 * @throws FileNotFoundException
	 *             if the file cannot be found.
	 * @throws IOException
	 *             if there is an error reading the file.
	 * @throws ParseException
	 *             if there is an error parsing the file.
	 */
	private final TestResults readTestResults(final File file)
			throws IOException, ParseException
	{
		// Open the file for reading. Be sure to close it.
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		try
		{
			// Obtain an array of all lines.
			List<String> lines = new ArrayList<String>();
			while (reader.ready())
			{
				lines.add(reader.readLine());
			}

			// Parse the lines.
			return TestResults.parse(lines.toArray(new String[0]));
		}
		finally
		{
			reader.close();
		}
	}

	/**
	 * @author Paul Buonopane
	 * @see com.earth2me.school.testproject.IDisposable#dispose()
	 */
	@Override
	public final void dispose()
	{
		// Nothing global to dispose in this program.
	}
}
