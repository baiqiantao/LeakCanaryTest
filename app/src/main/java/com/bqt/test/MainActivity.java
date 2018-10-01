package com.bqt.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.Arrays;

public class MainActivity extends FragmentActivity implements AdapterView.OnItemClickListener {
	private FrameLayout frameLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListView listView = new ListView(this);
		String[] array = {"静态成员导致的内存泄漏",
				"单例导致的内存泄漏：Fragment",
				"禁用 LeakCanary",
				"",};
		listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Arrays.asList(array)));
		listView.setOnItemClickListener(this);
		frameLayout = new FrameLayout(this);
		frameLayout.setId(R.id.fragment_id);
		listView.addFooterView(frameLayout);
		setContentView(listView);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
			case 0:
				startActivity(new Intent(this, StaticLeakActivity.class));
				break;
			case 1:
				getSupportFragmentManager().beginTransaction()
						.add(frameLayout.getId(), new SingleLeakFragment(), "SingleLeakFragment")
						.commit();
				break;
			case 2:
				MyApplication.app().getHeapDumper().setEnabled(false);
				break;
			default:
				break;
		}
	}
}