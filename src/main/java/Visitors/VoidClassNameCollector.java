package Visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;
import java.util.Set;

/**
 * collects all of the class names in the CompilationUnit
 */
public class VoidClassNameCollector extends VoidVisitorAdapter<Set<String>> {

    @Override
    public void visit(ClassOrInterfaceDeclaration d, Set<String> collector) {
        super.visit(d, collector);
        collector.add(d.getNameAsString());
    }
}
