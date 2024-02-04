package com.zs.codeDojo.models.checkTestCases;

import java.io.ByteArrayInputStream;
// import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
// import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.zs.codeDojo.models.DAO.IOStreams;
import com.zs.codeDojo.models.DAO.TestCases;

public class CheckLogic {
    private final Class<?> clazz;
    private final TestCases tc;
    private final IOStreams streams;    
    private byte[] res;
    private String message = null;
    private String error = null;
    private String[] executionTime = null;
    public static final Object lock = new Object();
    private final ExecutorService executors = Executors.newFixedThreadPool(1);


    public CheckLogic(Class<?> clazz, TestCases tc, IOStreams streams) {
        this.clazz = clazz;
        this.tc = tc;
        this.streams = streams;
        check();
    }

    private void check() {
        String[] tcIn = tc.getInputs();
        String[] tcOut = tc.getOutputs();

        res = new byte[tc.getSize()];
        executionTime = new String[tc.getSize()];

        synchronized (lock) {
            try {
                for (int i=0; i<tc.getSize(); i++) {
                    String currentTC = tcIn[i];

                    System.setIn(new ByteArrayInputStream(currentTC.getBytes()));

                    Method mainMethod = clazz.getMethod("main", String[].class);

                    // long startTime = System.nanoTime(); //for old
                    // mainMethod.invoke(null, (Object) new String[0]); //for old
                    // long endTime = System.nanoTime(); //for old

                    InvokeMain invokeMain = new InvokeMain(mainMethod);
                    Future<?> future = executors.submit(invokeMain);

                    try {
                        future.get(3, TimeUnit.SECONDS);
                        executionTime[i] = invokeMain.getExecTime();
                    }
                    catch (Exception e) {
                        if (e instanceof TimeoutException) {
                            error = "Execution Timeout";
                            return;
                        }
                    }
                    finally {
                        future.cancel(true);
                    }
                    // executionTime[i] = String.valueOf((endTime - startTime) / 1_000_000.0) + " ms"; //for old

                    String runtimeOutput = streams.getCustomOut().toString();
                    if (runtimeOutput.equals(tcOut[i])) {
                        res[i] = 1;
                    }
                    else {
                        res[i] = 0;
                        if (message == null) {
                            message = "Your output: \n"
                                    + runtimeOutput + "\n"
                                    + "Expected output: \n"
                                    + tcOut[i] + "\n";
                        }
                    }
                    
                    streams.getCustomOut().reset();
                    System.setIn(streams.getIn());
                }
                executors.shutdown();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getResult() {
        return Arrays.toString(res);
    }

    public boolean isMatched() {
        return message == null;
    }

    public String getMessage() {
        return message;
    }

    public String[] getResultWithExecTime() {
        return new String[]{Arrays.toString(res), Arrays.toString(executionTime)};
    }

    public boolean hasError() {
        return error != null;
    }

    public String getError() {
        return error;
    }

    // public String toString(byte[] source) {
    //     String res = "[";
    //     for (byte b: source) {
    //         res += String.valueOf(b) + ", ";
    //     }
    //     res = res.substring(0, res.length()-2) + "]";
    //     return res;
    // }

    // private String toString(String[] source) {
    //     String res = "[";
    //     for (String b: source) {
    //         res += b + ", ";
    //     }
    //     res = res.substring(0, res.length()-2) + "]";
    //     return res;
    // }
}
