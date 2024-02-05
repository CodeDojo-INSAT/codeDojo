package com.zs.codeDojo.controllers.main;

import javax.tools.*;

// import java.io.File;
// import java.io.FileNotFoundException;

// import com.github.javaparser.ast.CompilationUnit;
// import com.github.javaparser.ast.Modifier;
// import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.net.URI;
import java.util.Collections;
import java.util.List;
// import java.util.Scanner;

public class JavaFileCompile {
    private String name;
    private String code;
    private List<String> errorList;
    private boolean status = false;
    // private JavaFileCompile javaFileCompiler = null;

    JavaFileCompile(String name, String code, List<String> errros) {
        this.name = name;
        this.code = code;
        this.errorList = errros;

    }

    protected void compileJava() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            errorList.add("Run with JDK");
            System.err.println("Run with jdk");
        }
        JavaFileObject fileObject = new JavaSourceFromString(this.name, this.code);

        Iterable<? extends JavaFileObject> compilationUnit = Collections.singletonList(fileObject);
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();

        boolean compilationSuccess = compiler.getTask(null, null, diagnosticCollector, null, null, compilationUnit)
                .call();
    
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnosticCollector.getDiagnostics()) {
            errorList.add(diagnostic.toString().substring(1));
        }

        if (compilationSuccess) {
            status = true;
        }
    }

    public boolean getStatus() {
        return this.status;
    }
}

class JavaSourceFromString extends SimpleJavaFileObject {
    final String code;

    JavaSourceFromString(String name, String code) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return this.code;
    }
}
