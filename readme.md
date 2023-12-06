# Compiler Infrastructure and Optimization Implementations in Java

This repository contains implementations of various compiler optimizations and infrastructure for *MiniJava*, a subset of Java, using **JavaCC** and **JTB**. It includes a type checker, a function inliner, a register allocator using Kempe's graph colouring heuristic, and a for-loop parallelization using the GCD test. Each implementation comes with a detailed problem specification and a link to the solution. 

## CS614 : Advanced Compilers

### Course Instructor : Prof. Manas Thakur

## Type Checker for MiniJava

- A grammar file `minijava.jj` is provided, which models a Java-like object-oriented programming language, consisting of classes, objects, integer variables and arrays, while loops, etc. Along with that, a parser generated for that grammar using JavaCC is also provided. The task is to update the parser (`GJDepthFirst.java`) for type-checking MiniJava programs, and print the number of type errors therein. In particular:

- If the program has K (K > 0) type errors, the output should be “Found K type errors.”
- If the program has no type errors, the output should be “Program type-checked successfully.”

- The schema of a few data structures and a few helper methods have been added in `GJDepthFirst.java`, which are useful during type-checking. It should be noted that the input programs will neither have syntax errors nor any errors related to undeclared or multiply declared identifiers.

- Detailed Problem Specification can be found [here](https://github.com/rohankalbag/compiler-infrastructure-and-optimizations/blob/master/type-checker/assignment-1.pdf)
- My solution for the same can be found [here](https://github.com/rohankalbag/compiler-infrastructure-and-optimizations/tree/master/type-checker/assignment-1)
- All the starter code can be found in `starter.zip`



## Function Inliner for MiniJava

The task involves iterating over the body of instance methods and performing the following tasks:

- **Determine inlineability**: Determine if the call-site is inlineable based on Rapid Type Analysis (RTA), an improvement over Class Hierarchy Analysis (CHA) where the call-sites can be more precisely identified using the knowledge of instantiated types.

- **Inlining transformation**: After identifying the set of inlineable call-sites and methods, perform the following subtasks for performing method inlining at a call-site:
  - Map the method arguments (if any) to the local variables. You can create new assignments or even reuse the existing variables.
  - Replace the method call with the method body. Ensure to maintain syntactic and semantic correctness while doing so.
  - Map the return value to the appropriate local variable. Remember that our grammar supports return only from a single point in a method.

- **Constraints**:
    - Infinite memory. (No size constraints for inlining)
    - Nested inlining is not supported. i.e., The inlined method body will be the original version of that method.
    - Library calls can be ignored. Only inline methods whose source code is available.
    - Input test cases will not contain any form of recursive method calls.
    - Input test cases will not contain overloaded methods.
    - There is no specification for renaming variables. i.e., You can rename variables such that the program follows syntactic and semantic correctness. (Input test cases will not contain variable names with underscore character.)

- Detailed Problem Specification can be found [here](https://github.com/rohankalbag/compiler-infrastructure-and-optimizations/blob/master/function-inlining/assignment-2.pdf)
- My solution for the same can be found [here](https://github.com/rohankalbag/compiler-infrastructure-and-optimizations/tree/master/function-inlining/assignment-2)
- All the starter code can be found in `starter.zip`

## Register Allocation using Kempe's Graph Colouring Heuristic

- The task is to perform register allocation for variables in a function wherever possible and spill the rest to the memory using the help from liveness analysis pass provided in the starter. The first line of every testcase contains a special comment of the form `/*k*/` where k represents the maximum number of registers that can be used inside any given function. The goal is to replace all the variable declarations using a suitable register or memory reference. In order to do this, an implementation of liveness analysis is provided which must be used to create an interference graph and color it using the specified number of registers allowed for that program. 


- A liveness API provides the result of liveness analysis as a hashmap that contains a mapping from `Node` to `Set<String>`. The result contains the set of variables that are live at that node i.e. the IN set.

- Detailed Problem Specification can be found [here](https://github.com/rohankalbag/compiler-infrastructure-and-optimizations/blob/master/register-allocation/Assignment-3.pdf)
- My solution for the same can be found [here](https://github.com/rohankalbag/compiler-infrastructure-and-optimizations/tree/master/register-allocation/assignment-3)
- All the starter code can be found in `starter.zip`

## Loop Parallelization using GCD Test

- Parallelization enables a loop to run over multiple processors (or threads) with a potentially significant execution speedup. However, not all loops are parallelizable. Running a loop in parallel over several processors can result in iterations executing out of order. Moreover, the multiple threads executing the loop in parallel may interfere with each other whenever there are data dependencies in the loop.

- The task is to iterate over the body of methods and perform loop parallelization based on simple alias and dependence analysis for references and array indices. The GCD test will be used for this purpose. The for loops that are identified as parallelizable should be marked using the `/* @Parallel */` decorator.

- Detailed Problem Specification can be found [here](https://github.com/rohankalbag/compiler-infrastructure-and-optimizations/blob/master/loop-parallelization/Assignment-4.pdf)
- My solution for the same can be found [here](https://github.com/rohankalbag/compiler-infrastructure-and-optimizations/tree/master/loop-parallelization/assignment-4)
- All the starter code can be found in `starter.zip`
