package logic;

import Visitors.GenericMethodFinder;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

public class MyTypeSolver {
    private String path;
    private String targetMethod;
    private HashMap<String, String> types;

    public MyTypeSolver(String path,String targetMethod) {
        this.path = path;
        this.targetMethod = targetMethod;
        this.types = new HashMap<String, String>();
    }



    public void startConfiguration() throws FileNotFoundException {

        TypeSolver typeSolver = new CombinedTypeSolver(new ReflectionTypeSolver());
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
        StaticJavaParser
                .getConfiguration()
                .setSymbolResolver(symbolSolver);

        GenericMethodFinder mfinder = new GenericMethodFinder();
        CompilationUnit cu = StaticJavaParser.parse(new File(this.path));
        MethodDeclaration initMethod = mfinder.visit(cu, this.targetMethod);

        cu.findAll(FieldDeclaration.class).forEach(fd -> {
            types.put(fd.getVariables().get(0).getName().toString(),fd.getVariables().get(0).getType().toString());
        });

        initMethod.findAll(AssignExpr.class).forEach(ae -> {
            ResolvedType resolvedType = ae.calculateResolvedType();
            types.put(ae.getTarget().toString(),resolvedType.describe());
        });


       initMethod.findAll(VariableDeclarationExpr.class).forEach(vde -> {
           types.put(vde.getVariables().get(0).getName().toString(), vde.getVariables().get(0).getType().toString());
       });




    }

    public HashMap<String,String> getTypes() {
        return this.types;
    }


}
