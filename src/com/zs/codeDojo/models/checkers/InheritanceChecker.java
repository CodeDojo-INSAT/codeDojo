// author: Piradeep
package com.zs.codeDojo.models.checkers;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class InheritanceChecker extends VoidVisitorAdapter<Void> {
   public boolean status = true;
   private List<String> errorList;

   public InheritanceChecker(ArrayList<String> erroList) {
      this.errorList = erroList;
   }

   @Override
   public void visit(ClassOrInterfaceDeclaration classDec, Void v) {
      super.visit(classDec, v);

      if (classDec.getExtendedTypes().isEmpty()) {
         this.errorList.add("Class " + classDec.getName() + " does not extend any class.");
         this.status = false;
      }
   }
}
