package visitor;
import java.util.*;

public class ClassInfo{
    public Map<String, MethodInfo> methods;
    public String parent;

    public ClassInfo() {
        methods = new LinkedHashMap<>();
    }
}

class MethodInfo {
    public Set<String> parameters;
    public Set<String> registers;
    RegAlloc methodRegAlloc;
    public MethodInfo() {
        parameters = new LinkedHashSet<>();
        registers = new LinkedHashSet<>();
    }
}
