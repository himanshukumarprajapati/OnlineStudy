package regatta.com.oea.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import regatta.com.oea.AppConfig;
import regatta.com.oea.AppConstantVariables;
import regatta.com.oea.MainActivity;
import regatta.com.oea.R;
import regatta.com.oea.app.AppController;
import regatta.com.oea.newclasses.CustomVolleyRequestQueue;

/**
 * Created by rahul.singh on 4/27/2017.
 */

public class Profile extends Activity {

    private String urlJsonObj = "http://oea.org.in/oeaadmin/api/index.php?access=true&action=view_user_details&user_id="+ AppConstantVariables.userId;
    private static String TAG = MainActivity.class.getSimpleName();
    TextView textView2,textView3,sponserid,oeamoneyy,classnameee,phoneno,fullname,emailid,schoolname,imgurltxt;
    // Progress dialog
    private ProgressDialog pDialog;
    private TextView txtResponse;
    LinearLayout linearLayout3;
    private String jsonResponse,jsonResponse1,jsonResponse2,jsonResponse3,jsonResponse4,jsonResponse5,jsonResponse6,jsonResponse7,jsonResponse8,jsonResponse9;
    private NetworkImageView mNetworkImageView;
    private ImageLoader mImageLoader;
    Button btnsubmit;
    private static String  returnResponce;

