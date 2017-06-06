package regatta.com.oea.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import regatta.com.oea.R;

//D:\JunkCleaner_Application\app

public class Splash_Screen extends Activity {

    TextView textView,textView1;
    ImageView launcherimg;
    //D:\JunkCleaner_Application\app

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        textView = (TextView) findViewById(R.id.textView);
        //textView1 = (TextView) findViewById(R.id.textView1);
        launcherimg = (ImageView) findViewById(R.id.launcherimg);

        Thread timer = new Thread() {
            public void run() {
                try {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                    //Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
                    textView.startAnimation(animation1);
                   // textView1.startAnimation(animation1);
                    launcherimg.startAnimation(animation);
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(Splash_Screen.this, LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.newslide_down, R.anim.newslideup);
                    finish();
                }
            }
        };
        timer.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
