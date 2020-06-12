package logic;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.SourceRoot;
import libs.SequenceDiagram;
import libs.Stmt;


public class BlockHandler extends VoidVisitorAdapter<Void> {
    String currClass;
    SequenceDiagram diagram;
    SourceRoot root;

    public BlockHandler(String currClass, SourceRoot root) {
        this.diagram = SequenceDiagram.getSequenceDiagram();
        this.currClass = currClass;
        this.root = root;
    }

    public void handleStatements(NodeList<Statement> statements) {
        for(Statement s: statements) {
            String statementClass = s.getClass().getSimpleName();
            if (statementClass.equals(Stmt.CONSTRUCTOR.toString())) {

            } else if (statementClass.equals(Stmt.EXPRESSION.toString())) {
                ExpressionHandler ehandler = new ExpressionHandler(currClass, this.root);
                ehandler.handle((ExpressionStmt) s);
            } else if (statementClass.equals(Stmt.FOREACH.toString())) {
                this.diagram.beginLoop("for each");
                handleStatements(((BlockStmt) ((ForStmt) s).getBody()).getStatements());
                this.diagram.endConditions();
            } else if (statementClass.equals(Stmt.FOR.toString()) || statementClass.equals(Stmt.WHILE.toString())) {
                this.diagram.beginLoop(((ForStmt) s).getCompare().toString());
                handleStatements(((BlockStmt) ((ForStmt) s).getBody()).getStatements());
                this.diagram.endConditions();
            } else if (statementClass.equals(Stmt.IF.toString())) {
                this.diagram.beginIf(((IfStmt) s).getCondition().toString());
                handleStatements(((BlockStmt) ((IfStmt) s).getThenStmt()).getStatements());
                handleStatements(((BlockStmt) ((IfStmt) s).getThenStmt()).getStatements());
                this.diagram.endConditions();
            // TODO
            } else {
                System.out.println("Unsupported statement class: " + statementClass);
            }
        }
    }
}
