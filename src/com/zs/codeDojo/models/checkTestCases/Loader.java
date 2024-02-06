package com.zs.codeDojo.models.checkTestCases;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.*;

public class Loader {
    private final String qualifiedClassName;
    private final String sourceCode;
    private String error = null;


    public Loader (String source) {
        qualifiedClassName = findMainClass(source);
        sourceCode = source;
    }

    public Class<?> compileAndLoadClass() throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            System.err.println("\n\nCompiler is null\n");
            return null;
        }

        DiagnosticCollector<JavaFileObject> dc = new DiagnosticCollector<>();

        StandardJavaFileManager standardManager = compiler.getStandardFileManager(dc, null, null);
        JavaByteObject byteObject = new JavaByteObject(qualifiedClassName);

        JavaFileManager fileManager = createFileManager(standardManager, byteObject);

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, getCompilationUnit(qualifiedClassName));

        boolean compilationStatus = task.call();
        Class<?> clazz = null;

        if (!compilationStatus) {
            error = "";
            for (Diagnostic<? extends JavaFileObject> diagnostic: dc.getDiagnostics()) {
                error += diagnostic.toString().substring(1);
            }
        }
        else {
            try {
                clazz = loadClass(byteObject);
            }
            catch (ClassNotFoundException | IllegalArgumentException | SecurityException e) {
                e.printStackTrace();
            }
        }
        fileManager.close();
        
        return clazz;
    }

    private Class<?> loadClass(JavaByteObject byteObject) throws ClassNotFoundException {
        final ClassLoader loader = createClassLoader(byteObject);
        return loader.loadClass(qualifiedClassName);
    }

    private JavaFileManager createFileManager(StandardJavaFileManager manager, JavaByteObject byteObject) {
        return new ForwardingJavaFileManager<StandardJavaFileManager>(manager){
            @Override
            public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
                return byteObject;
            }
        };
    }

    private ClassLoader createClassLoader(JavaByteObject byteObject) {
        return new ClassLoader() {
            @Override
            public Class<?> findClass(String name) throws ClassNotFoundException {
                return defineClass(name, byteObject.getBytes(), 0, byteObject.getBytes().length);
            }
        };
    }

    private Iterable<? extends JavaFileObject> getCompilationUnit(String classname) {
        JavaSourceFromString source = new JavaSourceFromString(classname, this.sourceCode);
        return Arrays.asList(source);
    }

    private String findMainClass(String code) {
        String mainClassName = null;
        Pattern pattern = Pattern.compile("(?<=public class )[A-Z][a-zA-Z]*");

        Matcher matcher = pattern.matcher(code);

        if (matcher.find()) {
            mainClassName = matcher.group();
        }

        return mainClassName;
    }

    public String getError() {
        return error;
    }
}