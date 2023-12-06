package visitor;

import java.util.*;

class InterferenceGraph {
    public Map<String, Set<String>> adjList;
    public Map<String, Integer> degree;
    public Map<String, String> typeMap;
    public Map<String, Integer> regColors;

    public InterferenceGraph() {
        adjList = new HashMap<>();
        degree = new HashMap<>();
        typeMap = new HashMap<>();
    }

    public InterferenceGraph(InterferenceGraph copyGraph) {
        adjList = new HashMap<>();
        degree = new HashMap<>();
        typeMap = new HashMap<>();
        for (String node : copyGraph.adjList.keySet()) {
            adjList.put(node, new HashSet<>());
            for (String neighbour : copyGraph.adjList.get(node)) {
                adjList.get(node).add(neighbour);
            }
            degree.put(node, copyGraph.degree.get(node));
            typeMap.put(node, copyGraph.typeMap.get(node));
        }
    }

    public void addNode(String node, String type) {
        adjList.put(node, new HashSet<>());
        degree.put(node, 0);
        typeMap.put(node, type);
    }

    public boolean addEdge(String u, String v) {
        if (!adjList.get(u).contains(v)) {
            adjList.get(u).add(v);
            degree.put(u, degree.get(u) + 1);
        }
        if (!adjList.get(v).contains(u)) {
            adjList.get(v).add(u);
            degree.put(v, degree.get(v) + 1);
            return true;
        }
        return false;
    }

    public void removeNode(String node) {
        for (String neighbour : adjList.get(node)) {
            degree.put(neighbour, degree.get(neighbour) - 1);
            adjList.get(neighbour).remove(node);
        }
        degree.remove(node);
        typeMap.remove(node);
    }
}

public class RegAlloc {
    public int maxReg;
    public InterferenceGraph currInterferenceGraph;
    public int spillCount;
    public int currSpill;
    public Map<String, String> registerMap;
    public boolean DEBUG = true;

    public RegAlloc(int maxReg) {
        currInterferenceGraph = new InterferenceGraph();
        this.maxReg = maxReg;
        spillCount = 0;
        currSpill = 0;
        registerMap = new HashMap<>();
    }

    public void performKempeHeuristic() {
        currInterferenceGraph.regColors = new HashMap<>();
        Stack<String> kempeStack = new Stack<>();
        InterferenceGraph kempeGraph = new InterferenceGraph(currInterferenceGraph);
        boolean SomethingChanged = true;
        Iterator<String> iterator;

        while (SomethingChanged) {
            iterator = kempeGraph.adjList.keySet().iterator();
            SomethingChanged = false;
            while (iterator.hasNext()) {
                String node = iterator.next();
                if (kempeGraph.degree.get(node) < maxReg) {
                    kempeStack.push(node);
                    if (DEBUG) {
                        System.out.println(
                                "Pushing " + node + " with degree " + kempeGraph.degree.get(node) + " to stack");
                    }
                    kempeGraph.removeNode(node);
                    iterator.remove();
                    if (DEBUG) {
                        System.out.println("Removing " + node + " from Kempe Graph");
                    }
                    SomethingChanged = true;
                }
            }
        }

        iterator = kempeGraph.adjList.keySet().iterator();
        while (iterator.hasNext()) {
            String node = iterator.next();
            kempeStack.push(node);
            if (DEBUG) {
                System.out.println("Pushing " + node + " with degree " + kempeGraph.degree.get(node) + " to stack");
            }
            kempeGraph.removeNode(node);
            iterator.remove();
            if (DEBUG) {
                System.out.println("Removing " + node + " from Kempe Graph");
            }
        }

        while (!kempeStack.isEmpty()) {
            String node = kempeStack.pop();
            if (DEBUG) {
                System.out.println("Popping " + node + " from stack");
            }
            Set<String> neighbours = currInterferenceGraph.adjList.get(node);
            Set<Integer> neighbourRegs = new HashSet<>();
            for (String neighbour : neighbours) {
                if (currInterferenceGraph.regColors.containsKey(neighbour)) {
                    neighbourRegs.add(currInterferenceGraph.regColors.get(neighbour));
                }
            }
            boolean regAssigned = false;
            for (int i = 1; i <= maxReg; i++) {
                if (!neighbourRegs.contains(i)) {
                    currInterferenceGraph.regColors.put(node, i);
                    if (DEBUG) {
                        System.out.println("Assigning " + node + " to r" + i);
                    }
                    regAssigned = true;
                    break;
                }
            }
            if (!regAssigned) {
                currInterferenceGraph.regColors.put(node, -1);
                spillCount++;
                if (DEBUG) {
                    System.out.println("Assigning " + node + " to spill into memory");
                }
            }
        }

    }
}
