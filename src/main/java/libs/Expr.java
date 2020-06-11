package libs;

/** Enums for subclasses of the Expression JavaParser AST Class.
 *  https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/3.2.7/com/github/javaparser/ast/expr/Expression.html
 */
public enum Expr implements StrConst {
    ANNOTATION("AnnotationExpr"),
    ARRAY_ACCESS("ArrayAccessExpr"),
    ARRAY_CREATION("ArrayCreationExpr"),
    ARRAY_INITIALIZER("ArrayInitializerExpr"),
    ASSIGN("AssignExpr"),
    BINARY("BinaryExpr"),
    CAST("CastExpr"),
    CLASS("ClassExpr"),
    CONDITIONAL("ConditionalExpr"),
    ENCLOSED("EnclosedExpr"),
    FIELD_ACCESS("FieldAccessExpr"),
    INSTANCE_OF("InstanceOfExpr"),
    LAMBDA("LambdaExpr"),
    LITERAL("LiteralExpr"),
    METHOD_CALL("MethodCallExpr"),                  // support this
    METHOD_REF("MethodReferenceExpr"),
    NAME("NameExpr"),
    OBJECT_CREATION("ObjectCreationExpr"),
    SUPER("SuperExpr"),
    THIS("ThisExpr"),                               // support this
    TYPE("TypeExpr"),
    UNARY("UnaryExpr"),
    VARIABLE_DEC("VariableDeclarationExpr");

    private String type;

    Expr(String type) { this.type = type; }

    public String toString() {
        return this.type;
    }
}
