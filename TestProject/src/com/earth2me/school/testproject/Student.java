package com.earth2me.school.testproject;

/**
 * A single student's identification and test scores.
 * 
 * @category APCS
 * @version 1.0.0
 */
final class Student
{
	private final TestResults context;
	private final int index;

	// Cache variables. Not really necessary, but they speed things up
	// dramatically. Well worth the few extra globals.
	private int correct = -1;
	private float score = -1.0f;
	private byte percent = -1;
	private char grade = 0;

	/**
	 * Instantiates a new student.
	 * 
	 * @author Paul Buonopane
	 * @param context
	 *            The test results from which the student is to be parsed.
	 * @param index
	 *            The index of the student with the test results.
	 */
	public Student(TestResults context, int index)
	{
		this.context = context;
		this.index = index;
	}

	/**
	 * Gets the index identifying the student within its context.
	 * 
	 * @author Paul Buonopane
	 * @return The index of the student within its context.
	 */
	public int getIndex()
	{
		return index;
	}

	/**
	 * Gets the context in which the student resides.
	 * 
	 * @author Paul Buonopane
	 * @return The context in which the student resides.
	 */
	public TestResults getContext()
	{
		return context;
	}

	/**
	 * Returns the ID of the student.
	 * 
	 * @author Paul Buonopane
	 * @return The ID of the student.
	 */
	public String getId()
	{
		return context.studentIds[index];
	}

	/**
	 * Gets the student's answers to the test.
	 * 
	 * @author Paul Buonopane
	 * @return The student's answers to the test.
	 */
	public char[] getAnswers()
	{
		return context.answers[index];
	}

	/**
	 * Gets the number of answers the student got correct on the test.
	 * 
	 * @author Paul Buonopane
	 * @return The number of answers the student got correct on the test.
	 */
	public int getCorrect()
	{
		// Check to see if we have a cached value.
		if (correct >= 0)
		{
			return correct;
		}
		// Resort to calculating.

		// Get local copies of these two, since we'll be using them a lot.
		final char[] key = context.answerKey;
		final char[] answers = getAnswers();

		// Compare the answer key and the answers character by character.
		int correct = 0;
		for (int i = 0; i < key.length && i < answers.length; i++)
		{
			if (key[i] == answers[i])
			{
				correct++;
			}
		}
		return correct;
	}

	/**
	 * Gets the student's score on the test.
	 * 
	 * @author Paul Buonopane
	 * @return The student's score on the test as a fraction, 1.0f being 100%,
	 *         0.0f being 0%.
	 */
	public float getScore()
	{
		// Check to see if we have a cached value.
		if (score >= 0.0f)
		{
			return score;
		}

		// Resort to caclulating.
		final float total = context.answerKey.length;
		final float correct = getCorrect();
		return score = correct / total;
	}

	/**
	 * Gets the percent correct.
	 * 
	 * @author Paul Buonopane
	 * @return Gets the percent correct, from 0 to 100.
	 */
	public byte getPercent()
	{
		// Check to see if we have a cached value.
		if (percent >= 0)
		{
			return percent;
		}
		
		return percent = (byte)Math.round(getScore() * 100);
	}

	/**
	 * Gets the letter grade representing the student's score on the test.
	 * 
	 * @author Paul Buonopane
	 * @return The upper-case letter grade representing the student's score on
	 *         the test.
	 */
	public char getGrade()
	{
		// Check to see if we have a cached value.
		if (grade > 0)
		{
			return grade;
		}

		// Resort to calculating.
		switch (Math.round(getScore() * 10.0f))
		{
		case 10:
			return grade = 'A';

		case 9:
			return grade = 'B';

		case 8:
		case 7:
			return grade = 'C';

		case 6:
		case 5:
			return grade = 'D';

		default:
			return grade = 'F';
		}
	}
}
