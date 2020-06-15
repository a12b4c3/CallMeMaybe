
import libs.Client;
import libs.SequenceDiagram;
import logic.Root;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;


public class App {
    private SequenceDiagram sd = SequenceDiagram.getSequenceDiagram();

    // Give the root of the project here and name of the method to be analyzed.

    private static final String ROOT_DIR = "src/test/java/tdemo/";
    private static final String CLASS_NAME = "main";
    private static final String METHOD_NAME = "main";
    private static final String DEFAULT_LOOPBACK_ENDPOINT = "http://127.0.0.1:8080/api/v7/generateDiagramImage";
    private static ArrayList<String> METHOD_PARAMS = new ArrayList<String>(){};

    public static void main(String[] args) {
        try {
            METHOD_PARAMS.add("String[]");
            Root root = new Root(Paths.get(ROOT_DIR));
            root.start(CLASS_NAME, METHOD_NAME, METHOD_PARAMS);
            String clientInput = root.finishDiagram();
            Client client = new Client(DEFAULT_LOOPBACK_ENDPOINT);
            client.sendPostAndDraw(clientInput, METHOD_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}