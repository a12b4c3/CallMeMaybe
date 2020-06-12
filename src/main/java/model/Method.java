package model;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.MethodCallExpr;
import libs.Expr;
import libs.Utils;

import java.util.ArrayList;
import java.util.List;

public class Method {
    private String className;
    private String methodName;
    private String returnType;
    private String methodParams;
    private List<MethodCallExpr> methodCalls = new ArrayList<>();

    public Method(MethodDeclaration md, String className) {
        this.className = className;
        this.methodName = md.getName().toString();
        this.returnType = md.getTypeAsString();
        this.methodParams = this.nodeListParamsToArrayList(md.getParameters());
        this.recursiveMethodCallFinder(md, methodCalls);
    }

    private String nodeListParamsToArrayList(NodeList<Parameter> nodeList) {
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < nodeList.size(); i++) {
            ret.add(nodeList.get(i).getTypeAsString());
        }
        return Utils.listToString(ret);
    }

    private void recursiveMethodCallFinder(Node e, List<MethodCallExpr> collector) {
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
}
