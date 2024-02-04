package com.zs.codeDojo.models.DAO;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class IOStreams {
    
    private final InputStream in;
    private final OutputStream out;
    private ByteArrayOutputStream customOut;
    private final OutputStream err;

    public IOStreams(InputStream in, OutputStream out, OutputStream err) {
        this.in = in;
        this.out = out;
        this.err = err;
    }

    public void setCustomOut(ByteArrayOutputStream out) {
        customOut = out;
    }

    public InputStream getIn() {
        return in;
    }

    public OutputStream getOut() {
        return out;
    }

    public ByteArrayOutputStream getCustomOut() {
        return customOut;
    }

    public void restoreErr() {
        System.setErr(new PrintStream(err));
    }
}
