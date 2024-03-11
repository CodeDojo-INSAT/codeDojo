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
   public void visit(ClassOrInterfaceDeclaration cd, Void v) {
      super.visit(cd, v);

      if (cd.getExtendedTypes().isEmpty()) {
         this.errorList.add("Class " + cd.getName() + " does not extend any class.");
         this.status = false;
      }
   }
}
