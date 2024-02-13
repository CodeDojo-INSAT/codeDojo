package com.zs.codeDojo.models.checkers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class PolymorphismChecker {
    private boolean status = false;
    private Set<String> methodSignatures = new HashSet<>();
    private Map<String, String> className = new HashMap<>();
    private CompilationUnit compilationUnit;
    // private ErrorList errorList;

    public PolymorphismChecker(CompilationUnit cUnit) {
        this.compilationUnit = cUnit;
        analyze();
    }

    private void analyze() {
        compilationUnit.findAll(ClassOrInterfaceDeclaration.class).forEach(classDec -> {
            classDec.getExtendedTypes().forEach(extendedClasses -> {
                if (extendedClasses.toString().length() > 1) {
                   className.put("extended", extendedClasses.toString() + ":" + classDec.getNameAsString());

                   compilationUnit.getClassByName(extendedClasses.toString()).get().getMethods().forEach(methods -> {
                    methodSignatures.add(methods.getDeclarationAsString(false, false, false));
                   });

                   List<MethodDeclaration> methodDeclaration = null;
                   if (!(methodDeclaration = classDec.getMethods()).isEmpty()) {
                        methodDeclaration.forEach(methods -> {
                            if (!methodSignatures.add(methods.getDeclarationAsString(false, false, false))) {
                                System.out.println("Overrided method: " + methods.getNameAsString());
                                // errorList.add("Overrided method: " + methods.getNameAsString());
                                this.status = true;
                            }
                            else {
                                System.out.println("Method not overrided in " + classDec.getNameAsString() + " class");
                                // errorList.add("Method not overrided in " + classDec.getNameAsString() + " class");
                                this.status = false;
                            }
                        });
                    }
                    else {
                        System.out.println("Method not overrided in " + classDec.getNameAsString() + " class");
                        // errorList.add("Method not overrided in " + classDec.getNameAsString() + " class");
                        this.status = false;
                    }
                } 
            });
        });
    }

    public boolean getStatus() {
        return status;
    }
}