package com.bqt.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

public class StaticLeakActivity extends Activity {
	private static Bitmap bitmap;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImageView imageView = new ImageView(this);
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
		imageView.setImageBitmap(bitmap);
		setContentView(imageView);
	}
}