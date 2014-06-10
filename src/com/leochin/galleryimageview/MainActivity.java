package com.leochin.galleryimageview;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {

	// 屏幕宽度
	public static int screenWidth;
	// 屏幕高度
	public static int screenHeight;
	
	private FragmentManager mFrgementMag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		//获取屏幕大小
		Point outSize = new Point();
		getWindow().getWindowManager().getDefaultDisplay().getSize(outSize);
		screenWidth = outSize.x;
		screenHeight = outSize.y;
		Log.d("leochin",screenWidth+","+screenHeight);
		
		initFragment();	
	}
	
	private void initFragment(){

		mFrgementMag = getSupportFragmentManager();
		FragmentTransaction ft = mFrgementMag.beginTransaction();
		PictureViewFragment pf = new PictureViewFragment();
		
		ft.add(R.id.pic_content, pf,"pic");
		ft.commit();
	}

}
