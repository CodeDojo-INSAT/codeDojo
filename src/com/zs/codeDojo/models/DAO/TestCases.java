package com.zs.codeDojo.models.DAO;

public class TestCases {
    private final String[] input;
    private final String[] output;
    private final int size;

    public TestCases(String[] in, String[] out) {
        input = in;
        output = out;
        size = in.length;
    }

    public String[] getInputs() {
        return input;
    }

    public String[] getOutputs() {
        return output;
    }

    public int getSize() {
        return size;
    }
}