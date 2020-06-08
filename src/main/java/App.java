import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;
import logic.Root;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


public class App {
    private SequenceDiagram sd = SequenceDiagram.getSequenceDiagram();

    // Give the root of the project here and name of the method to be analyzed.
    private static String ROOT_DIR = "src/test/java/t1/";
    private static String FILE_NAME = "foo.java";
    private static String METHOD_NAME = "calls_one_method";

    public static void main(String[] args) {
        try {
            Root root = new Root(Paths.get(ROOT_DIR));
            System.out.println("hello");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
