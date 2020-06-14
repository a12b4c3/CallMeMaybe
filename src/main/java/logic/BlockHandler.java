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
    MyTypeSolver myTypeSolver;

    public BlockHandler(String currClass, SourceRoot root, MyTypeSolver myTypeSolver) {
        this.diagram = SequenceDiagram.getSequenceDiagram();
        this.currClass = currClass;
        this.root = root;
        this.myTypeSolver = myTypeSolver;
    }

    public void handleStatements(NodeList<Statement> statements) {
        for(Statement s: statements) {
            String statementClass = s.getClass().getSimpleName();
            if (statementClass.equals(Stmt.CONSTRUCTOR.toString())) {

            } else if (statementClass.equals(Stmt.EXPRESSION.toString())) {
                ExpressionHandler ehandler = new ExpressionHandler(currClass, this.root,this.myTypeSolver);
                ehandler.handle((ExpressionStmt) s);
            } else if (statementClass.equals(Stmt.FOREACH.toString())) {

                this.diagram.beginLoop("for each elements");
                handleStatements(((BlockStmt) ((ForStmt) s).getBody()).getStatements());
                this.diagram.endLoop();
            } else if (statementClass.equals(Stmt.FOR.toString()) || statementClass.equals(Stmt.WHILE.toString())) {
                this.diagram.beginLoop(((ForStmt) s).getCompare().toString());
                handleStatements(((BlockStmt) ((ForStmt) s).getBody()).getStatements());
                this.diagram.endLoop();
            } else if (statementClass.equals(Stmt.IF.toString())) {
                handleIfElse(s);
            } else if (statementClass.equals(Stmt.BLOCK.toString())) {
                this.diagram.endConditions();

            } else {
                System.out.println("Unsupported statement class: " + statementClass);
            }
        }
    }


    private void handleIfElse (Statement s) {
        int num = ((IfStmt) s).getElseStmt().get().getChildNodes().size();
        NodeList<Statement> ns = new NodeList<Statement>();
        if (!this.diagram.elseStatement) {
            this.diagram.beginIf(((IfStmt) s).getCondition().toString());
            handleStatements(((BlockStmt) ((IfStmt) s).getThenStmt()).getStatements()); // nested if detected here
            ns.add(((IfStmt) s).getElseStmt().get());
            handleStatements(ns);
            this.diagram.endConditions();
        } else {
            this.diagram.moreElseIf(((IfStmt) s).getCondition().toString());
            handleStatements(((BlockStmt) ((IfStmt) s).getThenStmt()).getStatements()); // nested if detected here
            if (num != 1) {
                ns.add(((IfStmt) s).getElseStmt().get());
            } else {
                this.diagram.moreElseIf("");
                ns.add(((BlockStmt) ((IfStmt) s).getElseStmt().get()).getStatements().get(0));
            }
            handleStatements(ns);
        }
    }

    private void handleNestedIf (Statement s) {

    }

    // 1 if-else if: more condition -> continue
        // write condition - ((IfStmt) s).getCondition().toString()
        // stuff in if - ((BlockStmt) ((IfStmt) s).getThenStmt()).getStatements()
        // ((IfStmt) s).getElseStmt().get().getChildNodes().size() > 1
        // go to 2 or 3

    // 2 else-else: more condition -> continue
        // write condition - ((IfStmt) s).getCondition().toString()
        // stuff in if - ((BlockStmt) ((IfStmt) s).getThenStmt()).getStatements()
        // ((IfStmt) s).getElseStmt().get().getChildNodes().size() > 1
        // go to 2 or 3

    // 3 (else)if-else: no more condition -> end
        // write condition - ((IfStmt) s).getCondition().toString()
        // stuff in if - ((BlockStmt) ((IfStmt) s).getThenStmt()).getStatements()
        // stuff in else - ((IfStmt) s).getElseStmt().get()
        // ((IfStmt) s).getElseStmt().get().getChildNodes() == 1
        // end

    // 4 nested if: extracted from a different way -> 1
        // go to 1
}


