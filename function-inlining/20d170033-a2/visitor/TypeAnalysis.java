package visitor;

import java.util.*;

import syntaxtree.AssignmentStatement;
import syntaxtree.Identifier;
import syntaxtree.NodeChoice;
import syntaxtree.NodeToken;
import syntaxtree.PrimaryExpression;
import syntaxtree.Statement;
import syntaxtree.Type;
import syntaxtree.Expression;
import syntaxtree.VarDeclaration;

public class TypeAnalysis {
    public Map<String, ClassInfo> ClassTable;
    String currClass;
    String currMethod;
    List<String> currArgs;

    int rtaCount;
    List<String> possibleCHAClasses;
    Set<String> visitedClasses;

    InlineDepthFirst subtreeDFS;

    Set<String> rtaClassInstantiations;
    List<CallInfo> methodCalls;

    public boolean debug;

    public TypeAnalysis() {
        rtaClassInstantiations = new LinkedHashSet<>();
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

    public String addMethodPrefix(CallInfo c, String Identifier) {
        return c.calleeMethod + '_' + Identifier;
    }

    public void CHA(String s1, String m1, CallInfo c) {
        ClassInfo c1 = ClassTable.get(s1);
        if (c1.methods.containsKey(m1)) {
            MethodInfo mInfo = c1.methods.get(m1);
            possibleCHAClasses.add(s1);
            if (debug) {
                System.out.print("\tChecking CHA for method : " + s1 + "." + m1 + " : ");
                System.out.print("isOverriden : " + mInfo.isOverriden);
                System.out.println(" isInherited : " + mInfo.isInherited);
            }
        }
        for (String child : c1.childrenClasses) {
            CHA(child, m1, c);
        }
    }

    public void checkValidRTA(String c, String m, CallInfo ci) {
        if (possibleCHAClasses.contains(c) && !visitedClasses.contains(c)) {
            ClassInfo cInfo = ClassTable.get(c);
            MethodInfo mInfo = cInfo.methods.get(m);
            if (debug) {
                System.out.println("\t\tChecking RTA for method : " + c + "." + m);
            }
            visitedClasses.add(c);
            if (!mInfo.isInherited) {
                rtaCount += 1;
                ci.devirtualizedClass = c;
            } else {
                if (mInfo.isOverriden) {
                    rtaCount += 1;
                    ci.devirtualizedClass = c;
                } else {
                    if (!visitedClasses.contains(cInfo.parentClass)) {
                        if (debug) {
                            System.out.println("\tChecking parent : " + cInfo.parentClass);
                        }
                        checkValidRTA(cInfo.parentClass, m, ci);
                        visitedClasses.add(cInfo.parentClass);
                    }
                }
            }
        }
    }

    public void RTA(String m1, CallInfo ci) {
        List<String> rtaClassList = new ArrayList<>(rtaClassInstantiations);
        for (String c : rtaClassList) {
            checkValidRTA(c, m1, ci);
        }
    }

    public void CheckInlinability() {
        if (debug) {
            System.out.println("Totally identified " + methodCalls.size() + " method calls");
        }
        for (CallInfo c : methodCalls) {
            String callerStaticType = c.callerType;
            possibleCHAClasses = new ArrayList<>();
            String oldestAncestor = callerStaticType;
            ClassInfo currAncestorCI = ClassTable.get(oldestAncestor);
            while (currAncestorCI.parentClass != null && currAncestorCI.methods.get(c.calleeMethod).isInherited
                    && !currAncestorCI.methods.get(c.calleeMethod).isOverriden) {
                oldestAncestor = ClassTable.get(oldestAncestor).parentClass;
                currAncestorCI = ClassTable.get(oldestAncestor);
            }
            CHA(oldestAncestor, c.calleeMethod, c);
            visitedClasses = new HashSet<>();
            rtaCount = 0;
            RTA(c.calleeMethod, c);

            if (debug) {
                System.out.println("\tRTA Classes:" + rtaClassInstantiations);
                System.out.print("\tRTA Count : ");
                System.out.println(rtaCount);
                System.out.println("\tPossible CHA Classes : " + possibleCHAClasses);
                System.out.println("\tVisited Classes : " + visitedClasses);
                System.out.println("\tDevirtualized Class : " + c.devirtualizedClass);
            }

            if (rtaCount == 1) {
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
        subtreeDFS.inlinedBody
                .add("\t\t" + c.callerType + " " + addMethodPrefix(c, "this") + ";\n");
        c.inlineDeclaredVars
                .add(new VarDeclaration(new Type(new NodeChoice(new Identifier(new NodeToken(c.callerType)), 4)),
                        addMethodPrefix(c, "this")));
        for (String x : c.devirtualizedMethod.parameters.keySet()) {
            subtreeDFS.inlinedBody
                    .add("\t\t" + c.devirtualizedMethod.parameters.get(x) + " " + addMethodPrefix(c, x) + ";\n");
            c.inlineDeclaredVars
                    .add(new VarDeclaration(c.devirtualizedMethod.param_type.get(x), addMethodPrefix(c, x)));
        }
        c.devirtualizedMethod.varDec.accept(subtreeDFS, "declarations");
        subtreeDFS.inlinedBody
                .add("\t\t" + addMethodPrefix(c, "this") + " = " + c.callerId + ";\n");
        AssignmentStatement thisAssignment = new AssignmentStatement(
                new Identifier(new NodeToken(addMethodPrefix(c, "this"))),
                new Expression(new NodeChoice(
                        new PrimaryExpression(new NodeChoice(
                                new Identifier(new NodeToken(c.callerId)),
                                3)),
                        11)));
        c.inlineStatements.add(new Statement(new NodeChoice(thisAssignment, 1)));
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

    public void PerformInlining() {
        for (CallInfo c : methodCalls) {
            if (c.isInlinable) {
                InlineCall(c);
            }
        }
    }
}