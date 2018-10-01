package com.bqt.test;

import android.annotation.SuppressLint;
import android.widget.ImageView;

public enum Single {
	@SuppressLint("StaticFieldLeak")
	SINGLETON; //定义一个枚举的元素，它就代表了Single的一个实例
	private ImageView imageView;
	
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
}