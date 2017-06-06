package regatta.com.oea.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.Timer;
import java.util.TimerTask;

import regatta.com.oea.AppConfig;
import regatta.com.oea.AppConstantVariables;
import regatta.com.oea.R;


/**
 * Created by rahul.singh on 4/26/2017.
 */

public class SignupActivity extends Activity {

    LinearLayout linearLayout2;
    ImageView back;
    EditText etmobile ,etusername,etpassword,etemail,etsponserid;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sign_up);
        linearLayout2 =(LinearLayout) findViewById(R.id.linearLayout2);
        etmobile = (EditText) findViewById(R.id.etmobile);
        etusername = (EditText) findViewById(R.id.etusername);
        etpassword = (EditText) findViewById(R.id.etpassword);
        etemail = (EditText) findViewById(R.id.etemail);
        etsponserid = (EditText) findViewById(R.id.etsponserid);
        back=(ImageView)findViewById(R.id.back);



        linearLayout2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String  name= etusername.getText().toString().trim();
                String lastname = etsponserid.getText().toString().trim();
                String email = etemail.getText().toString().trim();
                String password = etpassword.getText().toString().trim();
                String mobileno = etmobile.getText().toString().trim();


                if (etpassword.getText().length()<6 && !email.isEmpty() && !name.isEmpty() && mobileno.length()>=10)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Enter Valid Password");
                    builder.setMessage("Please set a Password minimum of 6 Character, Combination of Text,Number and Special Character");
                    builder.setCancelable(true);

                    final AlertDialog dlg = builder.create();

                    dlg.show();

                    final Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        public void run() {
                            dlg.dismiss();
                            t.cancel();
                        }
                    }, 3000);
                }
                else if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && mobileno.length()>=10) {
                    checkLogin();
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
                    alertDialog.setTitle("Try Again!");
                    alertDialog.setMessage("Fill The Form Completely,Check Mobile Number");
                    alertDialog.setIcon(R.drawable.backicon);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                }
            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkLogin() {

        RequestQueue queue = Volley.newRequestQueue(this);

        //String BaseURL = AppConfig.URL+"access=true&action=manage_user&type=Add&firstName="+editText.getText().toString().trim()+"&lastName="+lastnamee.getText().toString().trim()+"&mailId="+editText2.getText().toString().trim()+"&contactNumber="+moblieno.getText().toString().trim()+"&password="+editText3.getText().toString().trim()+"&orgnisationName="+organisationNamee.getText().toString().trim();
        String BaseURL= "http://oea.org.in/oeaadmin/api/index.php?access=true&action=signup&username="+etusername.getText().toString().trim()+"&password="+etpassword.getText().toString().trim()+"&email="+etemail.getText().toString().trim()+"&phone="+etmobile.getText().toString().trim();

        Log.e(AppConfig.TAG,AppConfig.TAG1 + BaseURL);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, BaseURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // display response
                Log.e("Response", response.toString());

                try{
                    JSONObject jsonObject=new JSONObject(response.toString());

                    String status=jsonObject.getString("status");

                    if (status.equals("true")){

                        Log.e(status,status);

                        //Toast.makeText(SignupActivity.this,"Successfully Register,Activate From Mail Link", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent( SignupActivity.this,LoginActivity.class);
                        startActivity(intent);
                        JSONObject categoryObject = jsonObject.getJSONObject("data");

                        Log.e("categoryObject"," hello "+categoryObject);

                        String id = categoryObject.optString("id");
                        Log.e(AppConstantVariables.TAG, " id " + id);

                        AppConstantVariables.userId=id;
                        etmobile.setText("");
                        etemail.setText("");
                        etsponserid.setText("");
                        etpassword.setText("");
                        etusername.setText("");
                        //organisationNamee.setText("");
                     /*   Intent intent=new Intent( SignupActivity.this,LoginActivity.class);
                        startActivity(intent);*/
                    }
                    else {

                        Toast.makeText(SignupActivity.this,"User Registered Already,Try To Change Mobile Number", Toast.LENGTH_LONG).show();
                    }

                }
                catch (Exception e){
                    Log.e("exception", e.toString());
                }
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
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        getRequest.setRetryPolicy(policy);
        queue.add(getRequest);
    }


}