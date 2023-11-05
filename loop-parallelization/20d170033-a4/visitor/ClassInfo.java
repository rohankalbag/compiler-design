package visitor;

import java.util.*;

public class ClassInfo {
    public Map<String, MethodInfo> methods;
    public String parent;

    public ClassInfo() {
        methods = new LinkedHashMap<>();
    }
}

class MethodInfo {

    public Set<String> parameters;
    public Set<String> array_parameters;
    public Map<String, Set<String>> variables;
    public Map<String, Set<String>> array_variables;
    public List<LoopInfo> loops;
    public Set<String> aliasWithParams;
    public Set<String> aliasWithArrayParams;

    public MethodInfo() {
        parameters = new LinkedHashSet<>();
        array_parameters = new LinkedHashSet<>();
        variables = new LinkedHashMap<>();
        array_variables = new LinkedHashMap<>();
        loops = new ArrayList<>();
        aliasWithParams = new LinkedHashSet<>();
        aliasWithArrayParams = new LinkedHashSet<>();
    }

    public void addVariable(String p) {
        variables.put(p, new LinkedHashSet<>());
    }

    public void addArrayVariable(String p) {
        array_variables.put(p, new LinkedHashSet<>());
    }

    public void varAlias(String v1, String v2) {
        variables.get(v1).add(v2);
    }

    public void arrayVarAlias(String v1, String v2) {
        array_variables.get(v1).add(v2);
    }
}

class LoopInfo {
    public String loop_var;
    public boolean isParallelizable;
    public int num_array_accesses;

    LoopInfo() {
        isParallelizable = false;
        num_array_accesses = 0;
    }

    public boolean GCDTest() {
        return true;
    }

    public void CheckParallelizability() {
        if (num_array_accesses <= 1)
            isParallelizable = true;
        else {
            isParallelizable = GCDTest();
        }
    }
}