package com.pedroid.primeclient;


public class Log {

	final static String TAG = "PrimeTime";
	
	public static void a(String msg) {
		android.util.Log.v(TAG, msg);
	}
	public static void d(String msg) {
		android.util.Log.d(TAG, msg);
	}
	public static void e(String msg) {
		android.util.Log.e(TAG, msg);
	}
	public static void i(String msg) {
		android.util.Log.i(TAG, msg);
	}
	public static void v(String msg) {
		android.util.Log.v(TAG, msg);
	}
	public static void w(String msg) {
		android.util.Log.w(TAG, msg);
	}
}
