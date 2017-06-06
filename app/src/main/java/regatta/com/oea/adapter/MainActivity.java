package regatta.com.oea.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import regatta.com.oea.AppConstantVariables;
import regatta.com.oea.R;
import regatta.com.oea.app.AppController;
import regatta.com.oea.chapter.Chapter_one;
import regatta.com.oea.newclasses.SubjectActivity;


public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private ListView listView;
	ImageView back;
	private FeedListAdapter listAdapter;
	private List<FeedItem> feedItems;
	//private String URL_FEED = "http://api.androidhive.info/feed/feed.json";
	private String URL_FEED ="http://oea.org.in/oeaadmin/api/index.php?access=true&action=get_books_list_by_class_and_subject_id&subject_id="+AppConstantVariables.idforuse+"&class_id="+ AppConstantVariables.idclass;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.acti_bookdetails);

		listView = (ListView) findViewById(R.id.list);
		back=(ImageView)findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SubjectActivity.class);
				startActivity(intent);
				finish();
			}
		});

		feedItems = new ArrayList<FeedItem>();

		listAdapter = new FeedListAdapter(this, feedItems);
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				Intent intent = new Intent(MainActivity.this, Chapter_one.class);
				startActivity(intent);

				finish();
			}
		});
		
		// These two lines not needed,
		// just to get the look of facebook (changing background color & hiding the icon)


		// We first check for cached request
		Cache cache = AppController.getInstance().getRequestQueue().getCache();
		Entry entry = cache.get(URL_FEED);
		if (entry != null) {
			// fetch the data from cache
			try {
				String data = new String(entry.data, "UTF-8");
				try {
					parseJsonFeed(new JSONObject(data));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		} else {
			// making fresh volley request and getting json
			JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
					URL_FEED, null, new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							VolleyLog.d(TAG, "Response: " + response.toString());
							if (response != null) {
								parseJsonFeed(response);
							}
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							VolleyLog.d(TAG, "Error: " + error.getMessage());
						}
					});

			// Adding request to volley request queue
			AppController.getInstance().addToRequestQueue(jsonReq);
		}

	}

	/**
	 * Parsing json reponse and passing the data to feed view list adapter
	 * */
	private void parseJsonFeed(JSONObject response) {
		try {
			JSONArray feedArray = response.getJSONArray("data");
			for (int i = 0; i < 1; i++) {
				JSONObject feedObj = (JSONObject) feedArray.get(i);
				FeedItem item = new FeedItem();
				item.setName(feedObj.getString("book_name"));
				item.setBookid(feedObj.getString("book_id"));
				String bookidis=feedObj.getString("book_id");
				Log.d("bookidis",""+bookidis);
				AppConstantVariables.bookidis=bookidis;
				// Image might be null sometimes
				String image = feedObj.isNull("image") ? null : feedObj.getString("image");
				item.setImge(image);
				/*item.setStatus(feedObj.getString("status"));*/
				/*item.setProfilePic(feedObj.getString("profilePic"));*/
				/*item.setTimeStamp(feedObj.getString("timeStamp"));*/
				// url might be null sometimes
				/*String feedUrl = feedObj.isNull("url") ? null : feedObj.getString("url");
				item.setUrl(feedUrl);*/
				feedItems.add(item);
			}
			// notify data changes to list adapater
			listAdapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
