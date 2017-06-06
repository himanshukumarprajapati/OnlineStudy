package regatta.com.oea.quizclasses;
/**
 * Created by rahul.singh on 5/8/2017.
 */

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
import android.widget.Toast;
import com.android.volley.Cache;
import com.android.volley.Request;
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

import regatta.com.oea.MainActivity;
import regatta.com.oea.R;
import regatta.com.oea.app.AppController;
import regatta.com.oea.chapter.FeedItem;

/**
 * Created by rahul.singh on 5/4/2017.
 */
public class QuizActivity extends Activity {
    private static final String TAG = regatta.com.oea.adapter.MainActivity.class.getSimpleName();
    private ListView listView;
    ImageView back;
    private regatta.com.oea.chapter.FeedListAdapter listAdapter;
    private List<regatta.com.oea.chapter.FeedItem> feedItems;
    //private String URL_FEED = "http://api.androidhive.info/feed/feed.json";
    private String URL_FEED ="http://oea.org.in/oeaadmin/api/index.php?access=true&action=get_chapter_quiz&level_id=13&book_id=143&chapter_id=10";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.quizvolley);

        listView = (ListView) findViewById(R.id.list);
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        feedItems = new ArrayList<regatta.com.oea.chapter.FeedItem>();

        listAdapter = new regatta.com.oea.chapter.FeedListAdapter(this, feedItems);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                Toast.makeText(QuizActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        // These two lines not needed,
        // just to get the look of facebook (changing background color & hiding the icon)


        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_FEED);
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
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    Log.d("Responsee",""+response);
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

            JSONObject abc=response.getJSONObject("data");
            JSONObject ab= abc.getJSONObject("13");
           // JSONObject a= ab.getJSONObject("mcq")
            JSONArray feedArray = abc.getJSONArray("mcq");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
                regatta.com.oea.chapter.FeedItem item = new FeedItem();
                item.setName(feedObj.getString("question"));
                // item.setStatus(feedObj.getString("chapter_no"));
                // Image might be null sometimes
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


