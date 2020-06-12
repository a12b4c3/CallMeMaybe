package model;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.MethodCallExpr;
import libs.Utils;

import java.util.ArrayList;
import java.util.List;

public class Method implements model.Node {
    private String className;
    private String methodName;
    private String returnType;
    private String methodParams;
    private List<MethodCallExpr> methodCalls = new ArrayList<>();

    public Method(MethodDeclaration md, String className) {
        this.className = className;
        this.methodName = md.getName().toString();
        this.returnType = md.getTypeAsString();
        this.methodParams = Utils.nodeListParamsToArrayList(md.getParameters());
        Utils.recursiveMethodCallFinder(md, methodCalls);
    }


    @Override
    public void build() {
        for(MethodCallExpr mc: this.methodCalls) {
            // todo
            // look at each methodcallexpr and get all of the info necessary to
        }
    }
}
