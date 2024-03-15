package com.zs.codeDojo.models.checkers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
// import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class PolymorphismChecker {
    public boolean status = false;
    private Set<String> methodSignatures = new HashSet<>();
    private Map<String, String> className = new HashMap<>();
    private CompilationUnit compilationUnit;
    private List<String> errorList;
    private boolean isExtended = false;

    public PolymorphismChecker(CompilationUnit cUnit, ArrayList<String> errorList) {
        this.errorList = errorList;
        this.compilationUnit = cUnit;
        analyze();
    }

    public void analyze() {
        compilationUnit.findAll(ClassOrInterfaceDeclaration.class).forEach(classDec -> {
            classDec.getExtendedTypes().forEach(extendedClasses -> {
                isExtended = true;
                if (extendedClasses.toString().length() > 1) {
                    className.put("extended", extendedClasses.toString() + ":" + classDec.getNameAsString());

                    compilationUnit.getClassByName(extendedClasses.toString()).get().getMethods().forEach(methods -> {
                        methodSignatures.add(methods.getDeclarationAsString(false, false, false));
                    });

                    List<MethodDeclaration> methodDeclaration = null;
                    if (!(methodDeclaration = classDec.getMethods()).isEmpty()) {
                        methodDeclaration.forEach(methods -> {
                            if (!methodSignatures.add(methods.getDeclarationAsString(false, false, false))) {
                                System.err.println("Overrided method: " + methods.getNameAsString());
                                errorList.add("Overrided method: " + methods.getNameAsString());
                                this.status = true;
                            } else {
                                System.err.println("Method not overrided in " + classDec.getNameAsString() + " class");
                                errorList.add("Method not overrided in " + classDec.getNameAsString() + " class");
                                this.status = false;
                            }
                        });
                    } else {
                        System.err.println("Method not overrided in " + classDec.getNameAsString() + " class");
                        errorList.add("Method not overrided in " + classDec.getNameAsString() + " class");
                        this.status = false;
                    }
                }
            });
        });

        if (!isExtended) {
            errorList.add("To acheive override you must be extend a class");
            System.err.println("Class not extend any class");
        }
    }

    public boolean getStatus() {
        return status;
    }
}