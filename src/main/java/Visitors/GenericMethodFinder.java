package Visitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import logic.MethodHandler;

import java.lang.reflect.Method;
import java.util.List;


public class GenericMethodFinder extends GenericVisitorAdapter<MethodDeclaration, String> {

    /**
     * when it encounters a MethodDeclaration node, it checks to see if the name of the method matches the arg.
     * @param md node of type MethodDeclaration
     * @param arg string of the desired method name
     * @return the MethodDeclaration node with the specified name ie., of variable arg.
     */
    // todo delete this
    @Override
    public MethodDeclaration visit(MethodDeclaration md, String arg) {
         super.visit(md, arg);
         if (md.getName().asString().equals(arg)) {
             return md;
         }
        throw new RuntimeException("Couldn't find method: " + arg);
    }

    /**
     * walks a tree and finds the method with proper methodname and correct primitive type arguments
     * @param n
     * @param methodName
     * @param methodArgs
     * @return
     */
    public MethodDeclaration recursivelySearchMethodDec(Node n, String methodName, List<String> methodArgs) {
        if (nodeNameEquals(n, methodName) ) {
            // todo
        }
        return null;
    }

    private boolean nodeNameEquals(Node n, String target) {
        if(n.getClass().getSimpleName().equals("MethodDeclaration")) {
            MethodDeclaration md = (MethodDeclaration) n;
            NodeList<Parameter> parameters = md.getParameters();
            return md.getName().equals(target);
        } else {
            return false;
        }
    }

}
