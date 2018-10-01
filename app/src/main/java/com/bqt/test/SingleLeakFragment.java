package com.bqt.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SingleLeakFragment extends Fragment {
	private ImageView imageView;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		imageView = new ImageView(getContext());
		return imageView;
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		imageView.setImageResource(R.drawable.icon);
		Single.SINGLETON.setImageView(imageView);//单例中引用View同样会导致Activity内存泄漏
	}
}