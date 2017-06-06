package regatta.com.oea.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import regatta.com.oea.MainActivity;
import regatta.com.oea.R;

/**
 * Created by rahul.singh on 4/26/2017.
 */

public class LoginActivity extends Activity {

    LinearLayout btnLogin;
    TextView registernow;
    TextView forgetpassword;
    EditText inputEmail,inputPassword;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activitylogin);
       // linearLayout2 =(LinearLayout) findViewById(R.id.linearLayout2);
        forgetpassword=(TextView) findViewById(R.id.forgetpassword);
        registernow =(TextView) findViewById(R.id.registernow);
        btnLogin = (LinearLayout) findViewById(R.id.linearLayout2);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputEmail.setText("");
        inputPassword.setText("");

        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
                   /* pd.show();*/


                } else {

                   /* AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Login Failed");
                    alertDialog.setMessage("Try Again");
                    alertDialog.setIcon(R.drawable.tick);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    // Showing Alert Message
                    alertDialog.show();*/
                    Toast.makeText(LoginActivity.this,"Enter Your Email And Password",Toast.LENGTH_SHORT).show();
                }
            }

        });

        registernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

        private void checkLogin(final String email, final String password) {
         RequestQueue queue = Volley.newRequestQueue(this);
         //String BaseURL= AppConfig.URL+"access=true&action=user_login&mailId="+inputEmail.getText().toString().trim()+"&password="+inputPassword.getText().toString().trim();
         String BaseURL = "http://oea.org.in/oeaadmin/api/index.php?access=true&action=login&username="+inputEmail.getText().toString().trim()+"&password="+inputPassword.getText().toString().trim();
         Log.e(AppConfig.TAG,AppConfig.TAG1 + BaseURL);
         // prepare the Request
         JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, BaseURL, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                // display response
                Log.e("Response", response.toString());

                try{
                    JSONObject jsonObject=new JSONObject(response.toString());

                    String status=jsonObject.getString("status");

                    if(status.equalsIgnoreCase("true")){
                       // manager.setPreferences(LoginActivity.this, "status1", "1");
                       // String status1=manager.getPreferences(LoginActivity.this,"status1");
                        JSONObject categoryObject = jsonObject.getJSONObject("data");
                        Log.e("categoryObject"," hello "+categoryObject.get("phone"));
                        AppConstantVariables.mailId = categoryObject.get("user_id").toString();

                        for(int i=0;i<categoryObject.length();i++) {
                            String id = categoryObject.optString("user_id");
                            String phone = categoryObject.optString("phone");
                            Log.e("user_id", " user_id " + id);
                            Log.e("phone", "phone" +phone);
                            AppConstantVariables.mailId=categoryObject.getString("email");
                            AppConstantVariables.firstName=categoryObject.getString("username");
                            AppConstantVariables.userId=categoryObject.getString("user_id");
                            AppConstantVariables.contactNumber=categoryObject.getString("phone");
                            Log.d("contact",AppConstantVariables.contactNumber);
                         /*   AppConstantVariables.userId=id;
                            AppConstantVariables.firstName=categoryObject.getString("firstName");
                            AppConstantVariables.merchantId=categoryObject.getString("merchantId");
                            AppConstantVariables.lastName=categoryObject.getString("lastName");
                            AppConstantVariables.contactNumber=categoryObject.getString("contactNumber");
                            AppConstantVariables.organisationName=categoryObject.getString("organisationName");
                            AppConstantVariables.organisationTypeId=categoryObject.getString("organisationTypeId");
                            AppConstantVariables.bankDetailId=categoryObject.getString("bankDetailId");*/
                            Intent intent=new Intent( LoginActivity.this,MainActivity.class);
                            intent.putExtra("user_id",id);
                            //  Intent intent = new Intent(activity2.this, activity1.class);
                            intent.putExtra("message", AppConstantVariables.mailId);
                            intent.putExtra("phone",AppConstantVariables.contactNumber);
                            startActivity(intent);
                            intent.putExtra("message", inputEmail.getText().toString());

                            // startActivity(intent);
                            startActivity(intent);
                        }
                    }else {
                        /*AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                        // Setting Dialog Title
                        alertDialog.setTitle("Enter Valid UserName & Password");
                        // Setting Dialog Message
                        alertDialog.setMessage("Try Again");
                        // Setting Icon to Dialog
                        alertDialog.setIcon(R.drawable.tick);
                        // Setting OK Button
                        alertDialog.setButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                // closed
                                // Toast.makeText(getApplicationContext(), "You clicked", Toast.LENGTH_SHORT)
                                // .show();
                                ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                                pd.cancel();
                            }
                        });
                        // Showing Alert Message
                        alertDialog.show();*/
                        Toast.makeText(LoginActivity.this,"Enter Your Valid Email & Password",Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
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
// add it to the RequestQueue

        queue.add(getRequest);
    }
}