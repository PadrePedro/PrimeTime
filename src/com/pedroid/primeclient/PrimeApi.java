package com.pedroid.primeclient;

import android.content.Context;

/**
 * Interface for Prime API
 * @author peterliaw
 *
 */
public interface PrimeApi {
	/**
	 * Get prime number at a certain position.  The results are returned asynchronously
	 * @param index
	 * @return if call was successful
	 */
	boolean getPrime(int index);
	/**
	 * Loads need resources for the api
	 * @param context
	 * @return if call was successful
	 */
	boolean load(Context context);
	/**
	 * Unloads resources used by the api
	 * @param context
	 */
	void unload(Context context);
	/**
	 * Clears the prime number cache
	 */
	void clearCache();
}
