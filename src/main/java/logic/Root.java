package logic;

import Visitors.GenericMethodFinder;
import Visitors.VoidClassNameCollector;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.utils.SourceRoot;
import libs.Client;
import libs.SequenceDiagram;
import libs.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Root {
    Path path;
    SourceRoot sourceRoot;
    List<ParseResult<CompilationUnit>> results;
    List<CompilationUnit> compilations;
    Set<String> projectClasses;
    String className;
    String methodName;
    List<String> methodParams;
    SequenceDiagram diagram;

    public Root(Path path) throws IOException {
        this.path = path;
        this.sourceRoot = new SourceRoot(this.path);
        this.results = this.sourceRoot.tryToParse();
        this.compilations = this.sourceRoot.getCompilationUnits();
        this.diagram = SequenceDiagram.getSequenceDiagram();
        this.projectClasses = new HashSet<String>();
    }

    public void start(String className, String methodName, List<String> methodParams) {
        this.className = className;
        this.methodName = methodName;
        this.methodParams = methodParams;
        this.findLocalClasses();
        this.initializeDiagram();
        MethodHandler mHandler = new MethodHandler(this.sourceRoot, this.className, this.methodName, this.methodParams);
        mHandler.handleMethod();
    }

    private void initializeDiagram() {
        this.diagram.clear();
        this.diagram.addTitle(this.methodName + "()");
        this.diagram.autoActivationOn();
        this.diagram.addParticipant(this.className);
        this.diagram.addEntryPoint(this.className, this.methodName);
    }

    public void finishDiagram() {
        Client c = new Client(null);
        String clientInput = this.diagram.finishDiagram();
        c.sendPostAndDraw(clientInput);
    }

    private void findLocalClasses() {
        VoidClassNameCollector classNameCollector = new VoidClassNameCollector();
        for(CompilationUnit cu: this.compilations) {
            classNameCollector.visit(cu, this.projectClasses);
        }
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
