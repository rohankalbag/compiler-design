//
// Generated by JTB 1.3.2
//

package syntaxtree;

/**
 * Grammar production:
 * f0 -> Identifier()
 * f1 -> "<="
 * f2 -> IntegerOrIdentifier()
 */
public class LTEExpression implements Node {
   public Identifier f0;
   public NodeToken f1;
   public IntegerOrIdentifier f2;

   public LTEExpression(Identifier n0, NodeToken n1, IntegerOrIdentifier n2) {
      f0 = n0;
      f1 = n1;
      f2 = n2;
   }

   public LTEExpression(Identifier n0, IntegerOrIdentifier n1) {
      f0 = n0;
      f1 = new NodeToken("<=");
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

