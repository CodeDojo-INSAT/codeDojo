// Source code is decompiled from a .class file using FernFlower decompiler.
package com.zs.codeDojo.models.checkers;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import java.util.List;

public class InheritanceChecker {
   public boolean status = true;
   private List<String> errorList;

   public InheritanceChecker(List<String> erroList, CompilationUnit compilationUnit) {
      this.errorList = erroList;
      this.checkInheritance(compilationUnit);
   }

   private void checkInheritance(CompilationUnit compilationUnit) {
      compilationUnit.findAll(ClassOrInterfaceDeclaration.class).forEach((classD) -> {
         if (classD.getExtendedTypes().isEmpty()) {
            this.errorList.add("Class  " + classD.getName() + " does not extend any class.");
            this.status = false;
         }
      });
   }
}
