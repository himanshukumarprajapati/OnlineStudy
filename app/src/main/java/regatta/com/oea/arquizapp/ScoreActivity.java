package regatta.com.oea.arquizapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import regatta.com.oea.MainActivity;
import regatta.com.oea.R;

/**
 * Created by AruKhk-PC on 09-May-17.
 */

public class ScoreActivity extends Activity {
    ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.score_layout);
        back= (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        QuizModelItem quizModelItem=QuizModelItem.getInstance();
        TextView textView= (TextView)findViewById(R.id.textView);
        TextView textView1= (TextView)findViewById(R.id.textView2);
       // textView.setText("Correct answer : "+quizModelItem.getCorrectAnswer());
       // textView1.setText("Incorrect answer : "+quizModelItem.getIncorrectAnswer());

    }
}
