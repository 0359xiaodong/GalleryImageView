package com.leochin.galleryimageview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Gallery;

@SuppressWarnings("deprecation")
public class PicGallery extends Gallery implements OnTouchListener {

	private GestureDetector gestureScanner;
	private MyImageView imageView;

	public static final int KEY_INVALID = -1;
	private int kEvent = KEY_INVALID; // invalid

	private float v[] = new float[9];
	private final String TAG = "leochin";

	public PicGallery(Context context, AttributeSet attrs) {
		super(context, attrs);

		Log.d(TAG, "onConstruct...");
		this.setOnTouchListener(this);
	}

	private int screenWidth;
	private int screenHeight;

	public void setScreenWidthAndHeight(int w, int h) {

		this.screenHeight = h;
		this.screenWidth = w;
	}

	private float baseValue = 0;
	private float originalScale = 0;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		Log.d(TAG, "onTouch...");
		// 实现双指缩放
		View view = PicGallery.this.getSelectedView();
		if (view instanceof MyImageView) {
			imageView = (MyImageView) view;

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				baseValue = 0;
				originalScale = imageView.getScale();
			}
			if (event.getAction() == MotionEvent.ACTION_MOVE) {

				// 双指被按下
				if (event.getPointerCount() == 2) {
					// Log.d("leochin","onTouch...Action_move");
					float x = event.getX(0) - event.getX(1);
					float y = event.getY(0) - event.getY(1);
					// 直角三角形勾股定理：a^2+b^2=c^2
					// sqrt 求平方根
					float value = (float) Math.sqrt(x * x + y * y);// 计算两点的距离
					if (baseValue == 0) {
						baseValue = value;
					} else {
						float scale = value / baseValue;// 当前两点间的距离除以手指落下时两点间的距离就是需要缩放的比例。
						Log.d("leochin", "onTouch..." + scale * originalScale);
						imageView.zoomTo(originalScale * scale,
								x + event.getX(1), y + event.getY(1));
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {

		Log.d(TAG, "onScroll...");
		// 实现左右滑动的效果
		// Log.d("leochin","onScroll...Action_move");
		View view = PicGallery.this.getSelectedView();
		if (view instanceof MyImageView) {

			float xdistance = calXdistance(e1, e2);
			float min_distance = screenWidth / 4f;
			// float min_distance = mContext.gets/ 4f;

			if (isScrollingLeft(e1, e2) && xdistance > min_distance) {
				kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
			} else if (!isScrollingLeft(e1, e2) && xdistance > min_distance) {
				kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
			}

			imageView = (MyImageView) view;

			Matrix m = imageView.getImageMatrix();
			m.getValues(v);
			// 图片实时的上下左右坐标
			float left, right;
			// 图片的实时宽，高
			float width = imageView.getScale() * imageView.getImageWidth();
			float height = imageView.getScale() * imageView.getImageHeight();

			if ((int) width <= screenWidth
					&& (int) height <= screenHeight)// 如果图片当前大小<屏幕大小，直接处理滑屏事件
			{
				super.onScroll(e1, e2, distanceX, distanceY);
			} else {

				// 放大之后的滑动情况
				left = v[Matrix.MTRANS_X];
				right = left + width;
				Rect r = new Rect();
				imageView.getGlobalVisibleRect(r);

				if (distanceX > 0)// 向左滑动
				{
					if (r.left > 0 || right < screenWidth) {// 判断当前ImageView是否显示完全
						super.onScroll(e1, e2, distanceX, distanceY);

					} else {
						imageView.postTranslate(-distanceX, -distanceY);

					}
				} else if (distanceX < 0)// 向右滑动
				{
					if (r.right < screenWidth || left > 0) {
						super.onScroll(e1, e2, distanceX, distanceY);
					} else {
						imageView.postTranslate(-distanceX, -distanceY);
					}
				}
			}

		} else {
			super.onScroll(e1, e2, distanceX, distanceY);

		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "onTouchEvent...");
		if (gestureScanner != null) {
			gestureScanner.onTouchEvent(event);
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:

			// Log.d("leochin","motionEvent up...");
			// 判断边界是否越界
			View view = PicGallery.this.getSelectedView();
			if (view instanceof MyImageView) {

				if (kEvent != KEY_INVALID) { // 是否切换上一页或下一页
					onKeyDown(kEvent, null);
					kEvent = KEY_INVALID;
				}

				imageView = (MyImageView) view;
				float width = imageView.getScale() * imageView.getImageWidth();
				float height = imageView.getScale()
						* imageView.getImageHeight();

				if ((int) width <= screenWidth
						&& (int) height <= screenHeight)// 如果图片当前大小<屏幕大小，判断边界
				{
					break;
				}
				float v[] = new float[9];
				Matrix m = imageView.getImageMatrix();
				m.getValues(v);
				float top = v[Matrix.MTRANS_Y];
				float bottom = top + height;
				if (top < 0 && bottom < screenHeight) {
					// imageView.postTranslateDur(-top, 200f);
					imageView.postTranslateDur(screenHeight
							- bottom, 200f);
				}
				if (top > 0 && bottom > screenHeight) {
					// imageView.postTranslateDur(PictureViewActivity.screenHeight
					// - bottom, 200f);
					imageView.postTranslateDur(-top, 200f);
				}

				float left = v[Matrix.MTRANS_X];
				float right = left + width;
				if (left < 0 && right < screenWidth) {
					// imageView.postTranslateXDur(-left, 200f);
					imageView.postTranslateXDur(screenWidth
							- right, 200f);
				}
				if (left > 0 && right > screenWidth) {
					// imageView.postTranslateXDur(PictureViewActivity.screenWidth
					// - right, 200f);
					imageView.postTranslateXDur(-left, 200f);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}

	private float calXdistance(MotionEvent e1, MotionEvent e2) {
		return Math.abs(e2.getX() - e1.getX());
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	public void setDetector(GestureDetector dectector) {
		gestureScanner = dectector;
	}

}
