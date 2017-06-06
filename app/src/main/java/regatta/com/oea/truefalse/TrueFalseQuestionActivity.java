package regatta.com.oea.truefalse;

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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import regatta.com.oea.R;
import regatta.com.oea.arquizapp.QuizModelItem;
import regatta.com.oea.chapter.Chapter_one;
import regatta.com.oea.oneworld.OneWorldActivity;


/**
 * Created by ArunKhk-PC on 10-May-17.
 */

public class TrueFalseQuestionActivity extends Activity {
    private TextView quizQuestion;

    private RadioGroup radioGroup;
    private RadioButton optionOne;
    private RadioButton optionTwo;

    private int currentQuizQuestion;
    private int quizCount;
    private TrueFalseWrapper firstQuestion;
    private List<TrueFalseWrapper> parsedObject;
    private int counter_correct_answer = 0;
    private int counter_in_correct_answer = 0;
    private QuizModelItem modelItem;
    ImageView back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.truefalse_question);

        modelItem = QuizModelItem.getInstance();
        quizQuestion = (TextView) findViewById(R.id.quiz_question);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        optionOne = (RadioButton) findViewById(R.id.radio_true);
        optionTwo = (RadioButton) findViewById(R.id.radio_false);
        back=(ImageView) findViewById(R.id.back);
        Button nextButton = (Button) findViewById(R.id.nextquiz);
        TrueFalseQuestionActivity.AsyncJsonObject asyncObject = new TrueFalseQuestionActivity.AsyncJsonObject();
        asyncObject.execute("");
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(TrueFalseQuestionActivity.this, Chapter_one.class);
        startActivity(intent);
        finish();
    }
});
        /*excute button*/
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioSelected = radioGroup.getCheckedRadioButtonId();
                String userSelection = getSelectedAnswer(radioSelected);
                Log.e("TAG", "userSelection :" + userSelection);
                String correctAnswerForQuestion = firstQuestion.getAnswers();
                Log.e("TAG", "correctAnswerForQuestion :" + correctAnswerForQuestion);
                currentQuizQuestion++;
                if (currentQuizQuestion >= quizCount) {
                   // Toast.makeText(TrueFalseQuestionActivity.this, "End of the Questions", Toast.LENGTH_LONG).show();
                    Intent in = new Intent(TrueFalseQuestionActivity.this, OneWorldActivity.class);
                    startActivity(in);
                    finish();
                    return;
                }
                firstQuestion = parsedObject.get(currentQuizQuestion);
                quizQuestion.setText(firstQuestion.getQuestion());
                uncheckedRadioButton();
                //  String[] possibleAnswers = firstQuestion.getAnswers().split(",");
                //optionOne.setText(possibleAnswers[0]);
                //  optionTwo.setText(possibleAnswers[0]);

                if (correctAnswerForQuestion.equalsIgnoreCase("False")) {
                    correctAnswerForQuestion = "2";
                    Log.e("TAG", "modify true value :" + correctAnswerForQuestion);
                } else if (correctAnswerForQuestion.equalsIgnoreCase("True")) {
                    correctAnswerForQuestion = "1";
                    Log.e("TAG", "modify false value :" + correctAnswerForQuestion);
                }

                if (userSelection.trim().equalsIgnoreCase(correctAnswerForQuestion)) {
                    // correct answer
                    AlertDialog alertDialog = new AlertDialog.Builder(TrueFalseQuestionActivity.this).create();
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
                    AlertDialog alertDialog = new AlertDialog.Builder(TrueFalseQuestionActivity.this).create();
                    alertDialog.setTitle("Incorrect Answer");
                    alertDialog.setMessage("You Did not choose Right Answer,Try This Question");
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
            progressDialog = ProgressDialog.show(TrueFalseQuestionActivity.this, "Downloading Quiz", "", true);
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
            String[] possibleAnswers = firstQuestion.getAnswers().split(",");
            //optionOne.setText(possibleAnswers[0]);
            optionTwo.setText(possibleAnswers[0]);
        }
    }

    private List<TrueFalseWrapper> returnParsedJsonObject(String result) {
        List<TrueFalseWrapper> jsonObject = new ArrayList<TrueFalseWrapper>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        TrueFalseWrapper newItemObject = null;
        //   Log.e("TAG","Testing the water " + result);
        try {
            resultObject = new JSONObject(result);
            jsonArray = resultObject.optJSONArray("true_false");
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
                newItemObject = new TrueFalseWrapper(id, question, correctAnswer);
                jsonObject.add(newItemObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    private String getSelectedAnswer(int radioSelected) {

        int answerSelected = 0;
        if (radioSelected == R.id.radio_true) {
            answerSelected = 1;
        }
        if (radioSelected == R.id.radio_false) {
            answerSelected = 2;
        }
        String strI = Integer.toString(answerSelected);
        return strI;
    }

    private void uncheckedRadioButton() {
        optionOne.setChecked(false);
        optionTwo.setChecked(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
