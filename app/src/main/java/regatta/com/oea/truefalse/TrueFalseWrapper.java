package regatta.com.oea.truefalse;

/**
 * Created by ITPC001 on 10-May-17.
 */

public class TrueFalseWrapper {

    private int id;
    private String question;
    private String answers;


    public TrueFalseWrapper(int id, String question, String answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

}
