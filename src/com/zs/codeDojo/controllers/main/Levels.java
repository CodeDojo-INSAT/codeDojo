// Source code is decompiled from a .class file using FernFlower decompiler.
package com.zs.codeDojo.controllers.main;

import com.github.javaparser.ast.CompilationUnit;
import com.zs.codeDojo.models.checkers.EncapsulationChecker;
import com.zs.codeDojo.models.checkers.InheritanceChecker;
import com.zs.codeDojo.models.checkers.NamingConventionChecker;

public class Levels {
   public Levels() {
   }

   protected boolean handleLevelOne(CompilationUnit compilationUnit, ErrorList errorList) {
        NamingConventionChecker testUnit = new NamingConventionChecker(errorList);
      compilationUnit.accept(testUnit, null);
      return testUnit.status;
   }

   protected boolean handleLevelTwo(CompilationUnit compilationUnit, ErrorList errorList) {
      InheritanceChecker testUnit = new InheritanceChecker(errorList, compilationUnit);
      return testUnit.status;
   }

   protected boolean handleLevelThree(CompilationUnit compilationUnit, ErrorList errorList) {
      return false;
   }

   protected boolean handleLevelFour(CompilationUnit compilationUnit, ErrorList errorList) {
      EncapsulationChecker testUnit = new EncapsulationChecker(errorList);
      compilationUnit.accept(testUnit, null);
      return testUnit.status;
   }
}
