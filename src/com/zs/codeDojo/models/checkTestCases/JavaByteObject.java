package com.zs.codeDojo.models.checkTestCases;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class JavaByteObject extends SimpleJavaFileObject {
    private OutputStream out;

    public JavaByteObject(String name) {
        super(URI.create("bytes:///" + name.replaceAll("\\.", "/")), Kind.CLASS);
        out = new ByteArrayOutputStream();
    }

    public OutputStream openOutputStream() {
        return out;
    }

    public byte[] getBytes() {
        return ((ByteArrayOutputStream) out).toByteArray();
    }
}
