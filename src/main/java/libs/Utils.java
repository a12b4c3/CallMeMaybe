package libs;

import Visitors.GenericMethodFinder;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.utils.SourceRoot;
import logic.Validator;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static boolean nodeIsType(Node node, StrConst type) {
        return node.getClass().getSimpleName().equals(type.toString());
    }

    public static boolean callInScope(MethodCallExpr call, Expr scope) {
        return call.getScope().get().getClass().getSimpleName().equals(scope.toString());
    }

    /**
     * returns the first compilation unit in the list matching the .java file with the target name.
     * @param cus list of compilation units
     * @param className CU of target .java file
     * @return CU belonging to the target .java file
     */
    public static CompilationUnit retrieveTargetCU(List<CompilationUnit> cus, String className) {
        for (CompilationUnit cu: cus) {
            if (cu.getStorage().get().getFileName().equals(className + ".java")) {
                return cu;
            }
        }
        return null;
    }

    /**
     * gets the MethodDeclaration node from a root file, with the specified classname, methodname, and
     * method parameters.
     * @param root
     * @param className
     * @param methodName
     * @param methodParams
     * @return
     */
    public static MethodDeclaration getMethodDeclarationFromClass(SourceRoot root, String className, String methodName, List<String> methodParams) {
        CompilationUnit cu = Utils.retrieveTargetCU(root.getCompilationUnits(), className);
        GenericMethodFinder finder = new GenericMethodFinder();
        return finder.recursivelySearchMethodDec(cu, methodName, methodParams);
    }

    /**
     * given an array of strings, converts it to a string and removes the brackets.
     * @param stringList the list of strings
     * @return returns a string without the brackets
     */
    public static String listToString(List<String> stringList) {
        String ret = stringList.toString();
        return ret.substring(1, ret.length()-1);
    }

    /**
     * given a javaparser node, will go through the tree and find all nodes of type
     * MethodCallExpr
     * @param e node
     * @param collector empty list to be added to, change only via sideeffect.
     */

    public static void recursiveMethodCallFinder(Node e, List<MethodCallExpr> collector) {
        if (e.getClass().getSimpleName().equals(Expr.METHOD_CALL.toString())) {
            collector.add((MethodCallExpr) e);
        }
        List<Node> children = e.getChildNodes();
        if (children.size() != 0) {
            for(Node child: children){
                recursiveMethodCallFinder(child, collector);
            }
        }
    }

    public static String nodeListParamsToArrayList(NodeList<Parameter> nodeList) {
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < nodeList.size(); i++) {
            ret.add(nodeList.get(i).getTypeAsString());
        }
        return Utils.listToString(ret);
    }
}
