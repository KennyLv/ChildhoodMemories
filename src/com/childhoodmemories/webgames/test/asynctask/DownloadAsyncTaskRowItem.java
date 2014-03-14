package com.childhoodmemories.webgames.test.asynctask;

import android.graphics.Bitmap;

public class DownloadAsyncTaskRowItem {
private Bitmap bitmapImage;
	
	public DownloadAsyncTaskRowItem(Bitmap bitmap) {
		this.bitmapImage = bitmap;
	}
	
	public Bitmap getBitmap() {
		return bitmapImage;
	}
	
	public void setBitmap(Bitmap bitmap) {
		this.bitmapImage = bitmap;
	}
}
