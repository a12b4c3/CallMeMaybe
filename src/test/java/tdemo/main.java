package tdemo;

public class main {
    public static void main(String[] args) {
        Input input = new Input();
        input.getInput("svg");
        Question question = new Question();

        question.addedNewQuestion("How are you feeling today?");
        input.getSurveyAnswer();
        question.addedNewQuestion("Did you sleep well?");
        input.getSurveyAnswer();

        question.listQuestions();
        question.deleteAllQuestions();

    }
}
