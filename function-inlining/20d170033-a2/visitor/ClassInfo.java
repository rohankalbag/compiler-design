package visitor;

import java.util.*;

import syntaxtree.Node;
import syntaxtree.VarDeclaration;
import syntaxtree.Statement;
import syntaxtree.Type;

class ClassInfo {
    String parentClass;
    Map<String, String> fields;
    Map<String, MethodInfo> methods;
    List<String> childrenClasses;

    public ClassInfo() {
        parentClass = null;
        fields = new HashMap<>();
        methods = new HashMap<>();
        childrenClasses = new ArrayList<>();
    }

    public ClassInfo(ClassInfo c) {
        parentClass = null;
        fields = new HashMap<>();
        methods = new HashMap<>();
        for (String m : c.methods.keySet()) {
            methods.put(m, c.methods.get(m));
        }
        for (String m : c.fields.keySet()) {
            methods.put(m, c.methods.get(m));
        }
        childrenClasses = new ArrayList<>();
    }
}

class MethodInfo {
    Boolean isOverriden;
    Map<String, String> parameters;
    Map<String, Type> param_type;
    Map<String, String> variables;
    Node varDec;
    Node statements;
    String returnId;

    public MethodInfo() {
        isOverriden = false;
        parameters = new LinkedHashMap<>();
        variables = new HashMap<>();
        param_type = new LinkedHashMap<>();
    }
}

class CallInfo {
    Boolean isInlinable;
    String callerId;
    String callerType;
    String calleeMethod;
    String devirtualizedClass;
    String returnDest;
    List<String> args;
    MethodInfo devirtualizedMethod;
    List<VarDeclaration> inlineDeclaredVars;
    List<Statement> inlineStatements;

    public CallInfo(String x, String y, List<String> z, String a) {
        isInlinable = false;
        callerId = x;
        calleeMethod = y;
        args = z;
        callerType = a;
        inlineDeclaredVars = new ArrayList<>();
        inlineStatements = new ArrayList<>();
    }
}