package visitor;

import java.util.*;

class ClassInfo {
    String parentClass;
    List<String> fields;
    Map<String, MethodInfo> methods;

    public ClassInfo() {
        parentClass = null;
        fields = new ArrayList<>();
        methods = new HashMap<>();
    }
}

class MethodInfo {
    List<String> parameters;
    List<String> variables;
    List<String> body;

    public MethodInfo() {
        parameters = new ArrayList<>();
        variables = new ArrayList<>();
        body = new ArrayList<>();
    }
}

class CallInfo{
    String callerId;
    String calleeMethod;
    List<String> args;

    public CallInfo(String x, String y, List<String> z){
        callerId = x;
        calleeMethod = y;
        args = z;
    }
}

class TypeAnalysis{
    List<String> rtaClassInstantiations;
    List<CallInfo> methodCalls;

    public TypeAnalysis(){
        rtaClassInstantiations = new ArrayList<>();
        methodCalls = new ArrayList<>();
    }
}