// author: magesh
package com.zs.codeDojo.models.checkers;

import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.github.javaparser.ast.type.PrimitiveType;
// import com.github.javaparser.ast.Modifier;
// import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;

public class NamingConventionChecker extends VoidVisitorAdapter<Void> {
    private boolean isCorrectClassName = true;
    private boolean isCorrectVariableName = true;
    private boolean isCorrectConstantName = true;
    private boolean isCorrectMethodName = true;
    public boolean status;
    private List<String> errorList;

    public NamingConventionChecker(List<String> errorList) {
        this.errorList = errorList;
    }

    // Checks ClassName
    @Override
    public void visit(ClassOrInterfaceDeclaration classDeclaration, Void arg) {
        super.visit(classDeclaration, arg);
        classDeclaration.getFields().forEach(fieldDeclaration -> {
            if (fieldDeclaration.isFinal()) {
                fieldDeclaration.getVariables().forEach(variables -> {
                    String variableName = variables.getName().toString();
                    Pattern regex = Pattern.compile("[A-Z]*");
                    Matcher match = regex.matcher(variableName);
                    if (!match.matches()) {
                        errorList.add("Incorrect naming for CONSTANT type: " + variableName);
                        isCorrectConstantName = false;
                    }
                });
            }
        });

        Pattern regex = Pattern.compile("[A-Z][a-zA-Z]*");
        String className = classDeclaration.getNameAsString();
        Matcher match = regex.matcher(className);
        if (!match.matches()) {
            errorList.add("Incorrect class name: " + className);
            isCorrectClassName = false;
        }
        changeStatus();
    }

    // check variables
    @Override
    public void visit(VariableDeclarationExpr variableDeclaration, Void arg) {
        super.visit(variableDeclaration, arg);
        // int variableAssignmentCount = 0;
        boolean isFinal = variableDeclaration.getModifiers().toString().contains("final");
        // NodeList<Modifier> variableModifiers = variableDeclaration.getModifiers();
        // for (Modifier modifier : variableModifiers) {
        //     isFinal = modifier.getKeyword().equals(Modifier.Keyword.FINAL);
        // }

        boolean isPrimitiveType = variableDeclaration.getVariable(0).getType() instanceof PrimitiveType;
        VariableDeclarator variableDeclarator = variableDeclaration.getVariable(0);
        String variableName = variableDeclarator.getNameAsString();
        if (isFinal && isPrimitiveType) {
            Pattern regex = Pattern.compile("[A-Z]*");
            Matcher match = regex.matcher(variableName);
            if (!match.matches()) {
                errorList.add("Incorrect naming for CONSTANT type: " + variableName);
                isCorrectConstantName = false;
            }
        } else {
            Pattern regex = Pattern.compile("[a-z][a-zA-Z0-9]*");
            Matcher match = regex.matcher(variableName);
            if (!match.matches()) {
                errorList.add("Incorrect variable declaration format: " + variableName);
                isCorrectVariableName = false;
            }
        }
        changeStatus();
    }

    @Override
    public void visit(MethodDeclaration methodDeclaration, Void arg) {
        super.visit(methodDeclaration, arg);
        Pattern regex = Pattern.compile("[a-z][a-zA-Z0-9]*");
        String methodName = methodDeclaration.getNameAsString();
        Matcher match = regex.matcher(methodName);
        if (!match.matches()) {
            errorList.add("Incorrect method name: " + methodName);
            isCorrectMethodName = false;
        }
        changeStatus();
    }

    private void changeStatus() {
        status = isCorrectClassName && isCorrectConstantName && isCorrectVariableName && isCorrectMethodName;
    }
}