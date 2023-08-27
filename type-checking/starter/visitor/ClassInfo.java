package visitor;

import java.util.*;

class ClassInfo {
    String parentClass;
    Map<String, String> fieldTypes;
    Map<String, MethodInfo> methods;

    public ClassInfo() {
        parentClass = null;
        fieldTypes = new HashMap<>();
        methods = new HashMap<>();
    }
}

class MethodInfo {
    String retType;
    List<String> argTypes;
    Map<String, String> varparmTypes;

    public MethodInfo() {
        retType = null;
        argTypes = new ArrayList<>();
        varparmTypes = new HashMap<>();
    }
}