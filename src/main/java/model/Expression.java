package model;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;


public class Expression implements Node{
    private String currClassName;
    private String currMethodName;

    private MethodDeclaration toMethodNode;
    private String toMethodClassName;
    private String toMethodParams;

    public Expression(MethodCallExpr callExpr) {

    }

    @Override
    public void build() {

    }
}
