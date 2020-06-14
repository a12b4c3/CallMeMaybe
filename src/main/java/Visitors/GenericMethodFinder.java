package Visitors;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

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
     * @param methodParams
     * @return
     */
    public MethodDeclaration recursivelySearchMethodDec(Node n, String methodName, List<String> methodParams) {
        if (nodeNameEquals(n, methodName) && paramListEquals(n, methodParams)) {
            MethodDeclaration md = (MethodDeclaration) n;
            return md;
        }
        List<Node> children = n.getChildNodes();
        MethodDeclaration res = null;
        for (Node child: children) {
            res = recursivelySearchMethodDec(child, methodName, methodParams);
            if (res != null) { return res; }
        }
        return res;
    }

    private boolean nodeNameEquals(Node n, String targetClassName) {
        if(n.getClass().getSimpleName().equals("MethodDeclaration")) {
            MethodDeclaration md = (MethodDeclaration) n;
            return md.getName().toString().equals(targetClassName);
        } else {
            return false;
        }
    }

    private boolean paramListEquals(Node n, List<String> params) {
        if (n.getClass().getSimpleName().equals("MethodDeclaration")) {
            MethodDeclaration md = (MethodDeclaration) n;
            List<String> nparams = new ArrayList<String>();
            for (int i = 0; i<md.getParameters().size(); i++) {
//                nparams.add(md.getParameter(i).getTypeAsString());
                nparams.add(md.getParameter(i).getType().toString());
            }
            return params.equals(nparams);
        }
        return false;
    }

}