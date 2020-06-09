package Visitors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


public class GenericMethodFinder extends GenericVisitorAdapter<MethodDeclaration, String> {

    /* when it encounters a MethodDeclaration node, it checks to see if the name of the method matches the arg.
     * input:
     *  MethodDeclaration md: a node of type MethodDeclaration
     *  String arg: a String of the desired method name.
     * output:
     *  returns the MethodDeclaration node with the specified name, arg.
     */
    @Override
    public MethodDeclaration visit(MethodDeclaration md, String arg) {
         super.visit(md, arg);
         if (md.getName().asString().equals(arg)) {
             return md;
         }
        throw new RuntimeException("Couldn't find method: " + arg);
    }

}
