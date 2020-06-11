package logic;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.utils.SourceRoot;
import libs.Utils;

public class MethodHandler {
    private SourceRoot root;
    private String currClass;
    private String currMethod;
    private MethodDeclaration methodNode;

    public MethodHandler(SourceRoot root, String className, String methodName) {
        this.root = root;
        this.currClass = className;
        this.currMethod = methodName;
        this.methodNode = Utils.getMethodDeclarationFromClass(this.root, currClass, currMethod);
    }






}
