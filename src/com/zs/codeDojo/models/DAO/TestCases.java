package com.zs.codeDojo.models.DAO;

public class TestCases {
    private final String[] input;
    private final String[] output;
    private String[] ids;
    private final int size;

    public TestCases(String[] in, String[] out) {
        input = in;
        output = out;
        size = in.length;
    }

    public TestCases(String[] in, String[] out, String[] ids) {
        input = in;
        output = out;
        this.ids = ids;
        size = in.length;
    }

    public String[] getSampleTestCase() {
        return new String[]{input[0], output[0]};
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


    public String[] getIds() {
        return this.ids;
    }
}