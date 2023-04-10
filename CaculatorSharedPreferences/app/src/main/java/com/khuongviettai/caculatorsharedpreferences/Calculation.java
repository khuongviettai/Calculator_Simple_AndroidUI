package com.khuongviettai.caculatorsharedpreferences;

public class Calculation {
    private String math;
    private String result;

    public Calculation(String math, String result) {
        this.math = math;
        this.result = result;
    }

    public String getMath() {
        return math;
    }

    public String getResult() {
        return result;
    }
}
