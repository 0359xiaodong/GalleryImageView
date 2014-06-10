package com.leochin.galleryimageview;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;

public class GalleryAdapter extends BaseAdapter {

	private Context context;
	private List<String> mItems;
	public RequestQueue mQueue;
	public LruImageCache mLruImageCache;
	public ImageLoader mImageLoader;

	public void setData(List<String> data) {
		this.mItems = data;
		notifyDataSetChanged();
	}

	public GalleryAdapter(Context context) {
		this.context = context;

		mQueue = Volley.newRequestQueue(context);
		mLruImageCache = LruImageCache.instance();
		mImageLoader = new ImageLoader(mQueue, mLruImageCache);
	}

	@Override
	public int getCount() {
		return mItems != null ? mItems.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Log.d("leochin","convertView = " + convertView);
		MyImageView view = (MyImageView) convertView;

		if (view == null) {
			view = new MyImageView(context);
			//Log.d("leochin","new MyimageView " + position);
		}

		view.setLayoutParams(new Gallery.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		String item = mItems.get(position);
		if (item != null) {
			view.setTag(item);

			ImageListener listener = ImageLoader.getImageListener(view,
					R.drawable.ic_launcher, R.drawable.ic_launcher);
			mImageLoader.get(item, listener);
		}

		return view;
	}

}
