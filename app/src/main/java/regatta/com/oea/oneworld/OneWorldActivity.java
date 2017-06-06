package regatta.com.oea.oneworld;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import regatta.com.oea.R;
import regatta.com.oea.arquizapp.QuizModelItem;
import regatta.com.oea.arquizapp.ScoreActivity;
import regatta.com.oea.chapter.Chapter_one;


/**
 * Created by ArunKhk-PC on 10-May-17.
 */

public class OneWorldActivity extends Activity {

    private EditText et_answer;
    private TextView quizQuestion;
    private int currentQuizQuestion;
    private int quizCount;
    private OneWorldWrapper firstQuestion;
    private List<OneWorldWrapper> parsedObject;
    private int counter_correct_answer = 0;
    private int counter_in_correct_answer = 0;
    private QuizModelItem modelItem;
    ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.on_world_layout);
        et_answer= (EditText)findViewById(R.id.et_answer);
        modelItem = QuizModelItem.getInstance();
        quizQuestion = (TextView) findViewById(R.id.quiz_question);
        Button nextButton = (Button) findViewById(R.id.nextquiz);
        back=(ImageView) findViewById(R.id.back);

        OneWorldActivity.AsyncJsonObject asyncObject = new OneWorldActivity.AsyncJsonObject();
        asyncObject.execute("");
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(OneWorldActivity.this, Chapter_one.class);
        startActivity(intent);
        finish();
    }
});
        /*excute button*/
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userSelection = getSelectedAnswer();
                Log.e("TAG", "userSelection :" + userSelection);
                String correctAnswerForQuestion = firstQuestion.getAnswers();
                Log.e("TAG", "correctAnswerForQuestion :" + correctAnswerForQuestion);
                currentQuizQuestion++;
                if (currentQuizQuestion >= quizCount) {
                    Toast.makeText(OneWorldActivity.this, "End of the Questions", Toast.LENGTH_LONG).show();
                    Intent in = new Intent(OneWorldActivity.this, ScoreActivity.class);
                    startActivity(in);
                    return;
                }
                firstQuestion = parsedObject.get(currentQuizQuestion);
                quizQuestion.setText(firstQuestion.getQuestion());
                clearText();

                if (userSelection.trim().equalsIgnoreCase(correctAnswerForQuestion)) {
                    // correct answer
                    AlertDialog alertDialog = new AlertDialog.Builder(OneWorldActivity.this).create();
                    alertDialog.setTitle("Correct Answer");
                    alertDialog.setMessage("Previous Answer was correct,Try This Question");
                    alertDialog.setIcon(R.drawable.iconji);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                    counter_correct_answer++;
                    modelItem.setCorrectAnswer(counter_correct_answer);
                } else {
                    // failed question
                    AlertDialog alertDialog = new AlertDialog.Builder(OneWorldActivity.this).create();
                    alertDialog.setTitle("Incorrect Answer");
                    alertDialog.setMessage("Previous Answer was incorrect,Try This Question");
                    alertDialog.setIcon(R.drawable.iconji);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                    counter_in_correct_answer++;
                    modelItem.setIncorrectAnswer(counter_in_correct_answer);
                    return;
                }
            }
        });
    }

    private class AsyncJsonObject extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {

            String jsonResult = "";
            try {

                jsonResult = modelItem.getSaveWholeQuestion();
                // Log.e("TAG","Returned Json object " + jsonResult);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return jsonResult;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressDialog.show(OneWorldActivity.this, "Downloading Quiz", "", true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            //  Log.e("TAG","Resulted Value: " + result);
            parsedObject = returnParsedJsonObject(result);
            if (parsedObject == null) {
                return;
            }
            quizCount = parsedObject.size();
            firstQuestion = parsedObject.get(0);
            quizQuestion.setText(firstQuestion.getQuestion());
    }
    }

    private List<OneWorldWrapper> returnParsedJsonObject(String result) {
        List<OneWorldWrapper> jsonObject = new ArrayList<OneWorldWrapper>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        OneWorldWrapper newItemObject = null;
        //   Log.e("TAG","Testing the water " + result);
        try {
            resultObject = new JSONObject(result);
            jsonArray = resultObject.optJSONArray("one_word");
        } catch (Exception e) {
            Log.e("TAG", "Exception " + result);
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonChildNode = null;
            try {
                jsonChildNode = jsonArray.getJSONObject(i);
                String str_id = jsonChildNode.getString("id");
                int id = Integer.parseInt(str_id);
                String question = jsonChildNode.getString("question");
                //   Log.e("TAG","question: " +question);
                String correctAnswer = jsonChildNode.getString("answer");
                // Log.e("TAG","Answer: " +correctAnswer);
                newItemObject = new OneWorldWrapper(id, question, correctAnswer);
                jsonObject.add(newItemObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    private String getSelectedAnswer() {
        String strI = et_answer.getText().toString();
        return strI;
    }
    private void clearText() {
        et_answer.setText("");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
