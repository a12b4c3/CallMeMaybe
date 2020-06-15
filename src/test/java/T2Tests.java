import libs.SequenceDiagram;
import logic.Root;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class T2Tests {
    private static SequenceDiagram diagram = SequenceDiagram.getSequenceDiagram();
    private static String ROOT_DIR = "";
    private static String CLASS_NAME = "";
    private static String METHOD_NAME = "";
    private static ArrayList<String> METHOD_PARAMS = new ArrayList<String>(){};

    @BeforeAll
    public static void resetTestConditions() {
        diagram.clear();
        ROOT_DIR = "";
        CLASS_NAME = "";
        METHOD_NAME = "";
        METHOD_PARAMS.clear();
    }

    @Test
    public void t2_calls_one_method() {
        ROOT_DIR = "src/test/java/t2/";
        CLASS_NAME = "foo";
        METHOD_NAME = "calls_one_other_class_no_return";
        METHOD_PARAMS.clear();
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList(
                "title calls_one_method()",
                "autoactivation on",
                "participant foo",
                "[->foo:calls_one_other_class_no_return()",
                "foo->bar: new bar()",
                "bar-->foo: ret",
                "foo->bar:call_me_no_return()",
                "bar-->foo: ret",
                "foo-->foo"));
        try {
            Root root = new Root(Paths.get(ROOT_DIR));
            root.start(CLASS_NAME, METHOD_NAME, METHOD_PARAMS);
            diagram.getStringToPaint();
            Assertions.assertIterableEquals(expected, diagram.getStringToPaint());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t1_calls_two_methods() {
        ROOT_DIR = "src/test/java/t2/";
        CLASS_NAME = "foo";
        METHOD_NAME = "calls_two_other_classes_mixed_returns";
        METHOD_PARAMS.clear();
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList(
                "calls_two_other_classes_mixed_returns()",
                "autoactivation on",
                "participant foo",
                "[->foo:calls_two_other_classes_mixed_returns()",
                "foo->bar: new bar()",
                "bar->foo: ret",
                "foo-->bar:call_me_with_return",
                "bar-->foo: ret  int",
                "foo->baz: new baz()",
                "baz-->foo: ret",
                "foo->baz:call_me_with_return()",
                "baz-->foo:ret  int",
                "foo-->foo:"));
        try {
            Root root = new Root(Paths.get(ROOT_DIR));
            root.start(CLASS_NAME, METHOD_NAME, METHOD_PARAMS);
            Assertions.assertIterableEquals(expected, diagram.getStringToPaint());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
