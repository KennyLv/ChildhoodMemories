package com.childhoodmemories.webgames;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.util.EncodingUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.childhoodmemories.webgames.downloadhelper.HttpDownloader;
import com.childhoodmemories.webgames.util.ZipUtil;

public class GameSquareActivity extends Activity {
	private File path;
	private String rootPath;
	private String baseUrl = "http://10.20.71.62:8056/";
	private GridView gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamesquare);

		path = getFilesDir();
		rootPath = path.getPath();
		
		gridview = (GridView) findViewById(R.id.games_gridview);

		// TODO : check if my game is empty
		// TODO : if my game id empty, load game square, and reset option menu

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_square, menu);

		// TODO :the title of action_switchs should be changed between
		// action_mygames and action_gamesquare

		return true;
	}
	
	
	private  Runnable downloadAppRunnable = new Runnable() {
        public void run() {
			try {
	
				HttpDownloader httpDownloader = new HttpDownloader();
	
				String downloadPath = rootPath + "/downloads/";
				File downloadDir = new File(downloadPath);
				if (!downloadDir.exists()) {
					downloadDir.mkdir();
				}
	
				File downloadFile = new File(downloadPath + "test.zip");
				if (downloadFile.exists()) {
					downloadFile.delete();
				}
				downloadFile.createNewFile();
				
				int result = httpDownloader.download(baseUrl + "test.zip", downloadFile);
	
				if (result == 0) {
					Log.d("========", "succeed!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };
	
    private JSONObject getAppInfo(File downloadFile){
    	JSONObject myj = null;
		File releaseDir = new File(rootPath + "/games/");
		if (!releaseDir.exists()) {
			releaseDir.mkdir();
		}

		ZipUtil.unzip(downloadFile, releaseDir);
		downloadFile.delete();

		String res = "";
		try {

			File myf = new File(rootPath + "/games/game.txt");

			FileInputStream fin = new FileInputStream(myf);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();
			Log.d("oooooo2ooooo", res);
	
			myj = new JSONObject(res);
	
			Log.d("oooooo3ooooo", myj.getString("displayName"));
			Log.d("oooooo3ooooo", myj.getString("launcherIcon"));
			Log.d("oooooo3ooooo", myj.getString("launcherUrl"));
			Log.d("oooooo3ooooo", myj.getString("version"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myj;
    	
    }
    
    
    private void updateGridView(){
		
		ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();
		
		for(int i = 1;i < 2;i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", getImage("http://10.20.71.62:8056/icon.png"));
			map.put("ItemText","Game "+i); 
			meumList.add(map);
		}
		
		SimpleAdapter saItem = new SimpleAdapter(GameSquareActivity.this, meumList,R.layout.activity_gamesquare_gamelauncher,
				new String[]{"ItemImage","ItemText"}, new int[]{R.id.games_icon_img,R.id.games_icon_txt});
		gridview.setAdapter(saItem);
		gridview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				//int index=arg2+1;
				
				//HashMap<String, Object> item=(HashMap<String, Object>)arg0.getItemAtPosition(arg2);
				//(String)item.get("ItemText");
				
				Intent i = new Intent(GameSquareActivity.this,GameActivity.class);
				//i.putExtra("releaseDir", releaseDir.getAbsolutePath());
				startActivity(i);
				finish();
			}
		});
    	
    	
    }
    
	public static Bitmap getImage(String address){  
        //通过代码 模拟器浏览器访问图片的流程   
		

		Bitmap bitmap = null;
		try {
			URL url = new URL(address);
	        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setConnectTimeout(5000);  
	        //获取服务器返回回来的流   
	        InputStream is = conn.getInputStream();  
	        byte[] imagebytes =  new byte[is.available()];
	        is.read(imagebytes);
	        bitmap = BitmapFactory.decodeByteArray(imagebytes, 0, imagebytes.length);  
	        is.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return bitmap;
    }  

}
