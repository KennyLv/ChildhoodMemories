package com.childhoodmemories.webgames.downloadhelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloader {
	private URL url = null;

	// 该函数返回整形 -1：代表下载文件出错 ;0：代表下载文件成功; 1：代表文件已经存在
	public int download(String urlStr, File file) {
		int downloadResult = -1;

		InputStream inputstream = null;
		OutputStream outputstream = null;
		HttpURLConnection httpUrlConnection = null;

		try {
			url = new URL(urlStr);
			httpUrlConnection = (HttpURLConnection) url.openConnection();
			inputstream = httpUrlConnection.getInputStream();
			
			outputstream = new FileOutputStream(file);
			byte buffer[] = new byte[1024];
			int len = 0;
			while ((len = inputstream.read(buffer)) != -1) {
				outputstream.write(buffer, 0, len);
			}

			outputstream.flush();
			downloadResult = 0;
		} catch (IOException e) {
			e.printStackTrace();
			downloadResult = 1;
		} finally {
			try {
				outputstream.close();
				inputstream.close();
				httpUrlConnection.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return downloadResult;
	}
}
