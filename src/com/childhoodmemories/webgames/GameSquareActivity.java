package com.childhoodmemories.webgames;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.childhoodmemories.webgames.downloadhelper.HttpDownloader;
import com.childhoodmemories.webgames.util.JsonUtil;
import com.childhoodmemories.webgames.util.ZipUtil;

public class GameSquareActivity extends Activity {
	private List<String> lstFile = new ArrayList<String>(); // 结果 List
	private Context ctxDealFile;
	private File path;
	private String rootPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamesquare);
		
		path = getFilesDir();
		rootPath = path.getPath();
		
		// TODO : check if my game is empty
		// TODO : if my game id empty, load game square, and reset option menu
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					HttpDownloader httpDownloader = new HttpDownloader();
					
					String downloadPath = rootPath + "/downloads/";
					File downloadDir = new File(downloadPath);
					if(!downloadDir.exists()){
						downloadDir.mkdir();
					}
					
					File downloadFile = new File(downloadPath + "test.zip");
					if(downloadFile.exists()){
						downloadFile.delete();
					}
					downloadFile.createNewFile();
					
					Log.d("========",downloadFile.getPath());
					
					int result = httpDownloader.download( "http://10.20.71.62:8056/test.zip", downloadFile);
					
					if(result == 0){
						Log.d("========","succeed!");
						
						File releaseDir = new File(rootPath + "/games/");
						if(!releaseDir.exists()){
							releaseDir.mkdir();
						}
						
						
						ZipUtil.unzip(downloadFile, releaseDir);
						
						
						 File[] files = releaseDir.listFiles();
						 if(null == files){ //没有权限 
							 return;
						 }
						 for (int i = 0; i < files.length; i++) {
							Log.d("ooooooooooo" , files[i].getAbsolutePath());
						 }

						 
						 /*
							String jsonstring = "";
							
							 try{   
						         FileInputStream fin = openFileInput();
						         int length = fin.available();   
						         byte [] buffer = new byte[length];   
						         fin.read(buffer);       
						         jsonstring = EncodingUtils.getString(buffer, "UTF-8");   
						         fin.close();       
						     }   
						     catch(Exception e){   
						         e.printStackTrace();   
						     }   
								Log.d("========",jsonstring);
							
							JSONObject appInfo = new JSONObject(jsonstring);
							
							Log.d("========",appInfo.getString("displayName"));
							Log.d("========",appInfo.getString("launcherIcon"));
							Log.d("========",appInfo.getString("launcherUrl"));
							Log.d("========",appInfo.getString("version"));
						 
						 */
						 
							Intent i = new Intent(GameSquareActivity.this, GameActivity.class);
							i.putExtra("releaseDir", releaseDir.getAbsolutePath());
							startActivity(i);
							finish();
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

		/*
		 * 
		 * try { String[] tfiles =
		 * getAssets().list("file:///android_asset/games");
		 * Log.d("-=-=-=-=-=-=-=-",String.valueOf(tfiles.length));
		 * 
		 * for (int i = 0; i < tfiles.length; i++) {
		 * Log.d("-=-=-=-=-=-=-=-",tfiles[i]); }
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		/*
		 * 
		 * GridView gridview = (GridView) findViewById(R.id.games_gridview);
		 * 
		 * File[] files = new File("file:///android_asset/games").listFiles();
		 * if(null == files){ //没有权限 return; }
		 * Log.d("-=-=-=-=-=-=-=-",String.valueOf(files.length));
		 * 
		 * for (int i = 0; i < files.length; i++) { File f = files[i]; if
		 * (f.isDirectory() && f.getPath().indexOf("/.") == -1){
		 * //忽略点文件（隐藏文件/文件夹） Log.d("-=-=-=-=-=-=-=-",f.getPath());
		 * lstFile.add(f.getPath()); } }
		 * 
		 * 
		 * ArrayList<HashMap<String, Object>> meumList = new
		 * ArrayList<HashMap<String, Object>>(); for(int i = 1;i < 10;i++) {
		 * HashMap<String, Object> map = new HashMap<String, Object>();
		 * map.put("ItemImage", R.drawable.ic_launcher); map.put("ItemText",
		 * "Game "+i); meumList.add(map); } SimpleAdapter saItem = new
		 * SimpleAdapter(this, meumList, //数据源 R.layout.activity_game_icon,
		 * //xml实现 new String[]{"ItemImage","ItemText"}, //对应map的Key new
		 * int[]{R.id.games_icon_img,R.id.games_icon_txt}); //对应R的Id
		 * 
		 * gridview.setAdapter(saItem);//添加Item到网格中
		 * gridview.setOnItemClickListener(new OnItemClickListener(){
		 * 
		 * @Override public void onItemClick( AdapterView<?> arg0, //The
		 * AdapterView where the click happened View arg1, //The view within the
		 * AdapterView that was clicked int arg2, //The position of the view in
		 * the adapter long arg3 //The row id of the item that was clicked ) {
		 * int index=arg2+1;//id是从0开始的，所以需要+1
		 * //Toast.makeText(getApplicationContext(), "你按下了选项："+index,
		 * 0).show();//Toast用于向用户显示一些帮助/提示
		 * 
		 * 
		 * Intent i = new Intent(GamesActivity.this, GameActivity.class);
		 * i.putExtra("game_folder", index); i.putExtra("game_startrul", index);
		 * startActivity(i); finish();
		 * 
		 * //HashMap<String, Object> item=(HashMap<String, Object>)
		 * arg0.getItemAtPosition(arg2);
		 * //setTitle((String)item.get("ItemText")); //显示所选Item的ItemText } });
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_square, menu);

		// TODO :the title of action_switchs should be changed between
		// action_mygames and action_gamesquare

		return true;
	}

	private void getAssestsFileList(String path) {

	}

}
