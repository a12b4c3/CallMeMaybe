package libs;

/** Enums for subclasses of the Statement JavaParser AST Class.
 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/3.5.0/com/github/javaparser/ast/stmt/Statement.html
 */
public enum Stmt implements StrConst {
    ASSERT("AssertStmt"),
    BLOCK("BlockStmt"),
    BREAK("BreakStmt"),
    CONTINUE("ContinueStmt"),
    DO("DoStmt"),
    EMPTY("EmptyStmt"),
    CONSTRUCTOR("ExplicitConstructorInvocationStmt"),   // support this
    EXPRESSION("ExpressionStmt"),                       // support this
    FOREACH("ForEachStmt"),                             // support this
    FOR("ForStmt"),                                     // support this
    IF("IfStmt"),                                       // support this
    LABELED("LabeledStmt"),
    LOCAL_CLASS_DEC("LocalClassDeclarationStmt"),
    RETURN("ReturnStmt"),                               // support this
    SWITCH_ENTRY("SwitchEntryStmt"),
    SWITCH("SwitchStmt"),
    SYNC("SynchronizedStmt"),
    THROW("ThrowStmt"),
    TRY("TryStmt"),
    UNPARSABLE("UnparsableStmt"),
    WHILE("WhileStmt");                                 // support this

    private String type;

    Stmt(String type) {
        this.type = type;
    }


    public String toString() {
        return this.type;
    }
}
