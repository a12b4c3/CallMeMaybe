package logic;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import libs.SequenceDiagram;


public class ExprHandler extends VoidVisitorAdapter<Void> {
    String srcClass;
    SequenceDiagram diagram;

    public ExprHandler(String srcClass) {
        this.diagram = SequenceDiagram.getSequenceDiagram();
    }

    public void handleStatements(NodeList<Statement> statements) {
        for(Statement s: statements) {

        }
    }

    @Override
    public void visit(ExplicitConstructorInvocationStmt s, Void arg) {

    }

    @Override
    public void visit(ExpressionStmt s, Void arg) {

    }

    @Override
    public void visit(ForEachStmt s, Void arg) {

    }
}
