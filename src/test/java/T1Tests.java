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

public class T1Tests {
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
    public void t1_calls_one_method() {
        ROOT_DIR = "src/test/java/t1/";
        CLASS_NAME = "foo";
        METHOD_NAME = "calls_one_method";
        METHOD_PARAMS.clear();
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList(
            "title calls_one_method()",
            "autoactivation on",
            "participant foo",
            "[->foo:calls_one_method()",
            "foo->foo:bazz()",
            "foo-->foo: ret  int",
            "foo->foo:bazz(int, String)",
            "foo-->foo: ret  int",
            "foo->foo:biff()",
            "foo-->foo:",
            "foo-->foo: ret  String"));
        try {
            Root root = new Root(Paths.get(ROOT_DIR));
            root.start(CLASS_NAME, METHOD_NAME, METHOD_PARAMS);
            Assertions.assertIterableEquals(expected, diagram.getStringToPaint());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t1_calls_two_methods() {
        ROOT_DIR = "src/test/java/t1/";
        CLASS_NAME = "foo";
        METHOD_NAME = "calls_two_methods";
        METHOD_PARAMS.clear();
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList(
                "title calls_two_methods()",
                "autoactivation on",
                "participant foo",
                "[->foo:calls_two_methods()",
                "foo->foo:calls_one_method()",
                "foo->foo:bazz()",
                "foo-->foo: ret  int",
                "foo->foo:bazz(int, String)",
                "foo-->foo: ret  int",
                "foo->foo:biff()",
                "foo-->foo:",
                "foo-->foo: ret  String",
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
