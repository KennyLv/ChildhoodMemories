package com.childhoodmemories.webgames.test.message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.childhoodmemories.webgames.R;

public class DownloadProgressBarActivity extends Activity {
	//private static final String LOG_TAG="Download_Progressbar";
    String imgHttp1="http://cdn.androidcommunity.com/wp-content/uploads/2011/03/Playbook_android_apps.jpg";
    private TextView txt;
    private Button downImg;
    private ImageView imgView;
    private ProgressBar progressBar;
    private Bitmap bitmap;
    private static final int LOADING=1;
    private static final int END=2;
    int maxSize=0;
    int nowSize=0;
    Handler handler;
    //private static final String SDCARD="/sdcard/";
    //private String fileName="networkimg1.png";
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		txt=(TextView)findViewById(R.id.txt);
	    downImg=(Button)findViewById(R.id.downImg);
	    downImg.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				//首先清空图片和进度条
				if(null!=bitmap){
					imgView.setImageBitmap(null);
					nowSize=0;
					progressBar.setProgress(0);
					txt.setText("即将下载......");
				}
				//1、显示进度条
				progressBar.setVisibility(View.VISIBLE);
				//2、开始下载
				new MyThread(imgHttp1).start();

			}

	    });
	    imgView=(ImageView)findViewById(R.id.imgView);
	    progressBar=(ProgressBar)findViewById(R.id.progressBar);
	    //progressBar.setVisibility(View.GONE);
		
	    handler = new Handler(){
	    	public void handleMessage(Message msg) {
	    		super.handleMessage(msg);
	    		if(null != progressBar){
		    		//设置进度条最大值
		    		progressBar.setMax(maxSize);
		    		//当前已经下载的值
		    		nowSize+=msg.getData().getInt("loadingSize");
		    		//设置进度条的当前进度值
		    		progressBar.setProgress(nowSize);
	
		    		if(msg.what==LOADING){
			    		//显示已经下载的值
			    		txt.setText("已下载："+(nowSize*100)/maxSize+"%");
			    		Log.e("Download_Progressbar", "正在下载："+nowSize);
		    		}
	
		    		if(msg.what==END){
			    		//下载完成后隐藏进度条
			    		progressBar.setVisibility(View.INVISIBLE);
			    		//显示图片
			    		imgView.setImageBitmap(bitmap);
			    		//将图片保存到sdcard中
			    		//saveImg(SDCARD+fileName,bitmap);
			    		//结束当前线程
			    		Thread.currentThread().interrupt();
		    		}
	    		}
	    	}
	    };
	}
	
	private class MyThread extends Thread{
		String httpImg;
		public MyThread(String _httpImg) {
			// TODO Auto-generated constructor stub
			this.httpImg = _httpImg;
		}
		
		@Override
		public void run() {
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			try {
				URL url=new URL(httpImg);
				HttpURLConnection con=(HttpURLConnection)url.openConnection();
				con.setDoInput(true);
				con.connect();
				InputStream is=con.getInputStream();
	
				//获取文件的大小
				maxSize=con.getContentLength();
				byte []buffer=new byte[1024];
				int len=-1;
				while((len=is.read(buffer))!=-1){
					bos.write(buffer,0,len);
					bos.flush();
					//发送消息
					Message msg=new Message();
					msg.what=LOADING;
					Bundle bundle=new Bundle();
					bundle.putInt("loadingSize", len);
					msg.setData(bundle);
					//Thread.sleep(100);
					handler.sendMessage(msg);
				}

				//关闭输入流	
				is.close();
				//关闭连接
				con.disconnect();	
				byte []imgBytes=bos.toByteArray();
				bitmap=BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
				//下载结束后发送消息
				Message msg=new Message();
				msg.what=END;
				handler.sendMessage(msg);
			} catch (MalformedURLException e) {
				Log.e("Download_Progressbar", "MalformedURLException");
				e.printStackTrace();
			} catch (IOException e) {
				Log.e("Download_Progressbar", "IOException");
				e.printStackTrace();
			}
		}
	}
}
