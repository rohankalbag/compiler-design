import syntaxtree.Node;
import visitor.ParallelizeVisitor;
import visitor.PrintVisitor;

public class Main {

    public static void main(String[] args) {
        performAnalysis();
    }

    private static void performAnalysis() {
        try {
            Node root = new MiniJavaParser(System.in).Goal();
            ParallelizeVisitor<String, String> parallelizeVisitor = new ParallelizeVisitor<>();
            root.accept(parallelizeVisitor, null);
            PrintVisitor<String, String> printVisitor = new PrintVisitor<>();
            root.accept(printVisitor, null);
            printVisitor.printCode();
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
}
