package com.childhoodmemories.webgames.test.asynctask;

import java.io.InputStream;
import java.io.OutputStream;

public class DownloadAsyncTaskFileUtils {
	public static void close(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(OutputStream out) {
		if (out != null) {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
