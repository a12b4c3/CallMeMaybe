package logic;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.utils.SourceRoot;
import libs.Expr;
import libs.LiteralExpr;
import libs.SequenceDiagram;
import libs.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpressionHandler {
    SequenceDiagram diagram;
    String currClass;
    SourceRoot root;
    MyTypeSolver myTypeSolver;

    public ExpressionHandler(String currClass, SourceRoot root, MyTypeSolver myTypeSolver) {
        this.diagram = SequenceDiagram.getSequenceDiagram();
        this.currClass = currClass;
        this.root = root;
        this.myTypeSolver = myTypeSolver;
    }

    /**
     * given a statement, finds all of the instances of method calls with in that statement.
     * @param stmt the statement node
     */
    public void handle(ExpressionStmt stmt) {
        Expression exp = stmt.getExpression();
        List<MethodCallExpr> collector = new ArrayList<MethodCallExpr>();
        recursiveMethodCallFinder(exp, collector);
        for(MethodCallExpr mCallExpr: collector) {
            handle(mCallExpr);
        }
        String expClass = exp.getClass().getSimpleName();
        if(expClass.equals(Expr.VARIABLE_DEC.toString())) {
            String[] strs = exp.toString().split(" ");
            String var = strs[1];
            String type = this.myTypeSolver.tryResolveTypeInMethod(var);
            if(strs.length > 2 && strs[3].equals("new")){
                //this is only invoked if the expression contains new XXX()
                handleConstruction(var,type);
            }

            //else then expression is just variable declaration. ignore it.
        }
    }

    private void handle(MethodCallExpr mCallExpr) {
        if (Utils.callInScope(mCallExpr, Expr.THIS)) {
            List<String> mparams = this.getParamListFromMethodCall(mCallExpr);
            diagram.addCallAToB(currClass, currClass, mCallExpr.getNameAsString(), Utils.listToString(mparams));

            MethodHandler mhandler = new MethodHandler(this.root, this.currClass, mCallExpr.getName().toString(),mparams,this.myTypeSolver);

            Validator.validateNotNull(mhandler);
            mhandler.handleMethod();

        } else {
            // figure out what the scope is
            // check if scope is in the current project
            // create new node and jump to
            String targetClass;
            String currMethod = this.myTypeSolver.getTargetMethod();
            List<String> currMethodParam = this.myTypeSolver.getTargetMethodParam();
            if(this.myTypeSolver.tryResolveTypeInMethod(mCallExpr.getScope().get().toString()) != null) {
                //check if the variable to be solved can be solved in Method level
                targetClass = this.myTypeSolver.tryResolveTypeInMethod(mCallExpr.getScope().get().toString());
                this.myTypeSolver.setTargetClass(targetClass);
                List<String> mparams = this.getParamListFromMethodCall(mCallExpr);
                this.myTypeSolver.setTargetMethod(mCallExpr.getNameAsString(),mparams);
                diagram.addCallAToB(currClass, targetClass, mCallExpr.getNameAsString(), Utils.listToString(mparams));
                MethodHandler mhandler = new MethodHandler(this.root, targetClass, mCallExpr.getName().toString(), mparams ,this.myTypeSolver);
                Validator.validateNotNull(mhandler);
                mhandler.handleMethod();
                this.diagram.addReturn(targetClass, currClass, mhandler.getReturnType());
            } else if(this.myTypeSolver.tryResolveTypeInClass(mCallExpr.getScope().get().toString()) != null) {
                //check if the variable to be solved is located in class fields level
                targetClass = this.myTypeSolver.tryResolveTypeInClass(mCallExpr.getScope().get().toString());
                this.myTypeSolver.setTargetClass(targetClass);
                List<String> mparams = this.getParamListFromMethodCall(mCallExpr);
                this.myTypeSolver.setTargetMethod(mCallExpr.getNameAsString(),mparams);
                diagram.addCallAToB(currClass, targetClass, mCallExpr.getNameAsString(), Utils.listToString(mparams));
                MethodHandler mhandler = new MethodHandler(this.root, targetClass, mCallExpr.getName().toString(), mparams ,this.myTypeSolver);
                Validator.validateNotNull(mhandler);
                mhandler.handleMethod();
                this.diagram.addReturn(targetClass, currClass, mhandler.getReturnType());
            }

            this.myTypeSolver.setTargetClass(currClass);
            this.myTypeSolver.setTargetMethod(currMethod,currMethodParam);
        }
    }

    private void handleConstruction(String var, String type) {
        //this.myTypeSolver.setTargetClass(type);
        //this.myTypeSolver.resolveType();
//        if(this.myTypeSolver.tryGetInitMethod(type) != null) {
            //if the class Contains a Constructor then we need to check if there is additional calls involved.
            //MethodHandler mHandler = new MethodHandler(this.root, type, type, this.myTypeSolver);
            //mHandler.handleMethod();
//        } else {
            //TODO
            // Add it create call and add return call
            this.diagram.addCallAToB(this.currClass, type, " new "+type, "()" );
            this.diagram.addReturn(type,this.currClass, "");

 //       }
    }

    private void recursiveMethodCallFinder(Node e, List<MethodCallExpr> collector) {
        if (e.getClass().getSimpleName().equals(Expr.METHOD_CALL.toString())) {
            collector.add((MethodCallExpr) e);
        }
        List<Node> children = e.getChildNodes();
        if (children.size() != 0) {
            for(Node child: children){
                recursiveMethodCallFinder(child, collector);
            }
        }
    }

    private List<String> getParamListFromMethodCall(MethodCallExpr mcall) {
        List<String> ret = new ArrayList<>();
        for(int i=0; i<mcall.getArguments().size(); i++) {
            ret.add(LiteralExpr.getSimpleJavaName(mcall.getArgument(i).getClass().getSimpleName()));
        }
        return ret;
    }
}
