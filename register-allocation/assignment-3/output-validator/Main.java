import syntaxtree.*;
import visitor.*;

public class Main {
   public static void main(String [] args) {
      try {
         Node root = new FriendTJMem(System.in).Goal();
         System.out.println("Program type-checked successfully.");
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
}


