package logic;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.utils.SourceRoot;
import libs.Utils;

import java.util.List;

public class MethodHandler {
    private SourceRoot root;
    private String currClass;
    private String currMethod;
    private List<String> methodParams;
    private MethodDeclaration methodNode;

    public MethodHandler(SourceRoot root, String className, String methodName, List<String> methodParams) {
        this.root = root;
        this.currClass = className;
        this.currMethod = methodName;
        this.methodParams = methodParams;
        this.methodNode = Utils.getMethodDeclarationFromClass(this.root, this.currClass, this.currMethod, this.methodParams);
    }

    public void handleMethod() {
        NodeList<Statement> statements = methodNode.getBody().get().getStatements();
        Validator.validateNotNull(statements);
        BlockHandler blockHandler = new BlockHandler(this.currClass, this.root);
        blockHandler.handleStatements(statements);
    }






}
