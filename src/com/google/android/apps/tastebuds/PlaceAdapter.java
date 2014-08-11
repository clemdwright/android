package com.google.android.apps.tastebuds;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PlaceAdapter extends BaseAdapter {
	private final Context context;
	private List<String> imageUrls = new ArrayList<String>();
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
		if (convertView == null) { //create a new view if no recycling available
			imageView = new ImageView(context);
		} else {
			imageView = (ImageView) convertView;
		}
		imageView.setImageResource(R.drawable.place_thumbnail);
		return imageView;
	}
}
	
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		RelativeLayout relativeLayout;
//		ImageView imageView;
////		TextView textView;
//		if (convertView == null) { //create a new view if no recycling available
//			relativeLayout = new RelativeLayout(context);
//			imageView = new ImageView(context);
////			textView = new TextView(context);
//			
//			
//		} else {
//			relativeLayout = (RelativeLayout) convertView;
//			imageView = new ImageView(context); //bad
////			imageView = (ImageView) relativeLayout.findViewById(R.id.place_thumbnail); //untested
////			textView = new TextView(context); //need to find by id instead
//			
//		}
////		textView.setText("Monk's kettle");
//		imageView.setImageResource(R.drawable.place_thumbnail);
//		relativeLayout.addView(imageView);
////		relativeLayout.addView(textView);
//		return relativeLayout;
//	}
//}
