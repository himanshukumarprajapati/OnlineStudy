package regatta.com.oea.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import regatta.com.oea.AppConfig;
import regatta.com.oea.AppConstantVariables;
import regatta.com.oea.R;

/**
 * Created by rahul.singh on 5/5/2017.
 */

public class QuizActivity extends Activity {
    private static String  returnResponce;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.quiz);
        RequestQueue queue = Volley.newRequestQueue(QuizActivity.this);
        //String BaseURL = AppConfig.URL+"access=true&action=forgot_password&mailId="+mailid.getText().toString().trim();
        String BaseURL ="http://oea.org.in/oeaadmin/api/index.php?access=true&action=get_level_list_for_quiz";
        Log.e(AppConfig.TAG, AppConfig.TAG1 + BaseURL);
// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, BaseURL, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response", response.toString());
                returnResponce=response.toString();
                Log.e(AppConstantVariables.TAG,"Response "+ returnResponce);
                Toast.makeText(QuizActivity.this,""+returnResponce,Toast.LENGTH_SHORT).show();
                return;
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error.Response", error.toString());
                    }
                }
        );
// add it to the RequestQueue
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        getRequest.setRetryPolicy(policy);
        queue.add(getRequest);
}
}