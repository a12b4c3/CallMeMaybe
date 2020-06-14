package libs;

public enum LiteralExpr {
    BOOL("BooleanLiteralExpr"),
    CHAR("CharLiteralExpr"),
    DOUB("DoubleLiteralExpr"),
    INTG("IntegerLiteralExpr"),
    LONG("LongLiteralExpr"),
    STRN("StringLiteralExpr"),
    NULL("NullLiteralExpr");

    String type;
    LiteralExpr(String type) {
        this.type = type;
    }

    public String getType() {
        return this.toString();
    }

    public static String getSimpleJavaName(String str) {
        switch (str) {
            case "BooleanLiteralExpr": return "boolean";
            case "CharLiteralExpr": return "char";
            case "DoubleLiteralExpr": return "double";
            case "IntegerLiteralExpr": return "int";
            case "LongLiteralExpr": return "long";
            case "StringLiteralExpr": return "String";
            case "NullLiteralExpr": return "null";
            default: throw new RuntimeException("Unexpected LiteralExpr type: " + str);
        }
    }

}

