import syntaxtree.Node;
import visitor.GJDepthFirst;
import visitor.PrettyPrintDepthFirst;

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
            root.accept(first_traversal, "first");
            first_traversal.typeAnalysis.CheckInlinability();
            first_traversal.typeAnalysis.PerformInlining();
            PrettyPrintDepthFirst final_traversal = new PrettyPrintDepthFirst();
            final_traversal.typeAnalysis = first_traversal.typeAnalysis;
            root.accept(final_traversal, "");
            for(String s : final_traversal.prettyPrint){
                System.out.print(s);
            }
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}
