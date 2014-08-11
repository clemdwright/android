package com.google.android.apps.tastebuds;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PlaceAdapter extends BaseAdapter {
	private final Context context;
	private List<String> imageUrls = new ArrayList<String>();
	private final LruCache<String, Bitmap> imageCache = new LruCache<String, Bitmap>(
			100);
	private final Set<String> downloadingImageUrls = new HashSet<String>();

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	public PlaceAdapter(Context c) {
		context = c;
	}

	@Override
	public int getCount() {
		return imageUrls.size();
	}

	@Override
	public Object getItem(int position) {
		if (position >= imageUrls.size()) {
			return null;
		}
		return imageUrls.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // create a new view if no recycling
									// available
			imageView = new ImageView(context);
		} else {
			imageView = (ImageView) convertView;
		}
		if (position >= imageUrls.size()) { // data not yet downloaded!
			return imageView;
		}
		String imageUrl = imageUrls.get(position);
		
		Bitmap bitmap = imageCache.get(imageUrl);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			if (!downloadingImageUrls.contains(imageUrl)) {
				downloadingImageUrls.add(imageUrl);
				new DownloadImageAsyncTask(imageUrl).execute();
			}
		}
		
		return imageView;
	}

	class DownloadImageAsyncTask extends AsyncTask<Void, Void, Void> {
		private final String imageUrl;

		public DownloadImageAsyncTask(String imageUrl) {
			this.imageUrl = imageUrl;
		}

//		@Override
//		protected void onPreExecute() {
//			Log.i("DownloadImageAsyncTask", "Starting image download task...");
//		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(
						imageUrl).getContent());
				imageCache.put(imageUrl, bitmap);
			} catch (IOException e) {
				Log.e("DownloadImageAsyncTask", "Error reading bitmap", e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			downloadingImageUrls.remove(imageUrl);
			notifyDataSetChanged();
		}
	}
}

// @Override
// public View getView(int position, View convertView, ViewGroup parent) {
// RelativeLayout relativeLayout;
// ImageView imageView;
// // TextView textView;
// if (convertView == null) { //create a new view if no recycling available
// relativeLayout = new RelativeLayout(context);
// imageView = new ImageView(context);
// // textView = new TextView(context);
//
//
// } else {
// relativeLayout = (RelativeLayout) convertView;
// imageView = new ImageView(context); //bad
// // imageView = (ImageView) relativeLayout.findViewById(R.id.place_thumbnail);
// //untested
// // textView = new TextView(context); //need to find by id instead
//
// }
// // textView.setText("Monk's kettle");
// imageView.setImageResource(R.drawable.place_thumbnail);
// relativeLayout.addView(imageView);
// // relativeLayout.addView(textView);
// return relativeLayout;
// }
// }
