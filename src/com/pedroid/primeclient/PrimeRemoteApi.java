package com.pedroid.primeclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/**
 * PrimeApi implementation that binds to PrimeService. 
 * @author peterliaw
 *
 */
public class PrimeRemoteApi implements PrimeApi{

	public static final int MSG_GET_PRIME = 1;
	public static final int MSG_GOT_PRIME = 2;
	public static final int MSG_GOT_PRIME_FROM_CACHE = 3;
	public static final int MSG_CLEAR_CACHE = 4;
	

	private PrimeListener primeListener;
	private Messenger messenger = new Messenger(new IncomingHandler());
	private Messenger service;
	private boolean serviceBounded;
	
	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName component, IBinder binder) {
			service = new Messenger(binder);
			serviceBounded = true;
			Log.d("onServiceConnected");
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			serviceBounded = false;
			Log.d("onDisconnectedServiceConnected");			
		}
	};
	
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_GOT_PRIME:
                case MSG_GOT_PRIME_FROM_CACHE:
        			Log.d("PrimeRemoteApi.handleMessage index=" + msg.arg1 + " prime=" + msg.arg2 + " from " + msg.what);			
                	primeListener.onPrime(msg.arg1, msg.arg2, msg.what == MSG_GOT_PRIME_FROM_CACHE);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Constructor that sets the listener for when prime numbers are retrieved
     * @param listener
     */
	public PrimeRemoteApi(PrimeListener listener) {
		primeListener = listener;
	}
	
	/**
	 * Binds to PrimeService
	 */
	@Override
	public boolean load(Context context) {
		Intent intent = new Intent();
		intent.setClassName("com.pedroid.primetime", "com.pedroid.primetime.PrimeService");
        return context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
//        return context.bindService(new Intent(context, 
//                PrimeService.class), serviceConnection, Context.BIND_AUTO_CREATE);
	}
	
	/**
	 * Unbinds from PrimeService
	 */
	@Override
	public void unload(Context context) {
		context.unbindService(serviceConnection);
	}

	/**
	 * Retrieves the prime number at position index via PrimeService
	 */
	@Override
	public boolean getPrime(int index) {
		if (!serviceBounded)
			return false;
		Message msg = Message.obtain();
		msg.what = MSG_GET_PRIME;
		msg.arg1 = index;
		msg.replyTo = messenger;
		try {
			service.send(msg);
			return true;
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void clearCache() {
		if (serviceBounded) {
			Message msg = Message.obtain();
			msg.what = MSG_CLEAR_CACHE;
			try {
				service.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
		}
	}

	
}
