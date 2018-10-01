package com.bqt.test;

import android.app.Application;
import android.util.Log;

import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.AndroidHeapDumper;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.LeakDirectoryProvider;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.leakcanary.internal.LeakCanaryInternals;

import java.util.concurrent.TimeUnit;

public class MyApplication extends Application {
	private RefWatcher refWatcher;
	private static MyApplication app;
	private TogglableHeapDumper heapDumper;
	
	@Override
	public void onCreate() {
		super.onCreate();
		if (LeakCanary.isInAnalyzerProcess(this)) {
			Log.i("bqt", "此进程是专用于LeakCanary进行堆分析用的。您不应该在此进程中初始化您的应用。");
			return;
		}
		
		refWatcher = LeakCanary.refWatcher(this)
				.watchActivities(true)  //默认为true，会监视所有Activity，你可以设置为false然后再指定要监测的Activity
				.watchFragments(true) //默认为true，会监视 native Fragment，如果添加了support依赖，则也会监视support中的Fragment
				.watchDelay(1, TimeUnit.SECONDS) //设置应该等待多长时间，直到它检查跟踪对象是否已被垃圾回收
				.maxStoredHeapDumps(7) //设置LeakCanary最多可以保存的 heap dumps 个数，默认为7
				.excludedRefs(getExcludedRefs()) //忽略特定的引用，这个垃圾东西设置后总是不生效
				.heapDumper(getHeapDumper()) //在运行时开启和关闭LeakCanary
				//.listenerServiceClass() //可以更改默认行为以将 leak trace 和 heap dump 上载到您选择的服务器。
				.buildAndInstall();
		app = this;
	}
	
	private ExcludedRefs getExcludedRefs() {
		return AndroidExcludedRefs.createAppDefaults()//经过大量测试，我感觉TMD完全忽略不了Activity和Fragment中内存泄漏
				.instanceField("com.bqt.test.Single", "imageView") //类名，字段名
				.staticField("com.bqt.test.StaticLeakActivity", "bitmap") //类名，静态字段名
				.clazz("com.bqt.test.StaticLeakActivity") //忽略提供的类名的所有子类的所有字段和静态字段
				.thread("Thread-10086") //忽略指定的线程，一般主线程名为【main】，子线程名为【Thread-整数】
				.build(); //忽略的引用如果又通过watch手动监测了，则仍会监测其内存泄漏情况
	}
	
	public static MyApplication app() {
		return app;
	}
	
	public RefWatcher getRefWatcher() {
		return refWatcher;
	}
	
	public TogglableHeapDumper getHeapDumper() {
		if (heapDumper == null) {
			LeakDirectoryProvider leakDirectoryProvider = LeakCanaryInternals.getLeakDirectoryProvider(this);
			AndroidHeapDumper defaultDumper = new AndroidHeapDumper(this, leakDirectoryProvider);
			heapDumper = new TogglableHeapDumper(defaultDumper);
		}
		return heapDumper;
	}
}