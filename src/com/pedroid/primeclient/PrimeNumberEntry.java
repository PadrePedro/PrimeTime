package com.pedroid.primeclient;

/**
 * Representation of a prime number, position and cache attribute to be used by adapter.
 * @author peterliaw
 *
 */
public class PrimeNumberEntry {

	private int value;
	private boolean fromCache;
	
	public void setPrime(int value) {
		this.value = value;
	}
	
	public int getPrime() {
		return value;
	}
	
	public void setFromCache(boolean fromCache) {
		this.fromCache = fromCache;
	}
	
	public boolean isFromCache() {
		return fromCache;
	}
	
	
}
