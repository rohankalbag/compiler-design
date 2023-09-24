import syntaxtree.Node;
import visitor.GJDepthFirst;
import visitor.GJDepthFirstRTA;

public class Main {
    private static final boolean debug = true;

    public static void main(String[] args) {
        performAnalysis();
    }

    private static void performAnalysis() {
        try {
            MiniJavaParser parser = new MiniJavaParser(System.in);
            Node root = parser.Goal();
            GJDepthFirst first_traversal = new GJDepthFirst();
            GJDepthFirstRTA second_traversal = new GJDepthFirstRTA();
            root.accept(first_traversal, "first");
            second_traversal.typeAnalysis = first_traversal.typeAnalysis;
            second_traversal.typeAnalysis.CheckInlinability();
            if(debug){
                second_traversal.typeAnalysis.printDebug();
            }
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}
