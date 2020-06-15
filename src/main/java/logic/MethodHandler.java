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
    private MyTypeSolver myTypeSolver;

    public MethodHandler(SourceRoot root, String className, String methodName, List<String> methodParams ,MyTypeSolver myTypeSolver) {
        this.diagram = SequenceDiagram.getSequenceDiagram();
        this.root = root;
        this.currClass = className;
        this.currMethod = methodName;
        this.methodParams = methodParams;
        this.methodNode = Utils.getMethodDeclarationFromClass(this.root, this.currClass, this.currMethod, this.methodParams);
        this.returnType = methodNode.getType().toString();
        this.myTypeSolver = myTypeSolver;

    }

    public void handleMethod() {
        try {
            NodeList<Statement> statements = methodNode.getBody().get().getStatements();
            Validator.validateNotNull(statements);
            BlockHandler blockHandler = new BlockHandler(this.currClass, this.root, this.myTypeSolver);
            blockHandler.handleStatements(statements, true);
        } catch (Exception e) {
            System.out.println("hmmmm?");
        }

        //if(this.returnType != null)
            //this.diagram.addReturn(currClass, currClass, this.returnType);
    }

    public String getReturnType(){
        return this.returnType;
    }






}
