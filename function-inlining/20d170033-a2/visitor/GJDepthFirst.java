//
// Generated by JTB 1.3.2
//

package visitor;

import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order. Your visitors may extend this class.
 */
public class GJDepthFirst implements GJVisitor<String, String> {
   // Auto class visitors--probably don't need to be overridden.
   private static final boolean debug = false;

   public String visit(NodeList n, String argu) {
      String _ret = null;
      int _count = 0;
      for (Enumeration<Node> e = n.elements(); e.hasMoreElements();) {
         e.nextElement().accept(this, argu);
         _count++;
      }
      return _ret;
   }

   public String visit(NodeListOptional n, String argu) {
      if (n.present()) {
         String _ret = null;
         int _count = 0;
         for (Enumeration<Node> e = n.elements(); e.hasMoreElements();) {
            e.nextElement().accept(this, argu);
            _count++;
         }
         return _ret;
      } else
         return null;
   }

   public String visit(NodeOptional n, String argu) {
      if (n.present())
         return n.node.accept(this, argu);
      else
         return null;
   }

   public String visit(NodeSequence n, String argu) {
      String _ret = null;
      int _count = 0;
      for (Enumeration<Node> e = n.elements(); e.hasMoreElements();) {
         e.nextElement().accept(this, argu);
         _count++;
      }
      return _ret;
   }

   public String visit(NodeToken n, String argu) {
      return null;
   }

