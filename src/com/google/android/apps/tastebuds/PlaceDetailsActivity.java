package com.google.android.apps.tastebuds;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class PlaceDetailsActivity extends Activity {

	private ImageView placeImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_details);

		placeImage = (ImageView) findViewById(R.id.place_image);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		Intent intent = getIntent();
		if (intent != null) {
			String url = intent.getExtras().getString("placeid"); //not really the palceid, just a photo url, but will be an id in the future
			new DownloadImageAsyncTask().execute(url);
		}
	}

	/*
	 * will want maybe two separate async tasks...
	 * one to get place details from the placeid that is passed with the intent
	 * then, once there is an image url from that, a separate task to get the image
	 */
	
	class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

		@Override
    	protected Bitmap doInBackground(String... params) {
    		String imageUrl = params[0];
        	try {
        		return BitmapFactory.decodeStream(
        				(InputStream) new URL(imageUrl).getContent());
        	} catch (IOException e) {
        		Log.e("DownloadImageAsyncTask", "Error reading bitmap", e);
        	}
        	return null;
    	}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				placeImage.setImageBitmap(result);
			}
		}
	}
}
