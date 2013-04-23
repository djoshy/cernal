package com.fizz.cernal;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class NiceAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<Post> data;
	private static LayoutInflater inflater = null;
	//public ImageLoader imageLoader;
	ViewHolder holder;

	NiceAdapter(Activity a, ArrayList<Post> d) {

		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	//	imageLoader = new ImageLoader(activity.getApplicationContext());

	}

	@Override
	public int getCount() {
		return data.toArray().length;

	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	public static class ViewHolder {
		public TextView label;
		public TextView addr;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.row, null);
			holder = new ViewHolder();
			holder.label = (TextView) vi.findViewById(R.id.title);
			holder.addr = (TextView) vi.findViewById(R.id.details);
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();


		holder.label.setText(data.get(position).getTitle());
		holder.addr.setText(data.get(position).getPubDate());


		return vi;
	}
}
