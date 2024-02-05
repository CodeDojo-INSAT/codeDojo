package com.zs.codeDojo.models.checkers;

import java.lang.reflect.Array;
import java.util.ArrayList;
// import java.util.ArrayList;
import java.util.List;

// import org.json.JSONObject;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class EncapsulationChecker extends VoidVisitorAdapter<Void> {
    public boolean status = true;
    List<String> error;

    public EncapsulationChecker(ArrayList<String> error) {
        this.error = error;
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration classDeclaration, Void v) {
        super.visit(classDeclaration, v);

        classDeclaration.getFields().forEach(fieldDeclaration -> {
            System.out.println("Field: "+ fieldDeclaration.toString());
            if (fieldDeclaration.isPrivate()) {
                fieldDeclaration.getVariables().forEach(variable -> {
                    
                    String variableName = variable.getNameAsString();

                    String getterName = "get" + variableName.substring(0,1).toUpperCase() + variableName.substring(1);
                    String isGetterName = "is" + variableName.substring(0, 1).toUpperCase() + variableName.substring(1);
                    if (classDeclaration.getMethodsByName(getterName).isEmpty() && classDeclaration.getMethodsByName(isGetterName).isEmpty()) {
                        this.status = false;
                        error.add("No getter found for this field: " + variableName);
                    }

                    String setterName = "set" + variableName.substring(0,1).toUpperCase() + variableName.substring(1);
                    if (classDeclaration.getMethodsByName(setterName).size() < 1) {
                        this.status = false;
                        error.add("No setter found for this field: " + variableName);
                    }
                });
            } else {
                this.status = false;

                fieldDeclaration.getVariables().forEach(variable -> {
                    error.add("Please change this field to private to acheive encapsulation. Field: " + variable.getNameAsString());
                });
            }
        });
    }
}