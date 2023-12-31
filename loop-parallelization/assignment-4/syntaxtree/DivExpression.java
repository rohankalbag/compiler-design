//
// Generated by JTB 1.3.2
//

package syntaxtree;

/**
 * Grammar production:
 * f0 -> ArrayorIdentifierorIntegerLiteral()
 * f1 -> "/"
 * f2 -> ArrayorIdentifierorIntegerLiteral()
 */
public class DivExpression implements Node {
   public ArrayorIdentifierorIntegerLiteral f0;
   public NodeToken f1;
   public ArrayorIdentifierorIntegerLiteral f2;

   public DivExpression(ArrayorIdentifierorIntegerLiteral n0, NodeToken n1, ArrayorIdentifierorIntegerLiteral n2) {
      f0 = n0;
      f1 = n1;
      f2 = n2;
   }

   public DivExpression(ArrayorIdentifierorIntegerLiteral n0, ArrayorIdentifierorIntegerLiteral n1) {
      f0 = n0;
      f1 = new NodeToken("/");
      f2 = n1;
   }

   public void accept(visitor.Visitor v) {
      v.visit(this);
   }
   public <R,A> R accept(visitor.GJVisitor<R,A> v, A argu) {
      return v.visit(this,argu);
   }
   public <R> R accept(visitor.GJNoArguVisitor<R> v) {
      return v.visit(this);
   }
   public <A> void accept(visitor.GJVoidVisitor<A> v, A argu) {
      v.visit(this,argu);
   }
}

