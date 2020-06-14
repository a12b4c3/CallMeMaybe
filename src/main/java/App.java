
import libs.SequenceDiagram;
import logic.Root;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;


public class App {
    private SequenceDiagram sd = SequenceDiagram.getSequenceDiagram();

    // Give the root of the project here and name of the method to be analyzed.

    private static final String ROOT_DIR = "src/test/java/t3/";
    private static final String CLASS_NAME = "node";
    private static final String METHOD_NAME = "loop_five_times";

    private static final ArrayList<String> METHOD_PARAMS = new ArrayList<String>(){};

    public static void main(String[] args) {
        try {
            Root root = new Root(Paths.get(ROOT_DIR));
            root.start(CLASS_NAME, METHOD_NAME, METHOD_PARAMS);
            root.finishDiagram();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}