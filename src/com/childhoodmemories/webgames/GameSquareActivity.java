package com.childhoodmemories.webgames;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;

public class GameSquareActivity extends Activity {
	private List<String> lstFile = new ArrayList<String>();  //结果 List
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamesquare);
		

		//TODO : check if my game is empty
		//TODO : if my game id empty, load game square, and reset option menu
		
		GridView gridview = (GridView) findViewById(R.id.games_gridview);
		
		File[] files = new File("file:///android_asset/games").listFiles();
		

    	Log.d("-=-=-=-=-=-=-=-",String.valueOf(files.length));
		
	    for (int i = 0; i < files.length; i++)
	    {
	        File f = files[i];
	        if (f.isDirectory() && f.getPath().indexOf("/.") == -1){  //忽略点文件（隐藏文件/文件夹）
	        	Log.d("-=-=-=-=-=-=-=-",f.getPath());
	        	lstFile.add(f.getPath());
	        }
	    }
		/*
		ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();
        for(int i = 1;i < 10;i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.ic_launcher);
            map.put("ItemText", "Game "+i);
            meumList.add(map);
        }
        SimpleAdapter saItem = new SimpleAdapter(this,
                  meumList, //数据源
                  R.layout.activity_game_icon, //xml实现
                  new String[]{"ItemImage","ItemText"}, //对应map的Key
                  new int[]{R.id.games_icon_img,R.id.games_icon_txt});  //对应R的Id
        
        gridview.setAdapter(saItem);//添加Item到网格中
        gridview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(
					AdapterView<?> arg0, //The AdapterView where the click happened 
					View arg1, //The view within the AdapterView that was clicked  
					int arg2, //The position of the view in the adapter 
					long arg3 //The row id of the item that was clicked  
				) {
				int index=arg2+1;//id是从0开始的，所以需要+1
                //Toast.makeText(getApplicationContext(), "你按下了选项："+index, 0).show();//Toast用于向用户显示一些帮助/提示
                

				Intent i = new Intent(GamesActivity.this, GameActivity.class);
				i.putExtra("game_folder", index);
				i.putExtra("game_startrul", index);
                startActivity(i);
				finish();
				
                //HashMap<String, Object> item=(HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                //setTitle((String)item.get("ItemText"));  //显示所选Item的ItemText  
			}
        });
		
		
		*/
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_square, menu);
		
		//TODO :the title of action_switchs should be changed between action_mygames and action_gamesquare
		
		return true;
	}
	
	
	private void getAssestsFileList(String path){
		

	}
	
}
