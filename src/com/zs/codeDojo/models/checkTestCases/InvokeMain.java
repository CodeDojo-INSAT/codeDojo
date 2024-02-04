package com.zs.codeDojo.models.checkTestCases;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvokeMain implements Runnable {
    private final Method method;
    private long startTime;
    private long endTime;

    public InvokeMain(Method method) {
        this.method = method;
    }

    public void run() {
        try {
            startTime = System.nanoTime();
            method.invoke(null, (Object) new String[0]);
            endTime = System.nanoTime();
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String getExecTime() {
        return String.valueOf((endTime - startTime) / 1_000_000.0);
    }
}
