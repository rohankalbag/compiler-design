package visitor;

import java.util.*;

public class TypeAnalysis {
    Map<String, ClassInfo> ClassTable;
    String currClass;
    String currMethod;
    List<String> currArgs;

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

    public boolean isDescendant(String s1, String s2) {
        ClassInfo c1 = ClassTable.get(s1);
        while (c1.parentClass != null) {
            if (s2 == c1.parentClass) {
                return true;
            }
            c1 = ClassTable.get(c1.parentClass);
        }
        return false;
    }

    public boolean containsMethod(String s1, String m1) {
        ClassInfo c1 = ClassTable.get(s1);
        while (c1 != null) {
            if (c1.methods.containsKey(m1)) {
                return true;
            }
            c1 = ClassTable.get(c1.parentClass);
        }
        return false;
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

    public void CheckInlinability() {
        for (CallInfo c : methodCalls.keySet()) {
            String callerStaticType = c.callerType;
            List<String> possibleDynamicTypes = new ArrayList<>();
            for (String classInfo : rtaClassInstantiations) {
                boolean isDesc = isDescendant(classInfo, callerStaticType);
                boolean hasMethod = containsMethod(classInfo, c.calleeMethod);
                if (isDesc && hasMethod) {
                    possibleDynamicTypes.add(classInfo);
                }
            }
            if (possibleDynamicTypes.size() == 1) {
                methodCalls.replace(c, true);
            }
        }
    }
}