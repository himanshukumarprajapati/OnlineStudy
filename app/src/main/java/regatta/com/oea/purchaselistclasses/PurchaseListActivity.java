package regatta.com.oea.purchaselistclasses;

/**
 * Created by rahul.singh on 5/10/2017.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
 * Created by rahul.singh on 5/10/2017.
 */

/**
 * Created by rahul.singh on 4/27/2017.
 */

/**
 * Created by rahul.singh on 12/29/2016.
 */
public class PurchaseListActivity extends Activity {
    EditText mailid;
    ImageView back;
    Button submit;
    private static String  returnResponce;
    EditText ettractionid,etclassname,etsubjectname,etprice,etpaidoea,etpaidinr,etschoolname;
    LinearLayout linearLayout3;
    final Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.purchaselist);
        ettractionid = (EditText) findViewById(R.id.ettractionid);
        etclassname = (EditText) findViewById(R.id.etclassname);

        etsubjectname = (EditText) findViewById(R.id.etsubjectname);
        etprice = (EditText) findViewById(R.id.etprice);
        etpaidoea = (EditText) findViewById(R.id.etpaidoea);
        etpaidinr = (EditText) findViewById(R.id.etpaidinr);
        etschoolname = (EditText) findViewById(R.id.etschoolname);
       // etclassname = (EditText) findViewById(R.id.etsummary);
        linearLayout3 =(LinearLayout) findViewById(R.id.linearLayout2);
        //  mailid = (EditText) findViewById(R.id.mailid);
        //mailid.setText(AppConstantVariables.mailId);
        back = (ImageView) findViewById(R.id.back);
        //  Log.d("con",""+AppConstantVariables.contactNumber);
        // submit=(Button)findViewById(R.id.btnsend);
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ettractionid.getText().toString().equals("")) {
                    //  new HelpActivity().SendDataToServer().execute();
                    //new HelpActivity().SendDataToServer().exe
                    checkLogin();

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    // set title
                    alertDialogBuilder.setTitle("Purchase List Sent Successfully");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("We will Reply Regarding Your Purchase List")
                            .setCancelable(false)
                            .setPositiveButton("Close",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    PurchaseListActivity.this.finish();
                                }
                            });
                            /*.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                   HelpActivity.this.finish();
                                }
                            });*/

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
               /* Intent intent = new Intent(ForgettenPassword.this,LoginActivity.class);
                startActivity(intent);*/
                }
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    // set title
                    alertDialogBuilder.setTitle("Oops!!! Try Again");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Fill The Form!")
                            .setCancelable(false)
                            .setPositiveButton("Close",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    dialog.dismiss();
                                }
                            });
                            /*.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                   HelpActivity.this.finish();
                                }
                            });*/

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PurchaseListActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private class SendDataToServer extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog= new ProgressDialog (PurchaseListActivity.this);

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
        String BaseURL="http://oea.org.in/oeaadmin/api/index.php?access=true&action=purchase&user_id="+AppConstantVariables.userId+"&class_id="+etclassname.getText().toString().trim()+"&subject_id="+etsubjectname.getText().toString().trim()+"&price="+etprice.getText().toString().trim()+"&transaction_id="+ettractionid.getText().toString().trim()+"&paid_oea_wallet="+etpaidoea.getText().toString().trim()+"&paid_inr="+etpaidinr.getText().toString().trim()+"&school_name="+etschoolname.getText().toString().trim();
        //String BaseURL="http://oea.org.in/oeaadmin/api/index.php?access=true&action=purchase&subject="+etsubject.getText().toString().trim()+"&summry="+etsummary.getText().toString().trim()+"&image=uploads1/37674653-285x360.jpg";
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
