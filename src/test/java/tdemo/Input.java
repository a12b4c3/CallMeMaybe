package tdemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Input {
    ArrayList<String> surveyResponses = new ArrayList<>();

    public String getInput(String fileExtension) {
        java.io.BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String ret = this.appendFileExtension(in.toString(), fileExtension);
        return ret;
    }

    private String appendFileExtension(String filename, String fileExtension) {
        return filename + fileExtension;
    }

    public boolean getSurveyAnswer() {
        java.io.BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        if (in.equals("yes")) {
            this.surveyResponses.add("yes");
            return true;
        } else {
            this.surveyResponses.add("false");
            return false;
        }
    }
}
