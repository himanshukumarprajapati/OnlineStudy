package regatta.com.oea.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import regatta.com.oea.MainActivity;
import regatta.com.oea.R;

/**
 * Created by rahul.singh on 4/27/2017.
 */

public class Subject extends Activity {
    ImageView six;
    ImageView back;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.subjectlayout);

six=(ImageView) findViewById(R.id.six);
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Subject.this,Books.class);
                startActivity(intent);
            }
        });
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Subject.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}