    ImageView imageView4,imageView5,imageView6,imageView7,imageView8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.profile);
        mNetworkImageView = (NetworkImageView) findViewById(R.id.networkImageView);
        linearLayout3= (LinearLayout)findViewById(R.id.linearLayout3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView5 = (ImageView) findViewById(R.id.imageView5);
        imageView6 = (ImageView) findViewById(R.id.imageView6);
        imageView7 = (ImageView) findViewById(R.id.imageView7);
        imageView8 = (ImageView) findViewById(R.id.imageView8);


        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        sponserid = (TextView) findViewById(R.id.sponserid);
        oeamoneyy = (TextView) findViewById(R.id.oeamoney);
        classnameee = (TextView) findViewById(R.id.classname);
        phoneno = (TextView) findViewById(R.id.phoneno);
        fullname = (TextView) findViewById(R.id.fullname);
        emailid = (TextView) findViewById(R.id.emailid);
        schoolname = (TextView) findViewById(R.id.schoolname);
        imgurltxt= (TextView) findViewById(R.id.imgurltxt);
        btnsubmit=(Button) findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue queue = Volley.newRequestQueue(Profile.this);
                //String BaseURL = AppConfig.URL+"access=true&action=forgot_password&mailId="+mailid.getText().toString().trim();
                String BaseURL ="http://oea.org.in/oeaadmin/api/index.php?access=true&action=updateProfile&email="+emailid.getText().toString().trim()+"&username="+fullname.getText().toString().trim()+"&phone="+phoneno.getText().toString().trim()+"&school_name="+schoolname.getText().toString().trim()+"&class_name="+classnameee.getText().toString().trim()+"&user_id="+AppConstantVariables.userId;
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
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Create custom dialog object
                final Dialog dialog = new Dialog(Profile.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog);
                // Set dialog title
                dialog.setTitle("Custom Dialog");

                // set values for custom dialog components - text, image and button
                final EditText text = (EditText) dialog.findViewById(R.id.textDialog);
                //text.setText("Custom dialog Android example.");
                ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
                image.setImageResource(R.drawable.backicon);

                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
                // if decline button is clicked, close the custom dialog
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        classnameee.setText(text.getText().toString());
                        dialog.dismiss();
                    }
                });
                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        //  dummy.setText(text.getText().toString());
                        dialog.dismiss();
                    }
                });


            }

        });
        imageView5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Create custom dialog object
                final Dialog dialog = new Dialog(Profile.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog);
                // Set dialog title
                dialog.setTitle("Custom Dialog");

                // set values for custom dialog components - text, image and button
                final EditText text = (EditText) dialog.findViewById(R.id.textDialog);
                //text.setText("Custom dialog Android example.");
                ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
                image.setImageResource(R.drawable.backicon);

                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
                // if decline button is clicked, close the custom dialog
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        phoneno.setText(text.getText().toString());
                        dialog.dismiss();
                    }
                });
                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        //  dummy.setText(text.getText().toString());
                        dialog.dismiss();
                    }
                });
            }

        });
        imageView6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Create custom dialog object
                final Dialog dialog = new Dialog(Profile.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog);
                // Set dialog title
                dialog.setTitle("Custom Dialog");

                // set values for custom dialog components - text, image and button
                final EditText text = (EditText) dialog.findViewById(R.id.textDialog);
                //text.setText("Custom dialog Android example.");
                ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
                image.setImageResource(R.drawable.backicon);

                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
                // if decline button is clicked, close the custom dialog
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        fullname.setText(text.getText().toString());
                        dialog.dismiss();
                    }
                });
                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        //  dummy.setText(text.getText().toString());
                        dialog.dismiss();
                    }
                });
            }

        });
        imageView7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Create custom dialog object
                final Dialog dialog = new Dialog(Profile.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog);
                // Set dialog title
                dialog.setTitle("Custom Dialog");

                // set values for custom dialog components - text, image and button
                final EditText text = (EditText) dialog.findViewById(R.id.textDialog);
                //text.setText("Custom dialog Android example.");
                ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
                image.setImageResource(R.drawable.backicon);

                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
                // if decline button is clicked, close the custom dialog
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        emailid.setText(text.getText().toString());
                        dialog.dismiss();
                    }
                });
                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        //  dummy.setText(text.getText().toString());
                        dialog.dismiss();
                    }
                });
            }

        });
        imageView8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Create custom dialog object
                final Dialog dialog = new Dialog(Profile.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog);
                // Set dialog title
                dialog.setTitle("Custom Dialog");

                // set values for custom dialog components - text, image and button
                final EditText text = (EditText) dialog.findViewById(R.id.textDialog);
                //text.setText("Custom dialog Android example.");
                ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
                image.setImageResource(R.drawable.backicon);

                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
                // if decline button is clicked, close the custom dialog
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        schoolname.setText(text.getText().toString());
                        dialog.dismiss();
                    }
                });
                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        //  dummy.setText(text.getText().toString());
                        dialog.dismiss();
                    }
                });
            }

        });
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        showpDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

                    JSONObject phone = response.getJSONObject("data");
                    String home = phone.getString("phone");
                    String mobile = phone.getString("username");
                    String email =phone.getString("email");
                    String classname=phone.getString("email");
                    String oeamoney = phone.getString("coins");
                    String classnamee= phone.getString("class_name");
                    String sponser= phone.getString("invite_id");
                    String imgurl=phone.getString("user_image");
                    Log.e("userimg",""+imgurl);

                    jsonResponse = "";
                    jsonResponse1 = "";
                    jsonResponse2 = "";
                    jsonResponse3 = "";
                    jsonResponse4 = "";
                    jsonResponse5 = "";
                    jsonResponse6 = "";
                    jsonResponse7 = "";
                    jsonResponse8 = "";
                    jsonResponse9 = "";
                    //jsonResponse += "Name: " + name + "\n\n";
                    //jsonResponse += "Email: " + email + "\n\n";
                    jsonResponse += "" + home + "\n\n";
                    jsonResponse1 +=""+ mobile ;
                    jsonResponse2 +=""+ email;
                    jsonResponse3 +=""+ classname;
                    jsonResponse4 +=""+ oeamoney;
                    jsonResponse5 +=""+ classnamee;
                    jsonResponse6+=""+ sponser;
                    jsonResponse7+=""+ imgurl;

                    phoneno.setText(jsonResponse);
                    textView2.setText(jsonResponse1);
                    textView3.setText(jsonResponse2);
                    emailid.setText(jsonResponse3);
                    oeamoneyy.setText(jsonResponse4);
                    classnameee.setText(jsonResponse5);
                    fullname.setText(jsonResponse1);
                    sponserid.setText(jsonResponse6);
                    imgurltxt.setText(jsonResponse7);
                    // textView2,textView3,sponserid,oeamoney,classname,phoneno,fullname,emailid,schoolname

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    protected void onStart() {
        super.onStart();
        // Instantiate the RequestQueue.
        mImageLoader = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getImageLoader();
        //Image URL - This can point to any image file supported by Android
        final String url = "http://oea.org.in/oeaadmin/profile_photo/7424071.jpg";
        mImageLoader.get(url, ImageLoader.getImageListener(mNetworkImageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
        mNetworkImageView.setImageUrl(url, mImageLoader);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
