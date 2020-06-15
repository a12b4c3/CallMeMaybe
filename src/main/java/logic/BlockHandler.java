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

    public void handleStatements(NodeList<Statement> statements, boolean isNestedif) {
        for(Statement s: statements) {
            String statementClass = s.getClass().getSimpleName();
            if (statementClass.equals(Stmt.CONSTRUCTOR.toString())) {

            } else if (statementClass.equals(Stmt.EXPRESSION.toString())) {
                ExpressionHandler ehandler = new ExpressionHandler(currClass, this.root,this.myTypeSolver);
                ehandler.handle((ExpressionStmt) s);
            } else if (statementClass.equals(Stmt.FOREACH.toString())) {
                this.diagram.beginLoop("for each elements");
                handleStatements(((BlockStmt) ((ForStmt) s).getBody()).getStatements(), false);
                this.diagram.endLoop();
            } else if (statementClass.equals(Stmt.FOR.toString()) || statementClass.equals(Stmt.WHILE.toString())) {
                this.diagram.beginLoop(((ForStmt) s).getCompare().toString());
                handleStatements(((BlockStmt) ((ForStmt) s).getBody()).getStatements(), false);
                this.diagram.endLoop();
            } else if (statementClass.equals(Stmt.IF.toString())) {
                if (isNestedif) {
                    handleIf(s, true);
                } else {
                    handleIf(s, false);
                }
            } else if (statementClass.equals(Stmt.BLOCK.toString())) {
                this.diagram.endConditions();
            } else {
                System.out.println("Unsupported statement class: " + statementClass);
            }
        }
    }

    private void handleIf (Statement s, boolean isNestedIfStart) {
        if (!this.diagram.elseStatement || isNestedIfStart) {
            if (!this.diagram.elseStatement) {
                this.diagram.beginIf(((IfStmt) s).getCondition().toString(), true);
            } else {
                this.diagram.beginIf(((IfStmt) s).getCondition().toString(), false);
            }
            handleStatements(((BlockStmt) ((IfStmt) s).getThenStmt()).getStatements(), true);
            if (((IfStmt) s).getElseStmt().isPresent()) {
                handleElse(((IfStmt) s).getElseStmt().get());
            }
            this.diagram.endConditions();
        } else {
            handleElse(s);
        }
    }

    private void handleElse (Statement s) {
        NodeList<Statement> ns = new NodeList<Statement>();
        int num = s.getChildNodes().size();
        if (num != 1) {
            this.diagram.moreElseIf(((IfStmt) s).getCondition().toString());
            handleStatements(((BlockStmt) ((IfStmt) s).getThenStmt()).getStatements(), true); // nested if detected here
            if (((IfStmt) s).getElseStmt().get().getChildNodes().size() == 1) {
                this.diagram.moreElseIf("");
                ns.add((Statement) ((IfStmt) s).getElseStmt().get().getChildNodes().get(0));
            } else {
                ns.add(((IfStmt) s).getElseStmt().get());
            }
        } else {
            this.diagram.moreElseIf("");
            ns.add(((BlockStmt) s).getStatements().get(0));
        }
        handleStatements(ns, false);
    }
}


