package logic;

import com.github.javaparser.ast.stmt.Statement;

public class ExprHandler {
    String srcClass;

    public ExprHandler(String srcClass) {

    }
    /* given a statement

     */
    public void handle(Statement stmt) {
        String stmtClassName = stmt.getClass().getSimpleName();
        if (stmtClassName.equals("ExpressionStmt")) {

        } else if (stmtClassName.equals("ForStmt")) {

        } else if (stmtClassName.equals("IfStmt")) {

        } else if (stmtClassName.equals("ReturnStmt")) {

        } else if (stmtClassName.equals("WhileStmt")) {

        } else {
            System.out.println("Unsupported STMT type: " + stmtClassName);
        }
    }
}
