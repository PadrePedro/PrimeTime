package com.pedroid.primeclient;

public interface PrimeListener {
	/**
	 * Called when a prime number result is ready to be reported.  This is called from non-UI thread,
	 * so the recipient must not manipulate UI from this call directly.
	 * @param index - position of prime number
	 * @param prime - value of prime
	 * @param fromCache - is it from cache or calculated?
	 */
	void onPrime(int index, int prime, boolean fromCache);
}
