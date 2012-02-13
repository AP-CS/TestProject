package com.earth2me.school.testproject;

/**
 * Must explicitly free resources.
 * 
 * @category APCS
 * @version 1.0.0
 */
interface IDisposable
{
	/**
	 * Explicitly frees resources held by the object and its children. Renders
	 * the object unusable.
	 * 
	 * @author Paul Buonopane
	 */
	void dispose();
}
