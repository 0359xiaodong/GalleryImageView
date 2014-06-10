package com.leochin.galleryimageview;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class PictureViewFragment extends Fragment {

	private PicGallery gallery;
	private GalleryAdapter mAdapter;
	private String[] imageUrls;
	private MainActivity mainActivity;
	private TextView mSummaryText;

	// private ActionBar mActionBar;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		mainActivity = (MainActivity) activity;
		imageUrls = mainActivity.getImageUrls();
		// mActionBar = activity.getActionBar();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.picture_view, null);
		
		fm = mainActivity.getSupportFragmentManager();
		
		mBackButton = (Button) view.findViewById(R.id.pic_button);
		
		mBackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				FragmentTransaction ft = fm.beginTransaction();
//				Fragment fragment = fm.findFragmentByTag("picFTag");
//				ft.remove(fragment);
//				ft.commit();
				//fm.popBackStack();
				mainActivity.finish();
				//Toast.makeText(getActivity(), "hello wenhao", Toast.LENGTH_LONG).show();
			}
		});

		return view;
	}
	
	private FragmentManager fm;
	private Button mBackButton;
	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		gallery = (PicGallery) view.findViewById(R.id.pic_gallery);
		mSummaryText = (TextView) view.findViewById(R.id.pic_summary_txt);

		gallery.setVerticalFadingEdgeEnabled(false);// 取消竖直渐变边框
		gallery.setHorizontalFadingEdgeEnabled(false);// 取消水平渐变边框
		gallery.setDetector(new GestureDetector(getActivity(),
				new MySimpleGesture()));
		mAdapter = new GalleryAdapter(getActivity());

		mAdapter.setData(arrayToList());
		gallery.setAdapter(mAdapter);
		gallery.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Toast.makeText(getActivity(), "LongClick唤起复制、保存操作",
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "click ...", Toast.LENGTH_SHORT)
						.show();
				int flag = mSummaryText.getVisibility();
				if (flag == View.GONE) {
					mSummaryText.setVisibility(View.VISIBLE);
					// mActionBar.show();
				} else {
					mSummaryText.setVisibility(View.GONE);
					// mActionBar.hide();
				}
			}
		});

	}

	public List<String> arrayToList() {
		List<String> list = new ArrayList<String>();

		for (String url : imageUrls) {
			list.add(url);
		}

		return list;
	}

	private class MySimpleGesture extends SimpleOnGestureListener {
		// 按两下的第二下Touch down时触发
		public boolean onDoubleTap(MotionEvent e) {

//			View view = gallery.getSelectedView();
//			if (view instanceof MyImageView) {
//				MyImageView imageView = (MyImageView) view;
//				if (imageView.getScale() > imageView.getMiniZoom()) {
//					imageView.zoomTo(imageView.getMiniZoom());
//				} else {
//					imageView.zoomTo(imageView.getMaxZoom());
//				}
//
//			} else {
//
//			}
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {

			return true;
		}
	}

	public GalleryAdapter getAdapter() {
		return mAdapter;
	}

}
