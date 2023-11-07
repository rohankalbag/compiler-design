package visitor;

import java.util.*;

public class ClassInfo {
    public Map<String, MethodInfo> methods;
    public String parent;

    public ClassInfo() {
        methods = new LinkedHashMap<>();
    }
}

class Diophantine {
    public int a;
    public String x;
    public int b;

    Diophantine() {
        a = 0;
        x = "";
        b = 0;
    }

    Diophantine(Diophantine d) {
        a = d.a;
        x = d.x;
        b = d.b;
    }

    public void Visualize() {
        System.out.println(a + " * " + x + " + " + b);
    }

    public void add(Diophantine d) {
        a += d.a;
        b += d.b;
    }

    public void sub(Diophantine d) {
        a -= d.a;
        b -= d.b;
    }

    public void mul(Diophantine d) {
        a = a * d.b;
        b = b * d.b;
    }

    public void div(Diophantine d) {
        a = a / d.b;
        b = b / d.b;
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
    public boolean DEBUG = false;
    public boolean isParallelizable;
    public boolean array_aliases_with_params;
    public boolean array_access_with_extn_var;
    public boolean array_const_write_across_iters;
    public boolean array_access_across_iters;
    public boolean gcd_test_check;
    public int num_array_accesses;
    Set<String> isFunctOfLoopVar;
    Set<String> refFromOut;
    Map<String, Diophantine> functionOfIterVar;

    LoopInfo() {
        isParallelizable = false;
        num_array_accesses = 0;
        array_aliases_with_params = false;
        isFunctOfLoopVar = new LinkedHashSet<>();
        refFromOut = new LinkedHashSet<>();
        array_access_with_extn_var = false;
        array_const_write_across_iters = false;
        array_access_across_iters = false;
        functionOfIterVar = new LinkedHashMap<>();
        gcd_test_check = false;
    }

    public boolean CheckFlags() {
        // if (DEBUG) {
        //     System.out.println("array_aliases_with_params: " + array_aliases_with_params);
        //     System.out.println("array_access_with_extn_var: " + array_access_with_extn_var);
        //     System.out.println("array_const_write_across_iters: " + array_const_write_across_iters);
        //     System.out.println("array_access_across_iters: " + array_access_across_iters);
        //     System.out.println("gcd_test_check: " + gcd_test_check);
        //     System.out.println("num_array_accesses: " + num_array_accesses);
        // }
        if (gcd_test_check)
            return false;
        if (array_access_across_iters)
            return false;
        if (array_const_write_across_iters)
            return false;
        if (array_access_with_extn_var)
            return false;
        if (array_aliases_with_params && gcd_test_check)
            return false;
        if (num_array_accesses < 1)
            return true;
        return true;
    }

    public void AccessedArray(String lhs, String rhs) {
        num_array_accesses++;
    }

    public int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    public void GCDTest(Diophantine lhs, Diophantine rhs) {
        int gcd = gcd(lhs.a, -rhs.a);
        int b = rhs.b - lhs.b;
        if (b % gcd == 0) {
            gcd_test_check = true;
            if (DEBUG)
                System.out.println("GCD Test Shows Solution Exists");
        } else {
            if (DEBUG)
                System.out.println("GCD Test Shows Solution Does Not Exist");
        }
    }

    public void ComputeDiophantine(Diophantine lhs, Diophantine rhs) {
        if (DEBUG) {
            System.out.println("Diophantine Equation:");
            lhs.Visualize();
            rhs.Visualize();
        }
        GCDTest(lhs, rhs);
    }

    public String getLinearForm(String input) {
        return input;
    }

    public void CheckParallelizability() {
        isParallelizable = CheckFlags();
    }
}