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

    public void Visualize() {
        System.out.println(a + " * " + x + " + " + b);
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
    List<List<String>> array_accesses;
    Set<String> isFunctOfLoopVar;
    Map<String, String> functionOfIterVar;

    LoopInfo() {
        isParallelizable = false;
        num_array_accesses = 0;
        array_accesses = new ArrayList<>();
        array_aliases_with_params = false;
        isFunctOfLoopVar = new LinkedHashSet<>();
        array_access_with_extn_var = false;
        array_const_write_across_iters = false;
        array_access_across_iters = false;
        functionOfIterVar = new LinkedHashMap<>();
        gcd_test_check = false;
    }

    public boolean CheckFlags() {
        if (gcd_test_check)
            return false;
        if (array_access_across_iters)
            return false;
        if (array_const_write_across_iters)
            return false;
        if (array_access_with_extn_var)
            return false;
        if (array_aliases_with_params)
            return false;
        if (num_array_accesses < 1)
            return true;
        return false;
    }

    public void AccessedArray(String lhs, String rhs) {
        List<String> access = new ArrayList<>();
        access.add(lhs);
        access.add(rhs);
        array_accesses.add(access);
        num_array_accesses++;
    }

    public boolean CheckFormat(String s, Diophantine d) {
        // we can have ( a * x ) + b or b + ( x * a ) or a * x or x * a or x or a
        String[] tokens = s.split(" ");
        if (tokens.length == 7) {
            if (tokens[0].equals("(") && tokens[2].equals("*") && tokens[4].equals(")") && tokens[5].equals("+")) {
                int b = Integer.parseInt(tokens[6]);
                int a;
                String x;
                if (tokens[1].equals(loop_var)) {
                    x = tokens[1];
                    a = Integer.parseInt(tokens[3]);
                } else {
                    x = tokens[3];
                    a = Integer.parseInt(tokens[1]);
                }
                d.a = a;
                d.x = x;
                d.b = b;
                return true;
            } else if (tokens[1].equals("+") && tokens[2].equals("(") && tokens[4].equals("*")
                    && tokens[6].equals(")")) {
                int b = Integer.parseInt(tokens[0]);
                int a;
                String x;
                if (tokens[3].equals(loop_var)) {
                    x = tokens[3];
                    a = Integer.parseInt(tokens[5]);
                } else {
                    x = tokens[5];
                    a = Integer.parseInt(tokens[3]);
                }
                d.a = a;
                d.x = x;
                d.b = b;
                return true;
            } else {
                return false;
            }
        } else if (tokens.length == 3) {
            if (tokens[1].equals("*")) {
                int a;
                String x;
                if (tokens[0].equals(loop_var)) {
                    x = tokens[0];
                    a = Integer.parseInt(tokens[2]);
                } else {
                    x = tokens[2];
                    a = Integer.parseInt(tokens[0]);
                }
                d.a = a;
                d.x = x;
                d.b = 0;
                return true;
            } else {
                return false;
            }
        } else if (tokens.length == 1) {
            if (tokens[0].equals(loop_var)) {
                d.a = 1;
                d.x = tokens[0];
                d.b = 0;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
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

    public void ComputeDiophantine(String lhs, String rhs) {
        Diophantine Dlhs = new Diophantine();
        Diophantine Drhs = new Diophantine();
        if (DEBUG) {
            System.out.println("Converting to Diophantine Equation");
            System.out.println("lhs: " + lhs);
            System.out.println("rhs: " + rhs);
        }
        if (CheckFormat(lhs, Dlhs) && CheckFormat(rhs, Drhs)) {
            if (DEBUG) {
                System.out.println("Diophantine Equation:");
                Dlhs.Visualize();
                Drhs.Visualize();
            }
            GCDTest(Dlhs, Drhs);
        } else {
            if (DEBUG) {
                System.out.println("Not convertable to a Diophantine Equation");
            }
        }
    }

    public void CheckParallelizability() {
        isParallelizable = CheckFlags();
    }
}