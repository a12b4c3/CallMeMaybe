import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileNotFoundException;


public class app {
    private SequenceDiagram sd = SequenceDiagram.getSequenceDiagram();

    // Give the root of the project here and name of the method to be analyzed.
    private static String FILE_PATH = "";
    private static String METHOD_NAME = "";

    public static void main(String[] args) {
        try {
            CompilationUnit root = StaticJavaParser.parse(new File(FILE_PATH));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
