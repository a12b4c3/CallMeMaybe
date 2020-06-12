package logic;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.utils.SourceRoot;
import libs.SequenceDiagram;
import libs.Utils;

import java.util.List;

public class MethodHandler {
    SequenceDiagram diagram;
    private SourceRoot root;
    private String currClass;
    private String currMethod;
    private List<String> methodParams;
    private MethodDeclaration methodNode;
    private String returnType;

    public MethodHandler(SourceRoot root, String className, String methodName, List<String> methodParams) {
        this.diagram = SequenceDiagram.getSequenceDiagram();
        this.root = root;
        this.currClass = className;
        this.currMethod = methodName;
        this.methodParams = methodParams;
        this.methodNode = Utils.getMethodDeclarationFromClass(this.root, this.currClass, this.currMethod, this.methodParams);
        this.returnType = methodNode.getType().toString();
        // this.returnType = methodNode.getTypeAsString();
    }

    public void handleMethod() {
        NodeList<Statement> statements = methodNode.getBody().get().getStatements();
        Validator.validateNotNull(statements);
        BlockHandler blockHandler = new BlockHandler(this.currClass, this.root);
        blockHandler.handleStatements(statements);
        this.diagram.addReturn(currClass, currClass, this.returnType);
    }






}
