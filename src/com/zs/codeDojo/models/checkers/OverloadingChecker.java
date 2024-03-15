package com.zs.codeDojo.models.checkers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// import java.util.Set;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class OverloadingChecker {
    public boolean status = false;
    private Map<String, Integer> methodSignatures = new HashMap<>();
    private CompilationUnit compilationUnit;
    private List<String> errorList;

    public OverloadingChecker(CompilationUnit cUnit, ArrayList<String> errorList) {
        this.errorList = errorList;
        this.compilationUnit = cUnit;
        analyze();
    }

    public void analyze() {
        compilationUnit.findAll(ClassOrInterfaceDeclaration.class).forEach(classDec -> {
            List<MethodDeclaration> methods = classDec.getMethods();

            for (MethodDeclaration method : methods) {
                String methodName = method.getNameAsString();

                methodSignatures.put(methodName, methodSignatures.getOrDefault(methodName, 0) +1);
            }
        });

        methodSignatures.forEach((methodName, count) -> {
            if (count > 1) {
                status = true;
                errorList.add("Method '" + methodName + "' is overloaded with " + count + " definitions.");
            }
        });

        if (!status) {
            errorList.add("No Overloaded methods found.");
        }
    }
}
