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
import java.util.ArrayList;
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
     * @param n node, usually compilation unit. But could be any node that may have childnodes.
     * @param methodName
     * @param methodArgs
     * @return
     */
    public MethodDeclaration recursivelySearchMethodDec(Node n, String methodName, List<String> methodArgs) {
        if (nodeNameEquals(n, methodName) && paramListEquals(n, methodArgs)) {
            MethodDeclaration md = (MethodDeclaration) n;
            return md;
        }
        List<Node> children = n.getChildNodes();
        MethodDeclaration res = null;
        for (Node child: children) {
            res = recursivelySearchMethodDec(child, methodName, methodArgs);
        }
        return res;
    }

    public MethodDeclaration recursivelySearchMethodDec(Node n, String methodName, NodeList<Parameter> methodArgs) {
        if (nodeNameEquals(n, methodName) && paramListEquals(n, methodArgs)) {
            MethodDeclaration md = (MethodDeclaration) n;
            return md;
        }
        List<Node> children = n.getChildNodes();
        MethodDeclaration res = null;
        for (Node child: children) {
            res = recursivelySearchMethodDec(child, methodName, methodArgs);
        }
        return res;
    }

    private boolean nodeNameEquals(Node n, String targetClassName) {
        if(n.getClass().getSimpleName().equals("MethodDeclaration")) {
            MethodDeclaration md = (MethodDeclaration) n;
            return md.getName().equals(targetClassName);
        } else {
            return false;
        }
    }

    private boolean paramListEquals(Node n, List<String> targetMethodParamList) {
        if (n.getClass().getSimpleName().equals("MethodDeclaration")) {
            MethodDeclaration md = (MethodDeclaration) n;
            NodeList<Parameter> parameters = md.getParameters();
            this.compareNodeList(parameters, targetMethodParamList);
        }
    }

    private boolean paramListEquals(Node n, NodeList<Parameter> targetMethodParamList) {

    }

    /**
     * given to list of parameters, deep compares them to see if they contain the same
     * type. eg.
     * @param nl1 nodelist-1 to compare
     * @param nl2 nodelist-2 to compare
     * @return
     */
    private boolean compareNodeList(NodeList<Parameter> nl1, NodeList<Parameter> nl2) {
        if (nl1.size() != nl2.size()) {
            return false;
        }
        for (int i=0; i<nl1.size(); i++) {
            if (nl1.get(i).getType() != nl2.get(i).getType()) {
                return false;
            }
        }
        return true;
    }

    private List<String> convertNodeListToArray(NodeList<Parameter> nodeList) {
        List<String> ret = new ArrayList<String>();
        for(Parameter p: nodeList) {
            String paramStr = p.getTypeAsString();
            ret.add(paramStr);
        }
        return ret;
    }

}
