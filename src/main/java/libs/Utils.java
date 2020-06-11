package libs;

import Visitors.GenericMethodFinder;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.utils.SourceRoot;
import logic.Validator;

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

    public static MethodDeclaration getMethodDeclarationFromClass(SourceRoot root, String className, String methodName) {
        CompilationUnit cu = Utils.retrieveTargetCU(root.getCompilationUnits(), className);
        GenericMethodFinder finder = new GenericMethodFinder();
        return finder.visit(cu, methodName);
    }
}
