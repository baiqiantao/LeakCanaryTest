package com.bqt.test;

import com.squareup.leakcanary.HeapDumper;

import java.io.File;

public class TogglableHeapDumper implements HeapDumper {
	private final HeapDumper defaultDumper;
	private boolean enabled = true;
	
	public TogglableHeapDumper(HeapDumper defaultDumper) {
		this.defaultDumper = defaultDumper;

	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public File dumpHeap() {
		return enabled ? defaultDumper.dumpHeap() : HeapDumper.RETRY_LATER;
	}
}