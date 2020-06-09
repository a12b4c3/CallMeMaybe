package logic;

import Visitors.GenericMethodFinder;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.utils.SourceRoot;
import libs.SequenceDiagram;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Root {
    Path path;
    SourceRoot sourceRoot;
    List<ParseResult<CompilationUnit>> results;
    List<CompilationUnit> compilations;
    String className;
    String methodName;
    SequenceDiagram diagram;

    public Root(Path path) throws IOException {
        this.path = path;
        this.sourceRoot = new SourceRoot(this.path);
        this.results = this.sourceRoot.tryToParse();
        this.compilations = this.sourceRoot.getCompilationUnits();
        this.diagram = SequenceDiagram.getSequenceDiagram();
    }

    public void start(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
        GenericMethodFinder mfinder = new GenericMethodFinder();
        CompilationUnit target = null;
        for(CompilationUnit cu: this.compilations) {
            if (cu.getStorage().get().getFileName().equals(className + ".java")) {
                target = cu;
                break;
            }
        }
        Validator.validateNotNull(target);
        MethodDeclaration initMethod = mfinder.visit(target, methodName);
        NodeList<Statement> statements = initMethod.getBody().get().getStatements();
        Validator.validateNotNull(statements);
        this.initializeDiagram();
        this.evaluateStatements(statements);
    }

    private void evaluateStatements(NodeList<Statement> statements) {
        for(Statement s: statements) {
            ExprHandler handler = new ExprHandler(this.className);
            handler.handle(s);
        }
    }

    private void initializeDiagram() {
        this.diagram.clear();
        this.diagram.addTitle(this.methodName + "()");
        this.diagram.autoActivationOn();
        this.diagram.addParticipant(this.className);
        this.diagram.addEntryPoint(this.className, this.methodName);
    }

    public Path getPath() {
        return path;
    }

    public SourceRoot getSourceRoot() {
        return sourceRoot;
    }


    public List<ParseResult<CompilationUnit>> getResults() {
        return results;
    }

    public List<CompilationUnit> getCompilations() {
        return compilations;
    }

}
