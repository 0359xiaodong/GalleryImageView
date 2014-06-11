package com.leochin.galleryimageview;

import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class PictureViewFragment extends Fragment implements
		OnItemClickListener, OnHierarchyChangeListener {

	private PicGallery gallery;
	private GalleryAdapter mAdapter;
	private Activity mainActivity;
	private TextView mSummaryText;
	private View mView;

	private String[] imageUrls = {
			"http://f.hiphotos.baidu.com/image/pic/item/cb8065380cd791235af13afeaf345982b2b78023.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/738b4710b912c8fca654a13efe039245d7882199.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/c75c10385343fbf25f57e7abb27eca8065388f3b.jpg",
			"http://g.hiphotos.baidu.com/image/pic/item/fd039245d688d43fd1eed3007f1ed21b0ff43bf8.jpg",
			"http://h.hiphotos.baidu.com/image/pic/item/77c6a7efce1b9d166a805aecf1deb48f8d5464fc.jpg",
			"http://h.hiphotos.baidu.com/image/pic/item/b7003af33a87e9505ada2b2112385343faf2b4aa.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/21a4462309f79052b5c37c300ef3d7ca7acbd5db.jpg",
			"http://a.hiphotos.baidu.com/image/pic/item/0823dd54564e92584112a1619e82d158ccbf4e1e.jpg" };

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		mainActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.picture_view, null);

		return mView;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		gallery = (PicGallery) mView.findViewById(R.id.pic_gallery);
		mSummaryText = (TextView) mView.findViewById(R.id.pic_summary_txt);

		gallery.setVerticalFadingEdgeEnabled(false);// 取消竖直渐变边框
		gallery.setHorizontalFadingEdgeEnabled(false);// 取消水平渐变边框
		gallery.setDetector(new GestureDetector(getActivity(),
				new MySimpleGesture()));
		mAdapter = new GalleryAdapter(mainActivity);
		mAdapter.setData(Arrays.asList(imageUrls));
		gallery.setAdapter(mAdapter);
		gallery.setOnItemClickListener(this);
		gallery.setOnHierarchyChangeListener(this);

	}

	// 实现双击屏幕方法功能
	private class MySimpleGesture extends SimpleOnGestureListener {
		// 按两下的第二下Touch down时触发
		public boolean onDoubleTap(MotionEvent e) {

			View view = gallery.getSelectedView();
			if (view instanceof MyImageView) {
				MyImageView imageView = (MyImageView) view;
				if (imageView.getScale() > imageView.getMiniZoom()) {
					imageView.zoomTo(imageView.getMiniZoom());
				} else {
					imageView.zoomTo(imageView.getMaxZoom());
				}
			} else {

			}
			return true;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		Toast.makeText(getActivity(), "click ...", Toast.LENGTH_SHORT).show();
		int flag = mSummaryText.getVisibility();
		if (flag == View.GONE) {
			mSummaryText.setVisibility(View.VISIBLE);
		} else {
			mSummaryText.setVisibility(View.GONE);
		}
	}

	@Override
	public void onChildViewAdded(View parent, View child) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChildViewRemoved(View parent, View child) {
		// TODO Auto-generated method stub
		
		Log.d("leochin", "this hello leochin " + gallery.getSelectedItemPosition());
	}

}
