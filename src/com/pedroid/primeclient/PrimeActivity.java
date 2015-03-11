package com.pedroid.primeclient;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * Main application activity
 * @author peterliaw
 *
 */
public class PrimeActivity extends Activity {

	ListView listView;
	PrimeAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prime);
		listView = (ListView)findViewById(R.id.list_view);
		adapter = new PrimeAdapter(this);
		adapter.load();
		listView.setAdapter(adapter);
	}

	@Override
	protected void onDestroy() {
		adapter.unload();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		if (item.getItemId()==R.id.action_clear_cache)
//			adapter.clearCache();
//		return super.onOptionsItemSelected(item);
//	}
	
	

}
