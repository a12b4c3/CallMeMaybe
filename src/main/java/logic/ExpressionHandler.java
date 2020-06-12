package logic;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.utils.SourceRoot;
import libs.Expr;
import libs.LiteralExpr;
import libs.SequenceDiagram;
import libs.Utils;

import java.util.ArrayList;
import java.util.List;

public class ExpressionHandler {
    SequenceDiagram diagram;
    String currClass;
    SourceRoot root;

    public ExpressionHandler(String currClass, SourceRoot root) {
        this.diagram = SequenceDiagram.getSequenceDiagram();
        this.currClass = currClass;
        this.root = root;
    }

    /**
     * given a statement, finds all of the instances of method calls with in that statement.
     * @param stmt the statement node
     */
    public void handle(ExpressionStmt stmt) {
        Expression exp = stmt.getExpression();
        List<MethodCallExpr> collector = new ArrayList<MethodCallExpr>();
        recursiveMethodCallFinder(exp, collector);
        for(MethodCallExpr mCallExpr: collector) {
            handle(mCallExpr);
        }
    }

    private void handle(MethodCallExpr mCallExpr) {
        if (Utils.callInScope(mCallExpr, Expr.THIS)) {
            List<String> mparams = this.getParamListFromMethodCall(mCallExpr);
            diagram.addCallAToB(currClass, currClass, mCallExpr.getNameAsString(), Utils.listToString(mparams));
            MethodHandler mhandler = new MethodHandler(this.root, this.currClass, mCallExpr.getName().toString(), mparams);
            Validator.validateNotNull(mhandler);
            mhandler.handleMethod();
        } else {
            // figure out what the scope is
            // check if scope is in the current project
            // create new node and jump to
        }
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

    private List<String> getParamListFromMethodCall(MethodCallExpr mcall) {
        List<String> ret = new ArrayList<>();
        for(int i=0; i<mcall.getArguments().size(); i++) {
            ret.add(LiteralExpr.getSimpleJavaName(mcall.getArgument(i).getClass().getSimpleName()));
        }
        return ret;
    }
}
