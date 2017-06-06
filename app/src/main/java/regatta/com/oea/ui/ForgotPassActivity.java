package regatta.com.oea.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import regatta.com.oea.AppConfig;
import regatta.com.oea.AppConstantVariables;
import regatta.com.oea.R;

/**
 * Created by rahul.singh on 4/27/2017.
 */

/**
 * Created by rahul.singh on 12/29/2016.
 */
public class ForgotPassActivity extends Activity {
    EditText mailid;
    ImageView back;
    Button submit,btncancel;
    private static String  returnResponce;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        mailid = (EditText) findViewById(R.id.mailid);
        //mailid.setText(AppConstantVariables.mailId);
        back = (ImageView) findViewById(R.id.back);
        Log.d("con",""+AppConstantVariables.contactNumber);
        submit=(Button)findViewById(R.id.btnsend);
        btncancel=(Button)findViewById(R.id.btncancel);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mailid.getText().toString().equals("")) {
                    new SendDataToServer().execute();

                    Toast.makeText(ForgotPassActivity.this, "Your password has been sent to your Email-Id", Toast.LENGTH_LONG).show();
               /* Intent intent = new Intent(ForgettenPassword.this,LoginActivity.class);
                startActivity(intent);*/
                }
                else {
                    Toast.makeText(ForgotPassActivity.this,"Enter Mail Id",Toast.LENGTH_SHORT).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private class SendDataToServer extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog= new ProgressDialog (ForgotPassActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            checkLogin();
            return returnResponce;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            super.onPostExecute(s);
            Log.e(AppConstantVariables.TAG," INdoin " +s);
            Log.d("con",""+AppConstantVariables.contactNumber);

        }
    }
    private void checkLogin() {
        RequestQueue queue = Volley.newRequestQueue(this);
       // String BaseURL = AppConfig.URL+"access=true&action=forgot_password&mailId="+mailid.getText().toString().trim();
        String BaseURL= "http://oea.org.in/oeaadmin/api/index.php?access=true&action=forget_password&phone="+mailid.getText().toString().toString();
        Log.e(AppConfig.TAG, AppConfig.TAG1 + BaseURL);
        Log.d("con",""+AppConstantVariables.contactNumber);
// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, BaseURL, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response", response.toString());
                returnResponce=response.toString();
                Log.e(AppConstantVariables.TAG,"Response "+ returnResponce);

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
        queue.add(getRequest);

    }
}