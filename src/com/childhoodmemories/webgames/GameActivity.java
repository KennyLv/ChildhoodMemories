package com.childhoodmemories.webgames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.childhoodmemories.webgames.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class GameActivity extends Activity {
	private Handler mHandler;
	private WebView wView;
	private WebSettings wSet;
	private String game_folder; 
	private String game_startrul; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_game);

		Intent intent = getIntent();
		game_folder  = String.valueOf(intent.getIntExtra("game_folder",1));
		game_startrul  = String.valueOf(intent.getIntExtra("game_startrul",1));
        

		wView = (WebView)findViewById(R.id.game_webview);  
		mHandler = new Handler();

        wSet = wView.getSettings();
        wSet.setJavaScriptEnabled(true);
		
        wView.requestFocus();
        wView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        
        wView.loadUrl("file:///android_asset/games/" + game_folder + "/" + game_startrul); 
        wView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
            	
            };
            
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return false;
            };
        });
		
	    wView.setOnKeyListener(new OnKeyListener() {
	        @Override
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	            if ((keyCode == KeyEvent.KEYCODE_BACK) && wView != null && wView.canGoBack()) {
	            	wView.goBack();
	                return true;
	            }
	            return false;
	        }
	    });
	    
	    wView.addJavascriptInterface(new Object() { 
	        @SuppressWarnings("unused") 
	        public void getPosition(final String locX, final String locY) {
	        	mHandler.post(new Runnable() { 
	                public void run() {
	                	wView.loadUrl("javascript:notifyFromAndroid(" + locX + "," + locY + ")");
	                } 
	            }); 
	        }
	        
	        
	        
	        
	    },"androidInterface");
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

	}

}
