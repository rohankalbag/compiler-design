package visitor;

import java.util.*;

public class TypeAnalysis {
    Map<String, ClassInfo> ClassTable;
    String currClass;
    String currMethod;
    List<String> currArgs;
    int chaCount;

    Set<String> rtaClassInstantiations;
    Map<CallInfo, Boolean> methodCalls;

    public TypeAnalysis() {
        rtaClassInstantiations = new HashSet<>();
        methodCalls = new HashMap<>();
        ClassTable = new HashMap<>();
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

    public void printDebug() {
        System.out.println("Totally identified " + methodCalls.size() + " method calls");
        for (CallInfo c : methodCalls.keySet()) {
            if (methodCalls.get(c)) {
                System.out.print("\tCleared to inline method call : ");
                System.out.print(c.callerId + ".");
                System.out.print(c.calleeMethod + "(");
                System.out.print(c.args);
                System.out.println(")");
            } else {
                System.out.print("\tNot cleared to inline method call : ");
                System.out.print(c.callerId + ".");
                System.out.print(c.calleeMethod + "(");
                System.out.print(c.args);
                System.out.println(")");
            }
        }
    }

    public void ClassHierarchyAnalysis(String s1, String m1){
        ClassInfo c1 = ClassTable.get(s1);
        if(c1.methods.containsKey(m1) && rtaClassInstantiations.contains(s1)){
            chaCount += 1;
        }
        for(String child : c1.childrenClasses){
            ClassHierarchyAnalysis(child, m1);
        }
    }

    public void CheckInlinability() {
        for (CallInfo c : methodCalls.keySet()) {
            String callerStaticType = c.callerType;
            chaCount = 0;
            ClassHierarchyAnalysis(callerStaticType, c.calleeMethod);
            if (chaCount == 1) {
                methodCalls.replace(c, true);
            }
        }
    }
}