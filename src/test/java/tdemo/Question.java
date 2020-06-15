package tdemo;

import java.util.ArrayList;
import java.util.List;

public class Question {
    List<String> subquestions;
    public Question() {
        this.subquestions = new ArrayList<String>();
        this.addedNewQuestion("is this a question?");
    }

    public boolean addedNewQuestion(String question) {
        int qnumber_old = subquestions.size();
        if (qnumber_old > 5) {
            return false;
        } else {
            subquestions.add(question);
            return true;
        }
    }

    public void listQuestions() {
        for (int i = 0; i < subquestions.size(); i++) {
            String q = this.getQuestion(i);;
            this.YELLTHEQUESTION(q);
            System.out.println(q);
        }
    }

    public String getQuestion(int i) {
        return this.subquestions.get(i);
    }

    public void deleteAllQuestions() {
        while(this.subquestions.size() != 0) {
            for (int i = this.subquestions.size()-1; i >= 0 ; i--) {
                this.subquestions.remove(i);
            }
            System.out.println("All questions removed!");
        }
    }

    public void YELLTHEQUESTION(String question) {
        System.out.println("IM YeLlING");
        question = question.toUpperCase();
        System.out.println(question);
    }
}
