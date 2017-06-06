package regatta.com.oea.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import regatta.com.oea.R;

/**
 * Created by rahul.singh on 4/27/2017.
 */

public class LevelActivity extends Activity {

    LinearLayout levelone,leveltwo,levelthree,levelfour;
    ImageView back;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.levelactivity);
        levelone =(LinearLayout) findViewById(R.id.levelone);
        leveltwo =(LinearLayout) findViewById(R.id.leveltwo);
        levelthree =(LinearLayout) findViewById(R.id.levelthree);
        levelfour =(LinearLayout) findViewById(R.id.levelfour);
        levelone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this,McqsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        leveltwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this,MatchActivity.class);
                startActivity(intent);
                finish();
            }
        });
        levelthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this,Filltheblank.class);
                startActivity(intent);
                finish();
            }
        });
        levelfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this,Trueone.class);
                startActivity(intent);
                finish();
            }
        });
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, ChapterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}