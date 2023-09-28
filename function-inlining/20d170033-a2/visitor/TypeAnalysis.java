package visitor;

import java.util.*;

import syntaxtree.AssignmentStatement;
import syntaxtree.Identifier;
import syntaxtree.NodeChoice;
import syntaxtree.NodeToken;
import syntaxtree.PrimaryExpression;
import syntaxtree.Statement;
import syntaxtree.Expression;
import syntaxtree.VarDeclaration;

public class TypeAnalysis {
    public Map<String, ClassInfo> ClassTable;
    String currClass;
    String currMethod;
    List<String> currArgs;
    int chaCount;
    InlineDepthFirst subtreeDFS;

    Set<String> rtaClassInstantiations;
    List<CallInfo> methodCalls;

    public boolean debug;

    public TypeAnalysis() {
        rtaClassInstantiations = new HashSet<>();
        methodCalls = new ArrayList<>();
        ClassTable = new HashMap<>();
        subtreeDFS = new InlineDepthFirst();
        subtreeDFS.debug = debug;
    }

    public String getType(String id) {
        String type = null;
        if (ClassTable.get(currClass).fields.containsKey(id)) {
            type = ClassTable.get(currClass).fields.get(id);
        } else if (ClassTable.get(currClass).methods.get(currMethod).variables.containsKey(id)) {
            type = ClassTable.get(currClass).methods.get(currMethod).variables.get(id);
        } else if (ClassTable.get(currClass).methods.get(currMethod).parameters.containsKey(id)) {
            type = ClassTable.get(currClass).methods.get(currMethod).parameters.get(id);
        }
        return type;
    }

    public boolean rtaContainSelfOrParent(String s1, CallInfo c) {
        boolean flag = rtaClassInstantiations.contains(s1);
        while (s1 != null) {
            ClassInfo c1 = ClassTable.get(s1);
            flag = (rtaClassInstantiations.contains(s1)) ? true : flag;
            c.devirtualizedClass = (rtaClassInstantiations.contains(s1)) ? s1 : c.devirtualizedClass;
            s1 = c1.parentClass;
        }
        return flag;
    }

    public void RTA(String s1, String m1, CallInfo c) {
        ClassInfo c1 = ClassTable.get(s1);
        if (c1.methods.containsKey(m1) && rtaContainSelfOrParent(s1, c)) {
            chaCount += 1;
        }
        for (String child : c1.childrenClasses) {
            RTA(child, m1, c);
        }
    }

    public String addMethodPrefix(CallInfo c, String Identifier) {
        return c.calleeMethod + '_' + Identifier;
    }

    public void InlineCall(CallInfo c) {
        if (debug) {
            System.out.print("\tInlining Method");
            System.out.print(c.callerType + ".");
            System.out.print(c.calleeMethod + "(");
            System.out.print(") : ");
            System.out.println("Devirtualized to " + c.devirtualizedClass);
        }
        c.devirtualizedMethod = ClassTable.get(c.devirtualizedClass).methods.get(c.calleeMethod);
        subtreeDFS.currCall = c;
        subtreeDFS.typeAnalysis = this;
        subtreeDFS.inlinedBody = new ArrayList<>();
        for (String x : c.devirtualizedMethod.parameters.keySet()) {
            subtreeDFS.inlinedBody
                    .add("\t\t" + c.devirtualizedMethod.parameters.get(x) + " " + addMethodPrefix(c, x) + ";\n");
            c.inlineDeclaredVars
                    .add(new VarDeclaration(c.devirtualizedMethod.param_type.get(x), addMethodPrefix(c, x)));
        }
        c.devirtualizedMethod.varDec.accept(subtreeDFS, "declarations");
        int arg_index = 0;
        for (String x : c.devirtualizedMethod.parameters.keySet()) {
            subtreeDFS.inlinedBody
                    .add("\t\t" + addMethodPrefix(c, x) + " = " + c.args.get(arg_index) + ";\n");
            AssignmentStatement argAssignment = new AssignmentStatement(
                    new Identifier(new NodeToken(addMethodPrefix(c, x))),
                    new Expression(new NodeChoice(
                            new PrimaryExpression(new NodeChoice(
                                    new Identifier(new NodeToken(c.args.get(arg_index))),
                                    3)),
                            11)));
            c.inlineStatements.add(new Statement(new NodeChoice(argAssignment, 1)));
            arg_index += 1;
        }
        c.devirtualizedMethod.statements.accept(subtreeDFS, "statements");
        AssignmentStatement retStatement = new AssignmentStatement(new Identifier(new NodeToken(c.returnDest)),
                new Expression(new NodeChoice(
                        new PrimaryExpression(new NodeChoice(
                                new Identifier(new NodeToken(addMethodPrefix(c, c.devirtualizedMethod.returnId))), 3)),
                        11)));
        c.inlineStatements.add(new Statement(new NodeChoice(retStatement, 1)));
        subtreeDFS.inlinedBody
                .add("\t\t" + c.returnDest + " = " + addMethodPrefix(c, c.devirtualizedMethod.returnId) + ";\n");
        if (debug) {
            for (String s : subtreeDFS.inlinedBody) {
                System.out.print(s);
            }
        }
    }

    public void CheckInlinability() {
        if (debug) {
            System.out.println("Totally identified " + methodCalls.size() + " method calls");
        }
        for (CallInfo c : methodCalls) {
            String callerStaticType = c.callerType;
            chaCount = 0;
            RTA(callerStaticType, c.calleeMethod, c);

            if (chaCount == 1) {
                c.isInlinable = true;
                if (debug) {
                    System.out.print("\tCleared to inline method call : ");
                    System.out.print(c.callerType + ".");
                    System.out.print(c.calleeMethod + "(");
                    System.out.print(c.args);
                    System.out.print(") : ");
                    System.out.println("Devirtualized to " + c.devirtualizedClass);
                }
            } else if (debug) {
                System.out.print("\tNot cleared to inline method call : ");
                System.out.print(c.callerType + ".");
                System.out.print(c.calleeMethod + "(");
                System.out.print(c.args);
                System.out.println(")");
            }
        }
    }

    public void PerformInlining() {
        for (CallInfo c : methodCalls) {
            if (c.isInlinable) {
                InlineCall(c);
            }
        }
    }
}