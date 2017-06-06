package regatta.com.oea.aboutus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import regatta.com.oea.R;

public class ApiActivity extends AppCompatActivity {
    List<String> listt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);
        listt=new ArrayList<String>();
        ListView listview=(ListView)findViewById(R.id.listview);
        getApi();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listt);
        listview.setAdapter(adapter);



    }

    private void getApi() {
        //Global.feedItems = new ArrayList<CartItem>();

        String URL_CART_LIST = "http://oea.org.in/oeaadmin/api/index.php?access=true&action=View_About";


        //showProgressDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CART_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hideProgressDialog();
                        //Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.d("loginresponse", response.toString());
                        JSONObject jobj = null;
                        try {

                            jobj = new JSONObject(response);
                            boolean status = jobj.getBoolean("status");

                            if (status == true) {

                                JSONArray jsonArray = jobj.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String id=jsonObject.getString("id");
                                    String about_contenent=jsonObject.getString("about_contenent");
                                    Log.d("id",id);
                                    Log.d("about_contenent",about_contenent);
                                    listt.add(about_contenent);

                                }
                            }
                            String displaymsg = jobj.getString("displyMessage");

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("error",error.toString());
                    }
                }) {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);




    }

   /* private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }*/
    }


