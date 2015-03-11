package com.pedroid.primeclient;


import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Adapter that produces the ListView rows to be displayed.  This is a never-ending adapter, in that
 * it automatically adds more rows to the end when the user scrolls near the end of the list.
 * @author peterliaw
 *
 */
public class PrimeAdapter extends ArrayAdapter<PrimeNumberEntry> implements PrimeListener{

	private PrimeApi primeApi;
	private Handler handler;
	
	public PrimeAdapter(Context context) {
		super(context, 0);
		primeApi = new PrimeRemoteApi(this);
		// initialize with 100 primes in adapter
		addMoreAdapterEntries(100);
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				PrimeAdapter.this.notifyDataSetChanged();
				super.handleMessage(msg);
			}};
	}
	
	public void load() {
		primeApi.load(getContext());
	}
	
	public void unload() {
		primeApi.unload(getContext());
	}
	
	public void clearCache() {
		primeApi.clearCache();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("getView: position=" + position);
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.prime_row, null);
        }
        TextView pos = (TextView)convertView.findViewById(R.id.position);
        TextView slope = (TextView)convertView.findViewById(R.id.slope);
        pos.setText(Integer.toString(position + 1));
        TextView prime = (TextView)convertView.findViewById(R.id.prime_value);
        PrimeNumberEntry pn = getItem(position);
        if (pn.getPrime()==0) {
        	prime.setText("?");
        	slope.setText("");
        	primeApi.getPrime(position);
        }
        else {
        	prime.setText(Integer.toString(pn.getPrime()));
        	slope.setText(String.format("%.2f",((float)pn.getPrime())/(position+1)));
        }
        // indicate items from cache with blue text
        prime.setTextColor(pn.isFromCache() ? Color.BLUE : Color.BLACK);
        // if we're within 20 entries of the adapter limit, add 50 more entries to simulate never-ending list
        if (position + 20 > this.getCount())
        	addMoreAdapterEntries(50);
        return convertView;
	}
	
	/**
	 * Adds 100 more entries, to simulate endless listview
	 */
	private void addMoreAdapterEntries(int count) {
		for (int i=0;i<count;i++) {
			add(new PrimeNumberEntry());
		}
	}

	final int NOTIFY_DATA_SET_CHANGED = 1;
	
	@Override
	public void onPrime(final int position, final int value, final boolean fromCache) {
		PrimeNumberEntry entry = PrimeAdapter.this.getItem(position);
		entry.setPrime(value);
		entry.setFromCache(fromCache);
		// We get frequent callbacks for onPrime, so hold off calling notifyDataSetChanged until
		// there hasn't been any callbacks in the last 500ms.  This greatly improves
		// ListView scrolling responsiveness.
		handler.removeMessages(NOTIFY_DATA_SET_CHANGED);
		handler.sendEmptyMessageDelayed(NOTIFY_DATA_SET_CHANGED, 500);
	}
	
	

}
