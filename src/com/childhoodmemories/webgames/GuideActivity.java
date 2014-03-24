package com.childhoodmemories.webgames;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.childhoodmemories.webgames.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class GuideActivity extends Activity {

    private static final String SHAREDPREFERENCES_NAME = "first_pref";
	private ViewPager vp;  
    private ViewPagerAdapter vpAdapter;  
    private List<View> views;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
		setContentView(R.layout.activity_guide);

		LayoutInflater inflater = LayoutInflater.from(this);  
		  
        views = new ArrayList<View>();  
        // 初始化引导图片列表  
        views.add(inflater.inflate(R.layout.activity_guide_page_one, null));  
        views.add(inflater.inflate(R.layout.activity_guide_page_two, null)); 

        vpAdapter = new ViewPagerAdapter(views);
        vp = (ViewPager) findViewById(R.id.vPager);  
        vp.setAdapter(vpAdapter);
        //public class GuideActivity extends Activity implements OnPageChangeListener {
        //设定回调调用本页面onPageChanged方法
        //vp.setOnPageChangeListener(this);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);


	}
	
	
	class ViewPagerAdapter extends PagerAdapter{
		private List<View> views;
		public ViewPagerAdapter(List<View> views) {  
	        this.views = views;
	    }  
		
		@Override  
	    public void destroyItem(View arg0, int arg1, Object arg2) {  
	        ((ViewPager) arg0).removeView(views.get(arg1));  
	    }
		
		@Override
		public int getCount() {
			if (views != null) {  
	            return views.size();  
	        }  
	        return 0;  
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return  arg0==arg1;
		}
		@Override  
	    public Object instantiateItem(View arg0, int arg1) {  
	        ((ViewPager) arg0).addView(views.get(arg1), 0);
	        
	        
	        if (arg1 == 1) {
		        findViewById(R.id.button_test).setOnClickListener(new OnClickListener(){
	
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						SharedPreferences preferences = getSharedPreferences(  
					            SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
						Editor editor = preferences.edit();  
						 // 存入数据  
						editor.putBoolean("isFirstIn", false);  
						// 提交修改  
						editor.commit(); 
						
						Intent login = new Intent(GuideActivity.this, LoginActivity.class);
		                startActivity(login);
		                finish();
						overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
					}
		        	
		        });
	        }
	        
	        
	        return views.get(arg1);  
	    } 
	}
}