   public TypeAnalysis typeAnalysis = new TypeAnalysis();
   public String returnId;
   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
   public String visit(Goal n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> ( VarDeclaration() )*
    * f15 -> ( Statement() )*
    * f16 -> "}"
    * f17 -> "}"
    */
   public String visit(MainClass n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      typeAnalysis.currClass = n.f1.accept(this, argu);
      ClassInfo classInfo = new ClassInfo();
      MethodInfo methodInfo = new MethodInfo();
      typeAnalysis.currMethod = "main";
      classInfo.methods.put(typeAnalysis.currMethod, methodInfo);
      if (debug) {
         System.out.println("Main class parsed : " + typeAnalysis.currClass);
         System.out.println("\tMethod parsed : " + typeAnalysis.currClass + "." + typeAnalysis.currMethod);
      }
      typeAnalysis.ClassTable.put(typeAnalysis.currClass, classInfo);

      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      n.f13.accept(this, argu);
      n.f14.accept(this, argu);
      n.f15.accept(this, argu);
      n.f16.accept(this, argu);
      n.f17.accept(this, argu);
      typeAnalysis.currClass = null;
      typeAnalysis.currMethod = null;
      return _ret;
   }

   /**
    * f0 -> ClassDeclaration()
    * | ClassExtendsDeclaration()
    */
   public String visit(TypeDeclaration n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
   public String visit(ClassDeclaration n, String argu) {
      String _ret = null;
      ClassInfo classInfo = new ClassInfo();
      n.f0.accept(this, argu);
      typeAnalysis.currClass = n.f1.accept(this, argu);
      if (debug) {
         System.out.println("Class parsed : " + typeAnalysis.currClass);
      }
      classInfo.parentClass = null;
      typeAnalysis.ClassTable.put(typeAnalysis.currClass, classInfo);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      typeAnalysis.currClass = null;
      typeAnalysis.currMethod = null;
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
   public String visit(ClassExtendsDeclaration n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      typeAnalysis.currClass = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      String parentClass = n.f3.accept(this, argu);
      ClassInfo parentClassInfo = typeAnalysis.ClassTable.get(parentClass);
      parentClassInfo.childrenClasses.add(typeAnalysis.currClass);
      ClassInfo classInfo = new ClassInfo(parentClassInfo);
      if (debug) {
         System.out.println("Class parsed : " + typeAnalysis.currClass + " extends : " + parentClass);
      }
      typeAnalysis.ClassTable.put(typeAnalysis.currClass, classInfo);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      typeAnalysis.currClass = null;
      typeAnalysis.currMethod = null;
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public String visit(VarDeclaration n, String argu) {
      String _ret = null;
      String type = n.f0.accept(this, argu);
      String id = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      if (typeAnalysis.currMethod != null) {
         typeAnalysis.ClassTable.get(typeAnalysis.currClass).methods.get(typeAnalysis.currMethod).variables.put(id,
               type);
         if (debug) {
            System.out.println("\t\tFound variable : " + id + " with type : " + type);
         }
      } else {
         typeAnalysis.ClassTable.get(typeAnalysis.currClass).fields.put(id, type);
         if (debug) {
            System.out.println("\tFound field : " + id + " with type : " + type);
         }
      }
      return _ret;
   }

   /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Identifier()
    * f11 -> ";"
    * f12 -> "}"
    */
   public String visit(MethodDeclaration n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      typeAnalysis.currMethod = n.f2.accept(this, argu);
      if (debug) {
         System.out.println("\tMethod parsed : " + typeAnalysis.currClass + "." + typeAnalysis.currMethod);
      }
      MethodInfo methodInfo = new MethodInfo();
      typeAnalysis.ClassTable.get(typeAnalysis.currClass).methods.put(typeAnalysis.currMethod, methodInfo);
      methodInfo.varDec = n.f7;
      methodInfo.statements = n.f8;
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      methodInfo.returnId = n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      typeAnalysis.currMethod = null;
      return _ret;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public String visit(FormalParameterList n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public String visit(FormalParameter n, String argu) {
      String _ret = null;
      MethodInfo curr_method_info = typeAnalysis.ClassTable.get(typeAnalysis.currClass).methods
            .get(typeAnalysis.currMethod);
      String type = n.f0.accept(this, argu);
      String id = n.f1.accept(this, argu);
      if (debug) {
         System.out.println("\t\tFound parameter : " + id + " with type : " + type);
      }
      curr_method_info.parameters.put(id, type);
      curr_method_info.param_type.put(id, n.f0);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public String visit(FormalParameterRest n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayType()
    * | BooleanType()
    * | IntegerType()
    * | FloatType()
    * | Identifier()
    */
   public String visit(Type n, String argu) {
      String _ret = null;
      _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   public String visit(ArrayType n, String argu) {
      String _ret = "int[]";
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "boolean"
    */
   public String visit(BooleanType n, String argu) {
      String _ret = "boolean";
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "int"
    */
   public String visit(IntegerType n, String argu) {
      String _ret = "int";
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "float"
    */
   public String visit(FloatType n, String argu) {
      String _ret = "float";
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Block()
    * | AssignmentStatement()
    * | ArrayAssignmentStatement()
    * | IfStatement()
    * | WhileStatement()
    * | PrintStatement()
    */
   public String visit(Statement n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public String visit(Block n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public String visit(AssignmentStatement n, String argu) {
      String _ret = null;
      String id = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      if (n.f2.f0.which == 10) {
         returnId = id;
      }
      n.f2.accept(this, argu);
      returnId = null;
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Identifier()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Identifier()
    * f6 -> ";"
    */
   public String visit(ArrayAssignmentStatement n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> IfthenElseStatement()
    * | IfthenStatement()
    */
   public String visit(IfStatement n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public String visit(IfthenStatement n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   public String visit(IfthenElseStatement n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public String visit(WhileStatement n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> ";"
    */
   public String visit(PrintStatement n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> OrExpression()
    * | AndExpression()
    * | CompareExpression()
    * | NeqExpression()
    * | PlusExpression()
    * | MinusExpression()
    * | TimesExpression()
    * | DivExpression()
    * | ArrayLookup()
    * | ArrayLength()
    * | MessageSend()
    * | PrimaryExpression()
    */
   public String visit(Expression n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "&&"
    * f2 -> Identifier()
    */
   public String visit(AndExpression n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "||"
    * f2 -> Identifier()
    */
   public String visit(OrExpression n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "<="
    * f2 -> Identifier()
    */
   public String visit(CompareExpression n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "!="
    * f2 -> Identifier()
    */
   public String visit(NeqExpression n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "+"
    * f2 -> Identifier()
    */
   public String visit(PlusExpression n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "-"
    * f2 -> Identifier()
    */
   public String visit(MinusExpression n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "*"
    * f2 -> Identifier()
    */
   public String visit(TimesExpression n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "/"
    * f2 -> Identifier()
    */
   public String visit(DivExpression n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Identifier()
    * f3 -> "]"
    */
   public String visit(ArrayLookup n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> "length"
    */
   public String visit(ArrayLength n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ArgList() )?
    * f5 -> ")"
    */
   public String visit(MessageSend n, String argu) {
      String _ret = null;
      String id = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String methodCall = n.f2.accept(this, argu);
      typeAnalysis.currArgs = new ArrayList<>();
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      String type = typeAnalysis.getType(id);
      if (debug) {
         System.out.println("\t\tFound call for method : " + methodCall + ", caller : " + id + ", with type : " + type);
         System.out.println("\t\tArglist : " + typeAnalysis.currArgs.toString());
         System.out.println("\t\tReturn Destination : " + returnId);
      }
      if (typeAnalysis.currMethod != "main") {
         CallInfo callInfo = new CallInfo(id, methodCall, typeAnalysis.currArgs, type);
         callInfo.returnDest = returnId;
         typeAnalysis.methodCalls.add(callInfo);
      } else if (debug) {
         System.out.println("\t\tIgnoring this call since it was made in public static void main(String args[]){}");
      }
      typeAnalysis.currArgs = null;
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> ( ArgRest() )*
    */
   public String visit(ArgList n, String argu) {
      String _ret = null;
      typeAnalysis.currArgs.add(n.f0.accept(this, argu));
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Identifier()
    */
   public String visit(ArgRest n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      typeAnalysis.currArgs.add(n.f1.accept(this, argu));
      return _ret;
   }

   /**
    * f0 -> IntegerLiteral()
    * | TrueLiteral()
    * | FalseLiteral()
    * | Identifier()
    * | ThisExpression()
    * | ArrayAllocationExpression()
    * | AllocationExpression()
    * | NotExpression()
    */
   public String visit(PrimaryExpression n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public String visit(IntegerLiteral n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "true"
    */
   public String visit(TrueLiteral n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "false"
    */
   public String visit(FalseLiteral n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public String visit(Identifier n, String argu) {
      String _ret = n.f0.tokenImage;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "this"
    */
   public String visit(ThisExpression n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Identifier()
    * f4 -> "]"
    */
   public String visit(ArrayAllocationExpression n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public String visit(AllocationExpression n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      String id = n.f1.accept(this, argu);
      typeAnalysis.rtaClassInstantiations.add(id);
      if (debug) {
         System.out.println("\t\tFound instantiation of class : " + id + ", added to RTA list");
      }
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "!"
    * f1 -> Identifier()
    */
   public String visit(NotExpression n, String argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

}
