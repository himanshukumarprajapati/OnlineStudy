package regatta.com.oea.arquizapp;

/**
 * Created by ArunKhk-PC on 09-May-17.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import regatta.com.oea.R;
import regatta.com.oea.chapter.Chapter_one;
import regatta.com.oea.truefalse.TrueFalseQuestionActivity;


public class QuizActivity extends ActionBarActivity {

    private TextView quizQuestion;

    private RadioGroup radioGroup;
    private RadioButton optionOne;
    private RadioButton optionTwo;
    private RadioButton optionThree;
    private RadioButton optionFour;
    private int currentQuizQuestion;
    private int quizCount;
    private QuizWrapper firstQuestion;
    private List<QuizWrapper> parsedObject;
    private int counter_correct_answer=0;
    private int counter_in_correct_answer=0;
    private QuizModelItem modelItem;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_quiz);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        modelItem=QuizModelItem.getInstance();
        quizQuestion = (TextView)findViewById(R.id.quiz_question);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        optionOne = (RadioButton)findViewById(R.id.radio0);
        optionTwo = (RadioButton)findViewById(R.id.radio1);
        optionThree = (RadioButton)findViewById(R.id.radio2);
        optionFour = (RadioButton)findViewById(R.id.radio3);
        back = (ImageView) findViewById(R.id.back);

       // Button previousButton = (Button)findViewById(R.id.previousquiz);
        Button nextButton = (Button)findViewById(R.id.nextquiz);

        AsyncJsonObject asyncObject = new AsyncJsonObject();
        asyncObject.execute("");


         back.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
        Intent intent = new Intent(QuizActivity.this, Chapter_one.class);
        startActivity(intent);
        finish();
                       }
              });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioSelected = radioGroup.getCheckedRadioButtonId();
                String userSelection = getSelectedAnswer(radioSelected);
                Log.e("TAG","userSelection :" +userSelection);
                String correctAnswerForQuestion = firstQuestion.getCorrectAnswer();
                Log.e("TAG","correctAnswerForQuestion :" +correctAnswerForQuestion);
            //  String replaced=  correctAnswerForQuestion.replace("o"," ").trim();
                correctAnswerForQuestion =correctAnswerForQuestion.replaceAll("option", " ").trim();
                Log.e("TAG","correctAnswerForQuestion after replace:" +correctAnswerForQuestion);
                currentQuizQuestion++;
                if(currentQuizQuestion >= quizCount){
                  //  Toast.makeText(QuizActivity.this, "End of the Questions", Toast.LENGTH_LONG).show();

                    Intent in =new Intent(QuizActivity.this,TrueFalseQuestionActivity.class);
                    startActivity(in);
                    finish();
                    return;
                }
                firstQuestion = parsedObject.get(currentQuizQuestion);
                quizQuestion.setText(firstQuestion.getQuestion());
                String[] possibleAnswers = firstQuestion.getAnswers().split(",");
                uncheckedRadioButton();
                optionOne.setText(possibleAnswers[0]);
                optionTwo.setText(possibleAnswers[1]);
                optionThree.setText(possibleAnswers[2]);
                optionFour.setText(possibleAnswers[3]);

                if(userSelection.trim().equalsIgnoreCase(correctAnswerForQuestion)){
                    // correct answer
                    AlertDialog alertDialog = new AlertDialog.Builder(QuizActivity.this).create();
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
                } else{
                    // failed question
                    AlertDialog alertDialog = new AlertDialog.Builder(QuizActivity.this).create();
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
       /* previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuizQuestion--;
                if(currentQuizQuestion < 0){
                   return;
                }
                uncheckedRadioButton();
                firstQuestion = parsedObject.get(currentQuizQuestion);
                quizQuestion.setText(firstQuestion.getQuestion());
                String[] possibleAnswers = firstQuestion.getAnswers().split(",");
                optionOne.setText(possibleAnswers[0]);
                optionTwo.setText(possibleAnswers[1]);
                optionThree.setText(possibleAnswers[2]);
                optionFour.setText(possibleAnswers[3]);
            }
        });*/
    }
    private class AsyncJsonObject extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... params) {

            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httpPost = new HttpPost("http://oea.org.in/oeaadmin/api/index.php?access=true&action=get_chapter_quiz&level_id=13&book_id=143&chapter_id=10");
            String jsonResult = "";
            try {
                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString().replace("&quot;","\"");
                Log.e("TAG","Returned Json object " + jsonResult);
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return jsonResult;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressDialog.show(QuizActivity.this, "Downloading Quiz","Wait....", true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.e("TAG","Resulted Value: " + result);
            parsedObject = returnParsedJsonObject(result);
            if(parsedObject == null){
                return;
            }
            quizCount = parsedObject.size();
            firstQuestion = parsedObject.get(0);
            quizQuestion.setText(firstQuestion.getQuestion());
            String[] possibleAnswers = firstQuestion.getAnswers().split(",");
            optionOne.setText(possibleAnswers[0]);
            optionTwo.setText(possibleAnswers[1]);
            optionThree.setText(possibleAnswers[2]);
            optionFour.setText(possibleAnswers[3]);
        }
        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            try {
                while ((rLine = br.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.e("TAG","answer +" +answer.toString());
            return answer;
        }
    }
    private List<QuizWrapper> returnParsedJsonObject(String result){
        List<QuizWrapper> jsonObject = new ArrayList<QuizWrapper>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        QuizWrapper newItemObject = null;
        try {
            resultObject = new JSONObject(result);
            if(!resultObject.getString("status").equalsIgnoreCase("true")){
                Log.e("TAG","status " + resultObject.getString("status"));
              return null ;
            }

            Log.e("TAG","Testing the water " + resultObject.toString());
            resultObject=resultObject.getJSONObject("data");
            resultObject=resultObject.getJSONObject("13");
            modelItem.setSaveWholeQuestion(resultObject.toString());
            jsonArray = resultObject.optJSONArray("mcq");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonChildNode = null;
            try {
                jsonChildNode = jsonArray.getJSONObject(i);
                String str_id = jsonChildNode.getString("id");
                int id= Integer.parseInt(str_id);
                String question = jsonChildNode.getString("question");

                String possible_answers1=jsonChildNode.getString("option1");
                String possible_answers2=jsonChildNode.getString("option2");
                String possible_answers3=jsonChildNode.getString("option3");
                String possible_answers4=jsonChildNode.getString("option4");

              /*  possible_answers1.replace("option","");
                possible_answers2.replace("option","");
                possible_answers3.replace("option","");
                possible_answers4.replace("option","");
*/

                String stringbuilder = new StringBuilder(possible_answers1+",")
                        .append(possible_answers2+",").append(possible_answers3+",").append(possible_answers4).toString();

                String answerOptions = stringbuilder;
                Log.e("TAG","stringbuilder: " +answerOptions);
                String correctAnswer = jsonChildNode.getString("answer");
                Log.e("TAG","correctAnswer: " +correctAnswer);
                newItemObject = new QuizWrapper(id, question, answerOptions, correctAnswer);
                jsonObject.add(newItemObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    private String getSelectedAnswer(int radioSelected){

        int answerSelected = 0;
        if(radioSelected == R.id.radio0){
            answerSelected = 1;
        }
        if(radioSelected == R.id.radio1){
            answerSelected = 2;
        }
        if(radioSelected == R.id.radio2){
            answerSelected = 3;
        }
        if(radioSelected == R.id.radio3){
            answerSelected = 4;
        }
        String strI = Integer.toString(answerSelected);
        return strI;
    }
    private void uncheckedRadioButton(){
        radioGroup.clearCheck();
       // optionOne.setChecked(false);
       // optionTwo.setChecked(false);
       // optionThree.setChecked(false);
       // optionFour.setChecked(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
