package com.google.android.apps.tastebuds;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class NearbyPlacesActivity extends ActionBarActivity {

	private ImageAdapter adapter;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places);
        
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new ImageAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	
        	@Override
        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        		Intent intent = new Intent(NearbyPlacesActivity.this, PlaceDetailsActivity.class);
        		intent.putExtra("placeid", (String) adapter.getItem(position));
        		startActivity(intent);
        	}
        });
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nearby_places, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		JsonParser atomParser = new JsonParser();
		atomParser.parse(new JsonParser.ParseCompleteCallback() {
			
			@Override
			public void onParseComplete(List<String> urls) {
				adapter.setImageUrls(urls);
				adapter.notifyDataSetChanged();
			}
		});
	}
}
