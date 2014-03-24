package com.childhoodmemories.webgames;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.childhoodmemories.webgames.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class WelcomeActivity extends Activity {
	boolean isFirstIn = false;  
	  
    private static final int GO_HOME = 1000;  
    private static final int GO_GUIDE = 1001;  
    private static final String SHAREDPREFERENCES_NAME = "first_pref";
    private Handler mHandler;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_welcome);
		final View contentView = findViewById(R.id.fullscreen_content);
		
		mHandler = new Handler() { 
	        @Override  
	        public void handleMessage(Message msg) {  
	            switch (msg.what) {  
	            case GO_HOME:  
					Intent login = new Intent(WelcomeActivity.this, LoginActivity.class);
	                startActivity(login);
	                finish();
					overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	                break;  
	            case GO_GUIDE:  
					Intent guide = new Intent(WelcomeActivity.this, GuideActivity.class);
	                startActivity(guide);
	                finish();
					overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	                break;  
	            }  
	            super.handleMessage(msg);  
	        }  
	    };

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// 读取SharedPreferences中需要的数据  
		        // 使用SharedPreferences来记录程序的使用次数  
		        SharedPreferences preferences = getSharedPreferences( SHAREDPREFERENCES_NAME, MODE_PRIVATE);  
		  
		        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值  
		        isFirstIn = preferences.getBoolean("isFirstIn", true);
		  
		        // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面  
		        if (!isFirstIn) {  
		            // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity  
		            mHandler.sendEmptyMessageDelayed(GO_HOME, 3000);  
		        } else {  
		            mHandler.sendEmptyMessageDelayed(GO_GUIDE, 3000);  
		        } 
			}
		});
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
	}
}
