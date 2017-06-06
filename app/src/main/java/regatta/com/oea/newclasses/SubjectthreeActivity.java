package regatta.com.oea.newclasses;

/**
 * Created by rahul.singh on 5/9/2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import regatta.com.oea.R;

/**
 * Created by rahul.singh on 5/5/2017.
 */

/**
 * Created by rahul.singh on 5/5/2017.
 */

public class SubjectthreeActivity extends AppCompatActivity {
    //Web api url
    //public static final String DATA_URL = "http://www.simplifiedcodingreaders.16mb.com/superheroes.php";
    public static final String DATA_URL="http://oea.org.in/oeaadmin/api/index.php?access=true&action=get_books_list_by_class_and_subject_id&subject_id=7&class_id=3";
    //Tag values to read from json
    public static final String TAG_IMAGE_URL = "image";
    public static final String TAG_NAME = "book_name";
    public static final  String TAG_SUBID ="subject_id";
    //GridView Object
    private GridView gridView;
    ImageView back;
    //ArrayList for Storing image urls and titles
    private ArrayList<String> images;
    private ArrayList<String> names;
    private ArrayList<String> subid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gridview);
        gridView = (GridView) findViewById(R.id.gridView);
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubjectthreeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        images = new ArrayList<>();
        names = new ArrayList<>();
        subid = new ArrayList<>();
        //Calling the getData method
        getData();
    }
    private void getData(){
        //Showing a progress dialog while our app fetches the data from url
        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...","Fetching data...",false,false);

        //Creating a json array request to get the json from our api
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, DATA_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                //Displaying our grid
                showGrid(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding our request to the queue
        requestQueue.add(jsonReq);

    }
    private void showGrid(JSONArray jsonArray){
        //Looping through all the elements of json array
        for(int i = 0; i<jsonArray.length(); i++){
            //Creating a json object of the current index
            JSONObject obj = null;
            try {
                //getting json object from current index
                obj = jsonArray.getJSONObject(i);
                //getting image url and title from json object
                images.add(obj.getString(TAG_IMAGE_URL));
                names.add(obj.getString(TAG_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Creating GridViewAdapter Object
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this,images,names,subid);
        //Adding adapter to gridview
        gridView.setAdapter(gridViewAdapter);
    }

    private void showGrid(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("data");
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
              /*  FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));*/
                images.add(feedObj.getString(TAG_IMAGE_URL));
                names.add(feedObj.getString(TAG_NAME));
                GridViewAdapter gridViewAdapter = new GridViewAdapter(this,images,names,subid);

                gridView.setAdapter(gridViewAdapter);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        // Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                        if (position==0){
                            Intent intent= new Intent(SubjectthreeActivity.this, regatta.com.oea.adapter.MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(SubjectthreeActivity.this,"Wrong Selection",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            // notify data changes to list adapater
            // listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


