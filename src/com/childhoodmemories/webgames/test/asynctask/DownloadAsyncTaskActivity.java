package com.childhoodmemories.webgames.test.asynctask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.childhoodmemories.webgames.R;

public class DownloadAsyncTaskActivity extends Activity {

	ProgressDialog progressDialog;
	DownloadAsyncTaskListAdapter listViewAdapter;
	ListView listView;
	//URLµÿ÷∑
	public static final String URL = "http://cdn.androidcommunity.com/wp-content/uploads/2011/03/Playbook_android_apps.jpg";
	public static final String URL1 ="http://cdn.androidcommunity.com/wp-content/uploads/2011/03/Playbook_android_apps.jpg";   
	public static final String URL2 = "http://cdn.androidcommunity.com/wp-content/uploads/2011/03/Playbook_android_apps.jpg";   
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_async_task);
		
		

		listView = (ListView) findViewById(R.id.imageList);
		DownloadTask task = new DownloadTask(this);
		task.execute(new String[]{URL, URL1, URL2});
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("In progress...");
		progressDialog.setMessage("Loading...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setIndeterminate(false);
		progressDialog.setMax(100);
		progressDialog.setCancelable(true);
		progressDialog.show();
		
	}
	
	
	class DownloadTask extends AsyncTask<String, Integer, List<DownloadAsyncTaskRowItem> > {
		private Activity context;
		List<DownloadAsyncTaskRowItem> rowItems;
		int taskCount;
		
		public DownloadTask(Activity context) {
			this.context = context;
		}
		
		@Override
		protected List<DownloadAsyncTaskRowItem> doInBackground(String... urls) {
			// TODO Auto-generated method stub
			
			
			taskCount = urls.length;
			rowItems = new ArrayList<DownloadAsyncTaskRowItem>();
			Bitmap map = null;
			for (String url : urls) {
				map = downloadImage(url);
				rowItems.add(new DownloadAsyncTaskRowItem(map));
			}
			return rowItems;
		}
		

		protected void onProgressUpdate(Integer... progress) {
			progressDialog.setProgress(progress[0]);
			if (rowItems != null) {
				progressDialog.setMessage("Loading " + (rowItems.size() + 1) + "/" + taskCount);
			}
		}
		
		@Override
		protected void onPostExecute(List<DownloadAsyncTaskRowItem> rowItems) {
			listViewAdapter = new DownloadAsyncTaskListAdapter(context, rowItems);
			listView.setAdapter(listViewAdapter);
			progressDialog.dismiss();
		}
		
		
		
		private Bitmap downloadImage(String urlString) {
			int count = 0;
			Bitmap bitmap = null;
			
			URL url;
			InputStream in = null;
			BufferedOutputStream out = null;
			
			try {
				url = new URL(urlString);
				URLConnection conn = url.openConnection();
				int lengthOfFile = conn.getContentLength();
				
				in = new BufferedInputStream(url.openStream());
				ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
				out = new BufferedOutputStream(dataStream);
				
				byte[] data = new byte[512];
				long total = 0L;
				while ((count = in.read(data)) != -1) {
					total += count;
					//
					publishProgress((int)((total * 100) / lengthOfFile));
					out.write(data, 0, count);
					Thread.sleep(100);
				}
				out.flush();
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 1;
				
				byte[] bytes = dataStream.toByteArray();
				bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return bitmap;
		}
	
	
	}
	

	class DownloadAsyncTaskListAdapter extends BaseAdapter{
	
		Context mContext;
		List<DownloadAsyncTaskRowItem> rowItems;
		
		public DownloadAsyncTaskListAdapter(Context context, List<DownloadAsyncTaskRowItem> items) {
			this.mContext = context;
			this.rowItems = items;
		}
		
		@Override
		public int getCount() {
			return rowItems.size();
		}
	
		@Override
		public Object getItem(int position) {
			return rowItems.get(position);
		}
	
		@Override
		public long getItemId(int position) {
			return 0;
		}
	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder = null;
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.activity_download_async_task_item, null);
				holder = new ViewHolder();
				holder.imageView = (ImageView) convertView.findViewById(R.id.thumbnail);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			DownloadAsyncTaskRowItem rowItem = (DownloadAsyncTaskRowItem) rowItems.get(position);
			holder.imageView.setImageBitmap(rowItem.getBitmap());
			return convertView;
		}
	}
	
	class ViewHolder {
		ImageView imageView;
	}
}
