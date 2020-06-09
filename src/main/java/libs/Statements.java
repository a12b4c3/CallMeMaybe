package libs;

public enum Statements {
    ASSERT("AssertStmt"),
    BLOCK("BlockStmt"),
    BREAK("BreakStmt"),
    CONTINUE("ContinueStmt"),
    DO("DoStmt"),
    EMPTY("EmptyStmt"),
    CONSTRUCTOR("ExplicitConstructorInvocationStmt"),
    EXPRESSION("ExpressionStmt"),
    FOREACH("ForEachStmt"),
    FOR("ForStmt"),
    IF("IfStmt"),
    LABELED("LabeledStmt"),
    LOCAL_CLASS_DEC("LocalClassDeclarationStmt"),
    RETURN("ReturnStmt"),
    SWITCH_ENTRY("SwitchEntryStmt"),
    SWITCH("SwitchStmt"),
    SYNC("SynchronizedStmt"),
    THROW("ThrowStmt"),
    TRY("TryStmt"),
    UNPARSEABLE("UnparsableStmt"),
    WHILE("WhileStmt");

    private String type;

    Statements(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
