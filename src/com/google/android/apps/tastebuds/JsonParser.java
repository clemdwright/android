package com.google.android.apps.tastebuds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

public class JsonParser {
	public interface ParseCompleteCallback {
		void onParseComplete(List<String> urls);
	}
	
	private static final String API_KEY = "AIzaSyDSxGRYQXuA7qy3Rzcu1zILt2hAqbNcHaM";
	private String photoRequestOne = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=120&photoreference=";
	private String photoRequestTwo = "&key=";
	

	


	protected String getJsonAsString() throws IOException {
		InputStream stream = new URL(PLACE_FEED_URL).openConnection().getInputStream();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream, "UTF-8"));
		StringBuilder result = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}
	
	private static final String PLACE_FEED_URL = "https://maps.googleapis.com/"
	+ "maps/api/place/nearbysearch/json?location=37.760107,-122.425908" 
	+ "&radius=500&types=food&key=AIzaSyDSxGRYQXuA7qy3Rzcu1zILt2hAqbNcHaM";
	
	public void parse(final ParseCompleteCallback parseCompleteCallback) {
		new AsyncTask<Void, Void, List<String>>() {
			
			@Override
			protected List<String> doInBackground(Void... params) {
				try {

					JSONObject feed = new JSONObject(getJsonAsString());
					JSONArray places = feed.getJSONArray("results");
					List<String> result = new ArrayList<String>();
					
					for (int i = 0; i < places.length(); i++) {
						JSONObject place = places.getJSONObject(i);
						JSONArray photos = place.getJSONArray("photos");
						JSONObject firstPhoto = photos.getJSONObject(0);
						String photoReference = firstPhoto.getString("photo_reference");
						String photoUrl = photoRequestOne + photoReference + photoRequestTwo + API_KEY;
						result.add(photoUrl);
					}

					return result;
				} catch (Exception e) {
					return Collections.emptyList();
				}
			}
			
			
			@Override
			protected void onPostExecute(List<String> result) {
				parseCompleteCallback.onParseComplete(result);
			}
		}.execute();
	}
}

//private static final String PLACE_FEED_URL = "https://picasaweb.google.com/"
//+ "data/feed/base/user/"
//+ "116526417878498972096/albumid/5750911813501865489?kind=photo"
//+ "&authkey=Gv1sRgCIHapMbBmI-VuAE&alt=json"
//+ "&fields=entry(media:group(media:content,media:thumbnail))";
//	
//	public void parse(final ParseCompleteCallback parseCompleteCallback) {
//		new AsyncTask<Void, Void, List<String>>() {
//			
//			@Override
//			protected List<String> doInBackground(Void... params) {
//				try {
//					JSONObject feed = new JSONObject(getJsonAsString());
//					
//					JSONArray entries = feed.getJSONObject("feed").getJSONArray("entry");
//					List<String> result = new ArrayList<String>();
//					for (int i = 0; i < entries.length(); i++) {
//						JSONObject picture = entries.getJSONObject(i);
//						JSONObject pictureData = (JSONObject) picture.getJSONObject(
//								"media$group").getJSONArray("media$thumbnail").get(0);
//						result.add(pictureData.getString("url"));
//					}
//					return result;
//				} catch (Exception e) {
//					return Collections.emptyList();
//				}
//			}
//			
//			@Override
//			protected void onPostExecute(List<String> result) {
//				parseCompleteCallback.onParseComplete(result);
//			}
//		}.execute();
//	}
//}
