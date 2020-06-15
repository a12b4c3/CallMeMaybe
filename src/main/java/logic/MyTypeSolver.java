package logic;

import Visitors.GenericMethodFinder;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.javaparser.Navigator;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.utils.SourceRoot;
import libs.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class MyTypeSolver {
    private Path path;
    private SourceRoot sourceRoot;
    private String targetClass;
    private String targetMethod;
    private List<String> methodParams;
    List<CompilationUnit> compilations;
    private CompilationUnit targetCu;
    MethodDeclaration initMethod = null;

    public MyTypeSolver(Path path,String targetClass ,String targetMethod, List<String> methodParams) {
        this.path = path;
        this.targetClass = targetClass;
        this.targetMethod = targetMethod;
        this.methodParams = methodParams;
        try{
            startConfiguration();
        }catch (IOException e) {
            System.out.println("file not found");
        }
    }



    private void startConfiguration() throws IOException {

        TypeSolver typeSolver = new CombinedTypeSolver(new ReflectionTypeSolver());
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
        /**StaticJavaParser
                .getConfiguration()
                .setSymbolResolver(symbolSolver);**/

        ParserConfiguration parserConfiguration = new ParserConfiguration().setSymbolResolver(symbolSolver);

        this.sourceRoot = new SourceRoot(this.path);
        this.sourceRoot.setParserConfiguration(parserConfiguration);
        List<ParseResult<CompilationUnit>> results = this.sourceRoot.tryToParse();
        this.compilations = this.sourceRoot.getCompilationUnits();

        setTargetCu();

    }


    public String tryResolveTypeInMethod(String toBeSolved){
        HashMap<String,String> types = new HashMap<String, String>();

        try {
            this.initMethod = Utils.getMethodDeclarationFromClass(this.sourceRoot, this.targetClass, this.targetMethod, this.methodParams);
            this.initMethod.findAll(AssignExpr.class).forEach(ae -> {
                ResolvedType resolvedType = ae.calculateResolvedType();
                types.put(ae.getTarget().toString(),resolvedType.describe());
            });


            this.initMethod.findAll(VariableDeclarationExpr.class).forEach(vde -> {
                types.put(vde.getVariables().get(0).getName().toString(), vde.getVariables().get(0).getType().toString());
                System.out.println(vde.calculateResolvedType().describe());
            });

           String type =  this.initMethod.getParameterByName(toBeSolved).get().getType().toString();
            if(type != null)
                types.put(toBeSolved,type);
        }catch (Exception e) {
            System.out.println("method is not found or construction is not found");
        }

        return types.get(toBeSolved);
    }

    public String tryResolveTypeInClass(String toBeSolved){

        HashMap<String,String> types = new HashMap<String,String>();
            this.targetCu.findAll(FieldDeclaration.class).forEach(fd -> {
                types.put(fd.getVariables().get(0).getName().toString(),fd.getVariables().get(0).getType().toString());
            });

        return types.get(toBeSolved);
    }


    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public void setTargetCu() {
        for(CompilationUnit cu: this.compilations) {
            if (cu.getStorage().get().getFileName().equals(this.targetClass + ".java")) {
                this.targetCu = cu;
                break;
            }
        }
    }

    public void setTargetMethod(String targetMethod, List<String> methodParams){
        this.targetMethod = targetMethod;
        this.methodParams = methodParams;
    }

    public String getTargetMethod(){
        return this.targetMethod;
    }

    public List<String> getTargetMethodParam(){
        return this.methodParams;
    }

    public MethodDeclaration tryGetInitMethod(String method){
        return Utils.getMethodDeclarationFromClass(this.sourceRoot, this.targetClass, method, this.methodParams);
    }


}
