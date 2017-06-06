package regatta.com.oea.arquizapp;

/**
 * Created by ArunKhk-PC on 09-May-17.
 */

public class QuizModelItem {

    public static QuizModelItem instance=null;
    public static QuizModelItem getInstance(){
        if(instance==null){
            instance= new QuizModelItem();
        }
        return instance;
    }

    int correctAnswer;
    int incorrectAnswer;
    String saveWholeQuestion;

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getIncorrectAnswer() {
        return incorrectAnswer;
    }

    public void setIncorrectAnswer(int incorrectAnswer) {
        this.incorrectAnswer = incorrectAnswer;
    }

    public static void setInstance(QuizModelItem instance) {
        QuizModelItem.instance = instance;
    }

    public String getSaveWholeQuestion() {
        return saveWholeQuestion;
    }

    public void setSaveWholeQuestion(String saveWholeQuestion) {
        this.saveWholeQuestion = saveWholeQuestion;
    }
}

