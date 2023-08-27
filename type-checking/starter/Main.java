import syntaxtree.*;
import visitor.*;

public class Main {
   public static void main(String[] args) {
      try {
         Node root = new MiniJavaParser(System.in).Goal();
         GJDepthFirstSymbolPopulation firstParser = new GJDepthFirstSymbolPopulation();
         GJDepthFirst secondParser = new GJDepthFirst();
         root.accept(firstParser, "first");
         secondParser.SymbolTable = firstParser.SymbolTable;
         root.accept(secondParser, "second");
      } catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
}
