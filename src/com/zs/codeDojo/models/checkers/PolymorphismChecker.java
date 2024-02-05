package com.zs.codeDojo.models.checkers;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.File;
import java.io.FileNotFoundException;

public class PolymorphismChecker {

    public static void main(String[] args) {
        String filePath = "/home/arjun/javaClass/Conventions.java";

        try {
            File file = new File(filePath);

            ParseResult<CompilationUnit> parseResult = new JavaParser().parse(file);

            if (parseResult.isSuccessful()) {
                CompilationUnit compilationUnit = parseResult.getResult().get();

                checkPolymorphism(compilationUnit);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void checkPolymorphism(CompilationUnit compilationUnit) {
        compilationUnit.findAll(ClassOrInterfaceDeclaration.class).forEach(classDeclaration -> {
            // System.out.println("Class " + classDeclaration.getName());

            classDeclaration.findAll(MethodDeclaration.class).forEach(method -> {
                if (method.isAnnotationPresent(Override.class)) {
                    System.out.println("\tMethod " + method.getDeclarationAsString(false, false, false) + " is overridden.");
                }
            });
        });
    }
}

