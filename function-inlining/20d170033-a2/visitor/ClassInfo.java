package visitor;

import java.util.*;

class ClassInfo {
    String parentClass;
    Map<String, String> fields;
    Map<String, MethodInfo> methods;

    public ClassInfo() {
        parentClass = null;
        fields = new HashMap<>();
        methods = new HashMap<>();
    }
}

class MethodInfo {
    Map<String, String> parameters;
    Map<String, String> variables;
    List<String> body;

    public MethodInfo() {
        parameters = new HashMap<>();
        variables = new HashMap<>();
        body = new ArrayList<>();
    }
}

class CallInfo {
    String callerId;
    String callerType;
    String calleeMethod;
    List<String> args;

    public CallInfo(String x, String y, List<String> z, String a) {
        callerId = x;
        calleeMethod = y;
        args = z;
        callerType = a;
    }
}