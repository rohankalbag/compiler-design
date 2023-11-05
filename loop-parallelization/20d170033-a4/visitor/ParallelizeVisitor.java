
package visitor;

import syntaxtree.*;
import java.util.*;

public class ParallelizeVisitor<R, A> implements GJVisitor<String, A> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
   public String visit(NodeList n, A argu) {
      String _ret = null;
      int _count = 0;
      for (Enumeration<Node> e = n.elements(); e.hasMoreElements();) {
         e.nextElement().accept(this, argu);
         _count++;
      }
      return _ret;
   }

   public String visit(NodeListOptional n, A argu) {
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

   public String visit(NodeOptional n, A argu) {
      if (n.present())
         return n.node.accept(this, argu);
      else
         return null;
   }

   public String visit(NodeSequence n, A argu) {
      String _ret = null;
      int _count = 0;
      for (Enumeration<Node> e = n.elements(); e.hasMoreElements();) {
         e.nextElement().accept(this, argu);
         _count++;
      }
      return _ret;
   }

   public String visit(NodeToken n, A argu) {
      return n.tokenImage;
   }

   public Map<String, ClassInfo> classInfoMap = new LinkedHashMap<>();
   public String currClass;
   public String currMethod;
   public LoopInfo currLoop;

   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
   public String visit(Goal n, A argu) {
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
   public String visit(MainClass n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      currClass = n.f1.accept(this, argu);
      classInfoMap.put(currClass, new ClassInfo());
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      currMethod = n.f6.accept(this, argu);
      classInfoMap.get(currClass).methods.put(currMethod, new MethodInfo());
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
      currMethod = null;
      currClass = null;
      return _ret;
   }

   /**
    * f0 -> ClassDeclaration()
    * | ClassExtendsDeclaration()
    */
   public String visit(TypeDeclaration n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( MethodDeclaration() )*
    * f4 -> "}"
    */
   public String visit(ClassDeclaration n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      currClass = n.f1.accept(this, argu);
      classInfoMap.put(currClass, new ClassInfo());
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      currClass = null;
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( MethodDeclaration() )*
    * f6 -> "}"
    */
   public String visit(ClassExtendsDeclaration n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      currClass = n.f1.accept(this, argu);
      classInfoMap.put(currClass, new ClassInfo());
      n.f2.accept(this, argu);
      classInfoMap.get(currClass).parent = n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      currClass = null;
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public String visit(VarDeclaration n, A argu) {
      String _ret = null;
      MethodInfo currMethodInfo = classInfoMap.get(currClass).methods.get(currMethod);
      n.f0.accept(this, argu);
      if (n.f0.f0.choice instanceof ArrayType)
         currMethodInfo.addArrayVariable(n.f1.accept(this, argu));
      else
         currMethodInfo.addVariable(n.f1.accept(this, argu));
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
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
   public String visit(MethodDeclaration n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      currMethod = n.f2.accept(this, argu);
      classInfoMap.get(currClass).methods.put(currMethod, new MethodInfo());
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
      currMethod = null;
      return _ret;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public String visit(FormalParameterList n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public String visit(FormalParameter n, A argu) {
      String _ret = null;
      MethodInfo currMethodInfo = classInfoMap.get(currClass).methods.get(currMethod);
      n.f0.accept(this, argu);
      if (n.f0.f0.choice instanceof ArrayType)
         currMethodInfo.array_parameters.add(n.f1.accept(this, argu));
      else
         currMethodInfo.parameters.add(n.f1.accept(this, argu));
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public String visit(FormalParameterRest n, A argu) {
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
   public String visit(Type n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   public String visit(ArrayType n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "boolean"
    */
   public String visit(BooleanType n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "int"
    */
   public String visit(IntegerType n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "float"
    */
   public String visit(FloatType n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Block()
    * | AssignmentStatement()
    * | ArrayAssignmentStatement()
    * | IfStatement()
    * | ForStatement()
    * | WhileStatement()
    * | PrintStatement()
    */
   public String visit(Statement n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public String visit(Block n, A argu) {
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
   public String visit(AssignmentStatement n, A argu) {
      String _ret = null;
      MethodInfo currMethodInfo = classInfoMap.get(currClass).methods.get(currMethod);
      String id = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      if (n.f2.f0.choice instanceof PrimaryExpression) {
         PrimaryExpression pe = (PrimaryExpression) n.f2.f0.choice;
         if (pe.f0.choice instanceof Identifier) {
            // handling equality aliasing
            String id2 = ((Identifier) pe.f0.choice).f0.tokenImage;
            if (currMethodInfo.parameters.contains(id2))
               currMethodInfo.aliasWithParams.add(id);
            else if (currMethodInfo.aliasWithParams.contains(id2))
               currMethodInfo.aliasWithParams.add(id);
            else if (currMethodInfo.array_parameters.contains(id2))
               currMethodInfo.aliasWithArrayParams.add(id);
            else if (currMethodInfo.aliasWithArrayParams.contains(id2))
               currMethodInfo.aliasWithArrayParams.add(id);
            else if (currMethodInfo.variables.containsKey(id2))
               currMethodInfo.varAlias(id, id2);
            else if (currMethodInfo.array_variables.containsKey(id2))
               currMethodInfo.arrayVarAlias(id, id2);
         }
      }
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayAssignArithemeticExpression()
    * | ArrayAssignArrayOrIntegerLiteralorIdentifier()
    */
   public String visit(ArrayAssignmentStatement n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> IfthenElseStatement()
    * | IfthenStatement()
    */
   public String visit(IfStatement n, A argu) {
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
   public String visit(IfthenStatement n, A argu) {
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
   public String visit(IfthenElseStatement n, A argu) {
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
   public String visit(WhileStatement n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "for"
    * f1 -> "("
    * f2 -> ( ParallelAnnotation() )?
    * f3 -> Identifier()
    * f4 -> "="
    * f5 -> IntegerOrIdentifier()
    * f6 -> ";"
    * f7 -> RelopExpression()
    * f8 -> ";"
    * f9 -> UpdateLoopInductionVariable()
    * f10 -> ")"
    * f11 -> Statement()
    */
   public String visit(ForStatement n, A argu) {
      String _ret = null;
      MethodInfo currMethodInfo = classInfoMap.get(currClass).methods.get(currMethod);
      currLoop = new LoopInfo();
      currMethodInfo.loops.add(currLoop);
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      currLoop.loop_var = n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      currLoop.CheckParallelizability();
      return _ret;
   }

   /**
    * f0 -> <SCOMMENT1>
    * f1 -> <PARALLEL_ANNOTATION>
    * f2 -> <SCOMMENT2>
    */
   public String visit(ParallelAnnotation n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Identifier()
    * f3 -> ")"
    * f4 -> ";"
    */
   public String visit(PrintStatement n, A argu) {
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
    * | LTEExpression()
    * | GTEExpression()
    * | LTExpression()
    * | GTExpression()
    * | NeqExpression()
    * | EQExpression()
    * | PlusExpression()
    * | MinusExpression()
    * | TimesExpression()
    * | DivExpression()
    * | ArrayLookup()
    * | ArrayLength()
    * | MessageSend()
    * | PrimaryExpression()
    */
   public String visit(Expression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> LTEExpression()
    * | LTExpression()
    * | GTEExpression()
    * | GTExpression()
    * | NeqExpression()
    * | EQExpression()
    */
   public String visit(RelopExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> ArithemeticExpression()
    */
   public String visit(UpdateLoopInductionVariable n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PlusExpression()
    * | MinusExpression()
    * | TimesExpression()
    * | DivExpression()
    */
   public String visit(ArithemeticExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayLookup()
    * | Identifier()
    * | IntegerLiteral()
    */
   public String visit(ArrayorIdentifierorIntegerLiteral n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "&&"
    * f2 -> Identifier()
    */
   public String visit(AndExpression n, A argu) {
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
   public String visit(OrExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "<="
    * f2 -> IntegerOrIdentifier()
    */
   public String visit(LTEExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "<"
    * f2 -> IntegerOrIdentifier()
    */
   public String visit(LTExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> ">="
    * f2 -> IntegerOrIdentifier()
    */
   public String visit(GTEExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> ">"
    * f2 -> IntegerOrIdentifier()
    */
   public String visit(GTExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "=="
    * f2 -> IntegerOrIdentifier()
    */
   public String visit(EQExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "!="
    * f2 -> IntegerOrIdentifier()
    */
   public String visit(NeqExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayorIdentifierorIntegerLiteral()
    * f1 -> "+"
    * f2 -> ArrayorIdentifierorIntegerLiteral()
    */
   public String visit(PlusExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayorIdentifierorIntegerLiteral()
    * f1 -> "-"
    * f2 -> ArrayorIdentifierorIntegerLiteral()
    */
   public String visit(MinusExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayorIdentifierorIntegerLiteral()
    * f1 -> "*"
    * f2 -> ArrayorIdentifierorIntegerLiteral()
    */
   public String visit(TimesExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayorIdentifierorIntegerLiteral()
    * f1 -> "/"
    * f2 -> ArrayorIdentifierorIntegerLiteral()
    */
   public String visit(DivExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> IntegerOrIdentifier()
    * f3 -> "]"
    */
   public String visit(ArrayLookup n, A argu) {
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
   public String visit(ArrayLength n, A argu) {
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
   public String visit(MessageSend n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> ( ArgRest() )*
    */
   public String visit(ArgList n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Identifier()
    */
   public String visit(ArgRest n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
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
   public String visit(PrimaryExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public String visit(IntegerLiteral n, A argu) {
      String _ret = null;
      _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "true"
    */
   public String visit(TrueLiteral n, A argu) {
      String _ret = null;
      _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "false"
    */
   public String visit(FalseLiteral n, A argu) {
      String _ret = null;
      _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public String visit(Identifier n, A argu) {
      String _ret = null;
      _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "this"
    */
   public String visit(ThisExpression n, A argu) {
      String _ret = null;
      _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> IntegerOrIdentifier()
    * f4 -> "]"
    */
   public String visit(ArrayAllocationExpression n, A argu) {
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
   public String visit(AllocationExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "!"
    * f1 -> Identifier()
    */
   public String visit(NotExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> IntegerLiteral()
    * | Identifier()
    */
   public String visit(IntegerOrIdentifier n, A argu) {
      String _ret = null;
      _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayLookup()
    * f1 -> "="
    * f2 -> ArithemeticExpression()
    * f3 -> ";"
    */
   public String visit(ArrayAssignArithemeticExpression n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayLookup()
    * f1 -> "="
    * f2 -> ArrayorIdentifierorIntegerLiteral()
    * f3 -> ";"
    */
   public String visit(ArrayAssignArrayOrIntegerLiteralorIdentifier n, A argu) {
      String _ret = null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

}
