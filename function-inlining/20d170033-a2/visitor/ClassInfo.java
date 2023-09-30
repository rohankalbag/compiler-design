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
            MethodInfo x = new MethodInfo(c.methods.get(m));
            methods.put(m, x);
        }
        for (String m : c.fields.keySet()) {
            methods.put(m, c.methods.get(m));
        }
        childrenClasses = new ArrayList<>();
    }
}

class MethodInfo {
    Boolean isOverriden;
    Boolean isInherited;
    Map<String, String> parameters;
    Map<String, Type> param_type;
    Map<String, String> variables;
    Node varDec;
    Node statements;
    String returnId;

    public MethodInfo() {
        isOverriden = false;
        isInherited = false;
        parameters = new LinkedHashMap<>();
        variables = new HashMap<>();
        param_type = new LinkedHashMap<>();
    }

    public MethodInfo(MethodInfo k){
        isOverriden = false;
        isInherited = true;
        parameters = new LinkedHashMap<>();
        variables = new HashMap<>();
        param_type = new LinkedHashMap<>();
        for(String s : k.parameters.keySet()){
            parameters.put(s, k.parameters.get(s));
        }
        for(String s : k.variables.keySet()){
            variables.put(s, k.variables.get(s));
        }
        for(String s : k.param_type.keySet()){
            param_type.put(s, k.param_type.get(s));
        }
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