import libs.SequenceDiagram;
import logic.Root;

import java.io.IOException;
import java.nio.file.Paths;


public class App {
    private SequenceDiagram sd = SequenceDiagram.getSequenceDiagram();

    // Give the root of the project here and name of the method to be analyzed.
    private static String ROOT_DIR = "src/test/java/t1/";
    private static String CLASS_NAME = "foo";
    private static String METHOD_NAME = "calls_one_method";

    public static void main(String[] args) {
        try {
            Root root = new Root(Paths.get(ROOT_DIR));
            root.start(CLASS_NAME, METHOD_NAME);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
