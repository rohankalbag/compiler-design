import syntaxtree.Node;
import visitor.GJDepthFirst;


public class Main {

    public static void main(String[] args) {
        performAnalysis();
    }

    private static void performAnalysis() {
        try {
            MiniJavaParser parser = new MiniJavaParser(System.in);
            Node root = parser.Goal();
            GJDepthFirst first_traversal = new GJDepthFirst();
            root.accept(first_traversal, "first");
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}
