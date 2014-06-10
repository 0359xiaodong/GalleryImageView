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

	public String[] getImageUrls() {
		return imageUrls;
	}

	private String[] imageUrls = {
			"http://f.hiphotos.baidu.com/image/pic/item/cb8065380cd791235af13afeaf345982b2b78023.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/738b4710b912c8fca654a13efe039245d7882199.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/c75c10385343fbf25f57e7abb27eca8065388f3b.jpg",
			"http://g.hiphotos.baidu.com/image/pic/item/fd039245d688d43fd1eed3007f1ed21b0ff43bf8.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/78310a55b319ebc4327336118026cffc1e17162a.jpg",
			"http://a.hiphotos.baidu.com/image/pic/item/0823dd54564e92584112a1619e82d158ccbf4e1e.jpg"};

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
		
		
		ft.add(R.id.pic_content, pf,"picFTag");
		//ft.addToBackStack("pic");
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
		
		return super.onOptionsItemSelected(item);
	}
	
	
	public void onBackClick(View view){
		Toast.makeText(this, "back", Toast.LENGTH_LONG).show();
	}

}
