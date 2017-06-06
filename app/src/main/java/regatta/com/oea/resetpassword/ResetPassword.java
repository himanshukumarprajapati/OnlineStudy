package regatta.com.oea.resetpassword;

/**
 * Created by rahul.singh on 5/8/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.util.HashMap;

import regatta.com.oea.AppConfig;
import regatta.com.oea.AppConstantVariables;
import regatta.com.oea.MainActivity;
import regatta.com.oea.R;
import regatta.com.oea.ui.LoginActivity;

/**
 * Created by rahul.singh on 12/29/2016.
 */
public class ResetPassword extends Activity {

    EditText oldpassword,newpassword,confirmpassword,oldpasswordedit;
    Button submit;
    EditText old;
    ImageView back;
    private static String  returnResponce;


    Context context;
    private HashMap<String,String> getPass;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpassword);
        context=ResetPassword.this;


        oldpassword = (EditText)findViewById(R.id.oldpassword);
        oldpasswordedit=(EditText)findViewById(R.id.oldpassword);
        old= (EditText)findViewById(R.id.oldpassword);
        oldpassword.setText(AppConstantVariables.userId);
        oldpasswordedit.setText(AppConstantVariables.password);
        newpassword =(EditText)findViewById(R.id.newpassword);
        confirmpassword= (EditText)findViewById(R.id.confirmpsd);
        oldpasswordedit.setText(AppConstantVariables.password);
        back=(ImageView)findViewById(R.id.back);
        submit=(Button)findViewById(R.id.btnsend);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (old.getText().toString().equals(AppConstantVariables.password) && newpassword.getText().toString().equals(confirmpassword.getText().toString()))
                {
                    Toast.makeText(ChangeYourPassword.this,"Toastttt",Toast.LENGTH_LONG).show();
                }*/




              /*  if(!old.getText().toString().equalsIgnoreCase("abc")){
                    Toast.makeText(ResetPassword.this, "Old password incorrect", Toast.LENGTH_LONG).show();
                } else*/ if (newpassword.getText().toString().equals(confirmpassword.getText().toString()) && newpassword.length()>=6 &&  !newpassword.getText().toString().equals(old.getText().toString())) {
                    new SendDataToServer().execute();
                    Toast.makeText(ResetPassword.this, "Your password has been changed", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ResetPassword.this, LoginActivity.class);
                    startActivity(intent);
                }
                else if(!newpassword.getText().toString().equals(confirmpassword.getText().toString()))
                {
                    Toast.makeText(ResetPassword.this, "Confirm Your Password Or Set New Password", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(ResetPassword.this,"Try Strong Password Minimum of 6 Characters",Toast.LENGTH_LONG).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPassword.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public class SendDataToServer extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog= new ProgressDialog (ResetPassword.this);

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
        }
    }
    private void checkLogin() {
        RequestQueue queue = Volley.newRequestQueue(this);
        //http://162.144.82.185/~cnstesting/mpos/api/ws/controller/index.php?access=true&action=change_password&action=change_password&id=1&password=admin&password1=12345
        //String BaseURL = AppConfig.URL+"access=true&action=forgot_password&mailId="+mailid.getText().toString().trim();
        // String BaseURL=AppConfig.URL+"access=true&action=manage_order&customerName="+username.getText().toString().trim()+"&customerMailId="+email.getText().toString().trim()



        //String BaseURL = AppConfig.URL+"access=true&action=change_password&action=change_password&password="+oldpassword.getText().toString().trim()+"&password1="+newpassword.getText().toString().trim()+"&id="+AppConstantVariables.userId;
        //String BaseURL = AppConfig.URL+"access=true&action=change_password&action=change_password&password="+ old.getText().toString()+"&password1="+newpassword.getText().toString().trim()+ "&id="+AppConstantVariables.userId;
        String BaseURL="http://oea.org.in/oeaadmin/api/index.php?access=true&action=reset_password&phone="+AppConstantVariables.contactNumber+"&user_id="+AppConstantVariables.userId+"&old_password="+oldpassword.getText().toString().trim()+"&password="+newpassword.getText().toString().trim()+"&confirm_password="+confirmpassword.getText().toString().trim();
        Log.e(AppConfig.TAG, AppConfig.TAG1 + BaseURL);
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
