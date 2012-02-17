package com.earth2me.school.testproject;

import java.text.ParseException;

/**
 * Immutable test results structure.
 * 
 * @category APCS
 * @version 1.0.0
 */
final class TestResults
{
	/*
	 * Note that this is an immutable data structure. Scope rules do not apply:
	 * all fields MUST be public and final. Structures avoid logic, so it is
	 * poor practice to put methods in data structures. There should not be any
	 * accessors for reasons of efficiency. You are essentially attempting to
	 * make a new primitive--and literally are, depending on the language.
	 * 
	 * Static methods, however, are allowed, and are often used for parsing.
	 */

	public final char[] answerKey;
	public final String[] studentIds;
	public final char[][] answers;

	/*
	 * All data structures should have a parameterless constructor that
	 * initializes all fields to their respective types' default value.
	 */
	/**
	 * Initialize all fields to default values for their respective types.
	 * 
	 * @author Paul Buonopane
	 */
	public TestResults()
	{
		answerKey = null;
		studentIds = null;
		answers = null;
	}

	/**
	 * Initialize all fields to the given values.
	 * 
	 * @author Paul Buonopane
	 * @param answerKey
	 *            Used to initialize the corresponding field.
	 * @param studentIds
	 *            Used to initialize the corresponding field.
	 * @param answers
	 *            Used to initialize the corresponding field.
	 */
	public TestResults(final char[] answerKey, final String[] studentIds, final char[][] answers)
	{
		this.answerKey = answerKey;
		this.studentIds = studentIds;
		this.answers = answers;
	}

	/**
	 * Parses lines of text as test results.
	 * 
	 * @author Paul Buonopane
	 * @param lines
	 *            The lines of text to be parsed. The first denotes the answer
	 *            key. Subsequent lines represent individual students. Each
	 *            student line is composed of two tokens separated by a space.
	 *            The first token is the student ID. The second token is the
	 *            student's answers to the test, to be matched with the answer
	 *            key character-by-character.
	 * @return A new TestResults object parsed from the lines of text.
	 * @throws ParseException
	 *             if the text is of an invalid format and cannot be parsed.
	 */
	public static TestResults parse(final String[] lines)
			throws ParseException
	{
		// Arrays can be null.
		if (lines == null)
		{
			throw new ParseException("The text to parse was null.", -1);
		}
		
		// We must have at least an answer key.
		if (lines.length < 1)
		{
			throw new ParseException("There is no answer key.", 0);
		}
		
		// The answer key will be the first line.  Make it upper-case.
		final char[] answerKey = lines[0].toUpperCase().toCharArray();
		
		// The number of students is the number of lines minus the answer key.
		final String[] studentIds = new String[lines.length - 1];
		
		// The answers form a matrix:
		// the number of students by the number of answers.
		final char[][] answers = new char[studentIds.length][answerKey.length];
		
		// Fill the arrays for each student.
		for (int i = 0; i < studentIds.length; i++)
		{
			// Tokenize by space delimiters.
			final String[] tokens = lines[i + 1].split("\\s+", 2);
			
			// If we don't have a full line, skip it.
			if (tokens.length < 2)
			{
				continue;
			}
			
			// The first token is the student ID.
			studentIds[i] = tokens[0];
			
			// Ensure the answers are upper-case.
			answers[i] = tokens[1].toUpperCase().toCharArray();
			
			// Ensure that we have the same number of answers as in the answer key.
			if (answers[i].length != answerKey.length)
			{
				throw new ParseException("Student " + studentIds[i] + " has the wrong number of answers.", i + 1);
			}
		}
		
		// Create a new TestResults object from what we parsed.
		return new TestResults(answerKey, studentIds, answers);
	}
}
